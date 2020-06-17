package org.knowm.xchange.bitcoinde.v4.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.knowm.xchange.bitcoinde.v4.dto.account.BitcoindeAccountLedger;
import org.knowm.xchange.bitcoinde.v4.dto.account.BitcoindeType;
import org.knowm.xchange.bitcoinde.v4.dto.trade.BitcoindePage;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class BitcoindeFundingHistoryWrapper extends BitcoindeResponse {

    @JsonProperty("account_ledger")
    private List<BitcoindeAccountLedger> accountLedgers;

    @JsonProperty("page")
    private BitcoindePage page;

    Map<List<BitcoindeAccountLedger>, BigDecimal> map;

    private List<BitcoindeAccountLedger> accountLedgerEntries;
    private BigDecimal accountLedgerFees;

    public Map<List<BitcoindeAccountLedger>, BigDecimal> mapLedger(){

        map = null;

        for (BitcoindeAccountLedger ledger : accountLedgers){
            if (ledger.getType() == BitcoindeType.OUTGOING_FEE_VOLUNTARY){

                accountLedgerFees = accountLedgerFees.add(ledger.getCashflow().abs());
            }
            else
            {
                accountLedgerEntries.add(ledger);
            }
        }

        map.put(accountLedgerEntries, accountLedgerFees);

        return map;
    }
}