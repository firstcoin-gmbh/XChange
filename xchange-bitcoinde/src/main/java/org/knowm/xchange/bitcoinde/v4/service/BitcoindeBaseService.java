package org.knowm.xchange.bitcoinde.v4.service;

import org.knowm.xchange.bitcoinde.v4.Bitcoinde;
import org.knowm.xchange.bitcoinde.v4.BitcoindeExchange;
import org.knowm.xchange.bitcoinde.v4.dto.BitcoindeException;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.RateLimitExceededException;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.BaseService;
import si.mazi.rescu.RestProxyFactory;

public class BitcoindeBaseService extends BaseExchangeService<BitcoindeExchange>
    implements BaseService {

  protected final Bitcoinde bitcoinde;
  protected final String apiKey;
  protected final BitcoindeDigest signatureCreator;

  protected BitcoindeBaseService(BitcoindeExchange exchange) {
    super(exchange);
    this.bitcoinde =
        RestProxyFactory.createProxy(
            Bitcoinde.class, exchange.getExchangeSpecification().getSslUri(), getClientConfig());
    this.apiKey = exchange.getExchangeSpecification().getApiKey();
    this.signatureCreator =
        BitcoindeDigest.createInstance(exchange.getExchangeSpecification().getSecretKey(), apiKey);
  }

  protected RuntimeException handleError(BitcoindeException exception) {
    if (exception.getMessage().contains("Insufficient credits")) {
      return new RateLimitExceededException(exception);
    } else {
      return new ExchangeException(exception.getMessage(), exception);
    }
  }
}
