package org.knowm.xchange.bitcoinde.v4.service;

import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.service.trade.params.CancelOrderParams;

public interface CancelOrderByIdAndCurrencyPair extends CancelOrderParams {

  CurrencyPair getCurrencyPair();

  String getId();
}
