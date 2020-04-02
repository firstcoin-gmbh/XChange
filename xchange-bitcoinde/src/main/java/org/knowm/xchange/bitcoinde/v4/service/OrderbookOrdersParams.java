package org.knowm.xchange.bitcoinde.v4.service;

import org.knowm.xchange.bitcoinde.PaymentOption;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParams;

public interface OrderbookOrdersParams extends OpenOrdersParams {

  default boolean onlyOrdersWithRequirementsFullfilled() {
    return false;
  }

  default boolean onlyFromFullyIdentifiedUsers() {
    return false;
  }

  default boolean onlyExpressOrders() {
    return true;
  }

  default boolean onlyOrdersWithSameBankGroup() {
    return false;
  }

  default boolean onlyOrdersWithSameBIC() {
    return false;
  }

  default String[] onlyOrdersWithSeatOfBank() {
    return new String[0];
  }

  default PaymentOption onlyOrdersWithPaymentOption() {
    return null;
  }
}
