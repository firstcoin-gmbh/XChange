package org.knowm.xchange.bitcoinde.v4.service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import org.knowm.xchange.bitcoinde.v4.BitcoindeAdapters;
import org.knowm.xchange.bitcoinde.v4.BitcoindeExchange;
import org.knowm.xchange.bitcoinde.v4.dto.account.BitcoindeAccountLedgerWrapper;
import org.knowm.xchange.bitcoinde.v4.dto.account.BitcoindeLedgerType;
import org.knowm.xchange.bitcoinde.v4.dto.trade.BitcoindeMyTrade;
import org.knowm.xchange.bitcoinde.v4.dto.trade.BitcoindeMyTradesWrapper;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.FundingRecord;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.service.account.AccountService;
import org.knowm.xchange.service.trade.params.*;

public class BitcoindeAccountService extends BitcoindeAccountServiceRaw implements AccountService {

  public BitcoindeAccountService(BitcoindeExchange exchange) {
    super(exchange);
  }

  @Override
  public AccountInfo getAccountInfo() throws IOException {
    return BitcoindeAdapters.adaptAccountInfo(getBitcoindeAccount());
  }

  /**
   * Create funding history parameters with default values.
   * The currency has no default value and has to be set manually using BitcoindeFundingHistoryParams.setCurrency.
   * The default type is "all".
   * The default startTime lies 10 days in the past.
   * The default endTime is yesterday.
   * The default page is 1.
   * @return fundingHistoryParams
   */
  @Override
  public TradeHistoryParams createFundingHistoryParams() {
    LocalDate currentTime = LocalDate.now();
    LocalDate startTime = currentTime.minusDays(10);
    LocalDate endTime = currentTime.minusDays(1);
    Date startDate = Date.from(startTime.atStartOfDay(ZoneId.systemDefault()).toInstant());
    Date endDate = Date.from(endTime.atStartOfDay(ZoneId.systemDefault()).toInstant());
    return new BitcoindeFundingHistoryParams(null, BitcoindeLedgerType.ALL, startDate, endDate, 1);
  }

  @Override
  public List<FundingRecord> getFundingHistory(TradeHistoryParams params) throws IOException {
    BitcoindeFundingHistoryParams bitcoindeFundingHistoryParams = (BitcoindeFundingHistoryParams) params;
    BitcoindeAccountLedgerWrapper bitcoindeAccountLedgerWrapper = queryAccountLedger(bitcoindeFundingHistoryParams);

    BitcoindeAccountLedgerWrapper.BitcoindeSplitAccountLedgerWrapper bitcoindeSplitAccountLedgerWrapper =
            bitcoindeAccountLedgerWrapper.splitAccountLedger();
    List<FundingRecord> fundingHistory = BitcoindeAdapters.adaptFundingHistory(bitcoindeSplitAccountLedgerWrapper);
    if ((/*!bitcoindeSplitAccountLedgerWrapper.getAccountLedgerEntries().isEmpty() ||*/
        !bitcoindeSplitAccountLedgerWrapper.getAccountLedgerFees().isEmpty()) &&
        bitcoindeAccountLedgerWrapper.getPage().last > bitcoindeAccountLedgerWrapper.getPage().current) {
      Integer page = bitcoindeFundingHistoryParams.getPage() + 1;
      BitcoindeFundingHistoryParams newParams = new BitcoindeFundingHistoryParams(bitcoindeFundingHistoryParams);
      newParams.setPage(page);

      BitcoindeAccountLedgerWrapper.BitcoindeSplitAccountLedgerWrapper newSplitAccountLedgerWrapper;
      if (!bitcoindeSplitAccountLedgerWrapper.getAccountLedgerEntries().isEmpty()) {
        // This block is only needed if the API returns payout entries prior to their respective fee entries
        /*
        // Need to get additional fees
        BitcoindeAccountLedgerWrapper newAccountLedgerWrapper = queryAccountLedger(newParams);
        newSplitAccountLedgerWrapper =
                newAccountLedgerWrapper.splitAccountLedger();
        newSplitAccountLedgerWrapper.setAccountLedgerEntries(bitcoindeSplitAccountLedgerWrapper.getAccountLedgerEntries());
        List<FundingRecord> newFundingHistory = BitcoindeAdapters.adaptFundingHistory(
                newSplitAccountLedgerWrapper);
        fundingHistory.addAll(newFundingHistory);
        */
      } else {
        // Need to get additional ledger entries
        BitcoindeAccountLedgerWrapper newAccountLedgerWrapper = queryAccountLedger(newParams);
        newSplitAccountLedgerWrapper =
                newAccountLedgerWrapper.splitAccountLedger();
        // Filter new non-payout entries
        newSplitAccountLedgerWrapper.getAccountLedgerEntries()
                .removeIf(entry -> BitcoindeLedgerType.fromString(entry.getType()) != BitcoindeLedgerType.PAYOUT);
        newSplitAccountLedgerWrapper.setAccountLedgerFees(bitcoindeSplitAccountLedgerWrapper.getAccountLedgerFees());
        List<FundingRecord> newFundingHistory = BitcoindeAdapters.adaptFundingHistory(
                newSplitAccountLedgerWrapper);
        fundingHistory.addAll(newFundingHistory);
      }
    }

    return fundingHistory;
  }

  private BitcoindeAccountLedgerWrapper queryAccountLedger(
          BitcoindeFundingHistoryParams bitcoindeFundingHistoryParams) throws IOException {
    Currency currency = bitcoindeFundingHistoryParams.getCurrency();
    FundingRecord.Type type = bitcoindeFundingHistoryParams.getType();
    Date startTime = bitcoindeFundingHistoryParams.getStartTime();
    Date endTime = bitcoindeFundingHistoryParams.getEndTime();
    Integer page = bitcoindeFundingHistoryParams.getPage();

    BitcoindeAccountLedgerWrapper bitcoindeAccountLedgerWrapper;
    if (type == null) {
      BitcoindeLedgerType customType = bitcoindeFundingHistoryParams.getCustomType();
      bitcoindeAccountLedgerWrapper = getAccountLedger(
              currency, customType, startTime, endTime, page);
    }
    else {
      bitcoindeAccountLedgerWrapper = getAccountLedger(
              currency, type, startTime, endTime, page);
    }

    return bitcoindeAccountLedgerWrapper;
  }

  public static class BitcoindeFundingHistoryParams extends DefaultTradeHistoryParamsTimeSpan
          implements TradeHistoryParamCurrencies, HistoryParamsFundingType {

    private Currency currency;
    private FundingRecord.Type type;
    private BitcoindeLedgerType customType;
    private Date startTime;
    private Date endTime;
    private Integer page;

    public BitcoindeFundingHistoryParams(final Currency currency, final FundingRecord.Type type,
                                         final Date startTime, final Date endTime, final Integer page) {
      super(startTime, endTime);
      this.currency = currency;
      this.type = type;
      this.startTime = startTime;
      this.endTime = endTime;
      this.page = page;
    }

    public BitcoindeFundingHistoryParams(final Currency currency, final BitcoindeLedgerType customType,
                                         final Date startTime, final Date endTime, final Integer page) {
      super(startTime, endTime);
      this.currency = currency;
      this.customType = customType;
      this.startTime = startTime;
      this.endTime = endTime;
      this.page = page;
    }

    /**
     * Copy constructor
     * @param other
     */
    public BitcoindeFundingHistoryParams(BitcoindeFundingHistoryParams other) {
      this(other.currency, other.type, other.startTime, other.endTime, other.page);
      this.customType = other.customType;
    }

    public Currency getCurrency() {
      return currency;
    }

    public void setCurrency(Currency currency) {
      this.currency = currency;
    }

    @Override
    public FundingRecord.Type getType() {
      return type;
    }

    @Override
    public void setType(FundingRecord.Type type) {
      this.type = type;
      this.customType = null;
    }

    public BitcoindeLedgerType getCustomType() {
      return customType;
    }

    public void setCustomType(BitcoindeLedgerType customType) {
      this.customType = customType;
      this.type = null;
    }

    @Override
    public Date getStartTime() {
      return startTime;
    }

    @Override
    public void setStartTime(Date startTime) {
      this.startTime = startTime;
    }

    @Override
    public Date getEndTime() {
      return endTime;
    }

    @Override
    public void setEndTime(Date endTime) {
      this.endTime = endTime;
    }

    public Integer getPage() {
      return page;
    }

    public void setPage(Integer page) {
      this.page = page;
    }

    @Override
    public Currency[] getCurrencies() {
      return new Currency[]{this.currency};
    }

    @Override
    public void setCurrencies(Currency[] currencies) {
      if (currencies.length > 1) {
        throw new NotAvailableFromExchangeException(
                "Parameter \"currencies\" may only contain one currency for BitcoindeFundingHistoryParams!");
      }
      this.currency = currencies[0];
    }

  }

}
