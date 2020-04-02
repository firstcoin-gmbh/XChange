package org.knowm.xchange.bitcoinde.v4.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BitcoindeData {

  private BitcoindeBalances balances;
  private BitcoindeFidorReservation fidorReservation;
  private BitcoindeEncryptedInformation encryptedInformation;

  public BitcoindeData(
      @JsonProperty("balances") BitcoindeBalances btcBalances,
      @JsonProperty("fidor_reservation") BitcoindeFidorReservation fidorReservation,
      @JsonProperty("encrypted_information") BitcoindeEncryptedInformation encryptedInformation) {
    this.balances = btcBalances;
    this.fidorReservation = fidorReservation;
    this.encryptedInformation = encryptedInformation;
  }

  public BitcoindeBalances getBalances() {
    return balances;
  }

  public BitcoindeFidorReservation getFidorReservation() {
    return fidorReservation;
  }

  public BitcoindeEncryptedInformation getEncryptedInformation() {
    return encryptedInformation;
  }
}
