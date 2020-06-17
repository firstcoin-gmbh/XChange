package org.knowm.xchange.bitcoinde.v4.service;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.knowm.xchange.bitcoinde.v4.BitcoindeAdapters;
import org.knowm.xchange.bitcoinde.v4.BitcoindeExchange;
import org.knowm.xchange.bitcoinde.v4.dto.BitcoindeIdResponse;
import org.knowm.xchange.bitcoinde.v4.dto.trade.BitcoindeMyTrade;
import org.knowm.xchange.bitcoinde.v4.dto.trade.BitcoindeMyTradesWrapper;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.service.trade.params.CancelOrderParams;
import org.knowm.xchange.service.trade.params.DefaultTradeHistoryParamsTimeSpan;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;

public class BitcoindeTradeService extends BitcoindeTradeServiceRaw implements TradeService {

  public BitcoindeTradeService(BitcoindeExchange exchange) {
    super(exchange);
  }

  @Override
  public OpenOrders getOpenOrders() throws IOException {
    return BitcoindeAdapters.adaptOpenOrders(getBitcoindeMyOpenOrders());
  }

  @Override
  public String placeLimitOrder(LimitOrder limitOrder) throws IOException {
    BitcoindeIdResponse response = bitcoindePlaceLimitOrder(limitOrder);
    return response.getId();
  }

  @Override
  public boolean cancelOrder(CancelOrderParams orderParams) throws IOException {
    if (orderParams instanceof CancelOrderByIdAndCurrencyPair) {
      CancelOrderByIdAndCurrencyPair cob = (CancelOrderByIdAndCurrencyPair) orderParams;
      bitcoindeCancelOrders(cob.getId(), cob.getCurrencyPair());
    }

    return true;
  }

  /**
   * Create trade history parameters with default values.
   * The default tradingPair is "all".
   * The default type is "all".
   * The default state is "pending".
   * The default startTime is null.
   * The default endTime is null.
   * The default page is 1.
   * @return tradeHistoryParams
   */
  @Override
  public TradeHistoryParams createTradeHistoryParams() {
    BitcoindeMyTrade.State state = BitcoindeMyTrade.State.PENDING;
    return new BitcoindeTradeHistoryParams(null, null, state,
            0, null, null, 1);
  }

  @Override
  public UserTrades getTradeHistory(TradeHistoryParams params) throws IOException {
    BitcoindeTradeHistoryParams bitcoindeTradeHistoryParams = (BitcoindeTradeHistoryParams) params;
    BitcoindeMyTradesWrapper bitcoindeMyTradesWrapper = queryMyTrades(bitcoindeTradeHistoryParams);
    return BitcoindeAdapters.adaptTradeHistory(bitcoindeMyTradesWrapper);
  }

  private BitcoindeMyTradesWrapper queryMyTrades(
          BitcoindeTradeHistoryParams bitcoindeTradeHistoryParams) throws IOException {
    CurrencyPair tradingPair = bitcoindeTradeHistoryParams.getTradingPair();
    BitcoindeMyTrade.Type type = bitcoindeTradeHistoryParams.getType();
    BitcoindeMyTrade.State state = bitcoindeTradeHistoryParams.getState();
    Integer onlyTradesWithActionForPaymentOrTransferRequired
            = bitcoindeTradeHistoryParams.getOnly_trades_with_action_for_payment_or_transfer_required();
    Date startTime = bitcoindeTradeHistoryParams.getStartTime();
    Date endTime = bitcoindeTradeHistoryParams.getEndTime();
    Integer page = bitcoindeTradeHistoryParams.getPage();

    BitcoindeMyTradesWrapper bitcoindeMyTradesWrapper = getMyTrades(
            tradingPair, type, state, onlyTradesWithActionForPaymentOrTransferRequired,
            startTime, endTime, page);

    return bitcoindeMyTradesWrapper;
  }

  public static class BitcoindeTradeHistoryParams extends DefaultTradeHistoryParamsTimeSpan {

    private CurrencyPair tradingPair;
    private BitcoindeMyTrade.Type type;
    private BitcoindeMyTrade.State state;
    private Integer only_trades_with_action_for_payment_or_transfer_required;
    private Date startTime;
    private Date endTime;
    private Integer page;

    public BitcoindeTradeHistoryParams(final CurrencyPair tradingPair, final BitcoindeMyTrade.Type type,
                                       final BitcoindeMyTrade.State state,
                                       final Integer only_trades_with_action_for_payment_or_transfer_required,
                                       final Date startTime, final Date endTime, final Integer page) {
      super(startTime, endTime);
      this.tradingPair = tradingPair;
      this.type = type;
      this.state = state;
      this.only_trades_with_action_for_payment_or_transfer_required
              = only_trades_with_action_for_payment_or_transfer_required;
      this.startTime = startTime;
      this.endTime = endTime;
      this.page = page;
    }

    /**
     * Copy constructor
     * @param other
     */
    public BitcoindeTradeHistoryParams(BitcoindeTradeHistoryParams other) {
      this(other.tradingPair, other.type, other.state, other.only_trades_with_action_for_payment_or_transfer_required,
              other.startTime, other.endTime, other.page);
    }

    public CurrencyPair getTradingPair() {
      return tradingPair;
    }

    public void setTradingPair(CurrencyPair tradingPair) {
      this.tradingPair = tradingPair;
    }

    public BitcoindeMyTrade.Type getType() {
      return type;
    }

    public void setType(BitcoindeMyTrade.Type type) {
      this.type = type;
    }

    public BitcoindeMyTrade.State getState() {
      return state;
    }

    public void setState(BitcoindeMyTrade.State state) {
      this.state = state;
    }

    public Integer getOnly_trades_with_action_for_payment_or_transfer_required() {
      return only_trades_with_action_for_payment_or_transfer_required;
    }

    public void setOnlyTradesWithActionForPaymentOrTransferRequired(
            Integer only_trades_with_action_for_payment_or_transfer_required) {
      this.only_trades_with_action_for_payment_or_transfer_required
              = only_trades_with_action_for_payment_or_transfer_required;
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

  }

}
