package org.knowm.xchange.bitcoinde.v4.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.knowm.xchange.bitcoinde.v4.dto.account.BitcoindeAccountLedger;
import org.knowm.xchange.bitcoinde.v4.dto.trade.BitcoindePage;
import java.util.List;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class BitcoindeFundingHistoryWrapper extends BitcoindeResponse {

    @JsonProperty("account_ledger")
    private List<BitcoindeAccountLedger> accountLedgers;

    @JsonProperty("page")
    private BitcoindePage page;
}
