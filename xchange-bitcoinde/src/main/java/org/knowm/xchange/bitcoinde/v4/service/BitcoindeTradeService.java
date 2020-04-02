package org.knowm.xchange.bitcoinde.v4.service;

import java.io.IOException;
import org.knowm.xchange.bitcoinde.v4.BitcoindeAdapters;
import org.knowm.xchange.bitcoinde.v4.BitcoindeExchange;
import org.knowm.xchange.bitcoinde.v4.dto.BitcoindeIdResponse;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.service.trade.params.CancelOrderParams;

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
}
