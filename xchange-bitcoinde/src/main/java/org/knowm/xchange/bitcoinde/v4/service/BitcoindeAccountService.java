package org.knowm.xchange.bitcoinde.v4.service;

import java.io.IOException;
import org.knowm.xchange.bitcoinde.v4.BitcoindeAdapters;
import org.knowm.xchange.bitcoinde.v4.BitcoindeExchange;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.service.account.AccountService;

public class BitcoindeAccountService extends BitcoindeAccountServiceRaw implements AccountService {

  public BitcoindeAccountService(BitcoindeExchange exchange) {
    super(exchange);
  }

  @Override
  public AccountInfo getAccountInfo() throws IOException {
    return BitcoindeAdapters.adaptAccountInfo(getBitcoindeAccount());
  }
}
