package org.knowm.xchange.bitcoinde.v4.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.knowm.xchange.bitcoinde.v4.dto.marketdata.BitcoindeTradeHistoryTrade;
import org.knowm.xchange.bitcoinde.v4.dto.trade.BitcoindePage;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class BitcoindeTradeHistoryWrapper extends BitcoindeResponse{
    @JsonProperty("trades")
    private List<BitcoindeTradeHistoryTrade> trades;

    @JsonProperty("page")
    private BitcoindePage page;
}
