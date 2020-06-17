package org.knowm.xchange.bitcoinde.v4.dto.account;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.bitcoinde.v4.dto.marketdata.BitcoindeTrade;
import org.knowm.xchange.bitcoinde.v4.dto.trade.BitcoindeAccountLedgerTrade;

import java.math.BigDecimal;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BitcoindeAccountLedger {

  private String date;
  private String type;
  private String reference;
  private BigDecimal cashflow;
  private BigDecimal balance;
  private BitcoindeAccountLedgerTrade trade;

  public BitcoindeAccountLedger(
      @JsonProperty("date") String date,
      @JsonProperty("type") String type,
      @JsonProperty("reference") String reference,
      @JsonProperty("cashflow") BigDecimal cashflow,
      @JsonProperty("balance") BigDecimal balance,
      @JsonProperty("trade") BitcoindeAccountLedgerTrade trade) {
    this.date = date;
    this.type = type;
    this.reference = reference;
    this.cashflow = cashflow;
    this.balance = balance;
    this.trade = trade;
  }

  public String getDate() {
    return date;
  }

  public String getType() {
    return type;
  }

  public String getReference() {
    return reference;
  }

  public BigDecimal getCashflow() {
    return cashflow;
  }

  public BigDecimal getBalance() {
    return balance;
  }

  public BitcoindeAccountLedgerTrade getTrade() { return trade; }

}
