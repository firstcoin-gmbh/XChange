package org.knowm.xchange.bitcoinde.v4.service;

import java.io.IOException;
import java.util.Date;

import org.knowm.xchange.bitcoinde.v4.BitcoindeExchange;
import org.knowm.xchange.bitcoinde.v4.dto.BitcoindeException;
import org.knowm.xchange.bitcoinde.v4.dto.BitcoindeFundingHistoryWrapper;
import org.knowm.xchange.bitcoinde.v4.dto.account.BitcoindeAccountWrapper;
import si.mazi.rescu.SynchronizedValueFactory;

public class BitcoindeAccountServiceRaw extends BitcoindeBaseService {

  private final SynchronizedValueFactory<Long> nonceFactory;

  protected BitcoindeAccountServiceRaw(BitcoindeExchange exchange) {
    super(exchange);
    this.nonceFactory = exchange.getNonceFactory();
  }

  public BitcoindeAccountWrapper getBitcoindeAccount() throws IOException {
    try {
      return bitcoinde.getAccount(apiKey, nonceFactory, signatureCreator);
    } catch (BitcoindeException e) {
      throw handleError(e);
    }
  }

  public BitcoindeFundingHistoryWrapper getBitcoindeFundingHistory(String Currency, String type, Date dateStart, Date dateEnd, Integer page) throws  IOException{
    try{
      return bitcoinde.getFundingHistory(apiKey, nonceFactory, signatureCreator,Currency, type, dateStart, dateEnd, page);
    }catch ( BitcoindeException e){
      throw handleError(e);
    }
  }
}
