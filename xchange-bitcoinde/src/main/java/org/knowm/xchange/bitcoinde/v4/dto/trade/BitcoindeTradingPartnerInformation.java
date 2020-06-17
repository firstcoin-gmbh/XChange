package org.knowm.xchange.bitcoinde.v4.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.knowm.xchange.bitcoinde.v4.dto.trade.BitcoindeTradingPartnerInformation.TrustLevel.TrustLevelDeserializer;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class BitcoindeTradingPartnerInformation {

    private final String username;
    private final boolean is_kyc_full;
    private final TrustLevel trust_level;
    private final Integer amount_trades;
    private final Integer rating;
    private final String bank_name;
    private final String bic;
    private final String seat_of_bank;

    public BitcoindeTradingPartnerInformation(
            @JsonProperty("username") String username,
            @JsonProperty("is_kyc_full") boolean is_kyc_full,
            @JsonProperty("trust_level") TrustLevel trust_level,
            @JsonProperty("amount_trades") Integer amount_trades,
            @JsonProperty("rating") Integer rating,
            @JsonProperty("bank_name") String bank_name,
            @JsonProperty("bic") String bic,
            @JsonProperty("seat_of_bank") String seat_of_bank) {
        this.username = username;
        this.is_kyc_full = is_kyc_full;
        this.trust_level = trust_level;
        this.amount_trades = amount_trades;
        this.rating = rating;
        this.bank_name = bank_name;
        this.bic = bic;
        this.seat_of_bank = seat_of_bank;
    }

    public String getUsername() {
        return username;
    }

    public boolean isKycFull() {
        return is_kyc_full;
    }

    public TrustLevel getTrustLevel() {
        return trust_level;
    }

    public Integer getAmountTrades() {
        return amount_trades;
    }

    public Integer getRating() {
        return rating;
    }

    public String getBankName() {
        return bank_name;
    }

    public String getBic() {
        return bic;
    }

    public String getSeatOfBank() {
        return seat_of_bank;
    }

    @JsonDeserialize(using = TrustLevelDeserializer.class)
    public static enum TrustLevel {
        BRONZE,
        SILVER,
        GOLD,
        PLATIN;

        private static final Map<String, TrustLevel> fromString = new HashMap<>();

        static {
            for (TrustLevel trustLevel : values()) fromString.put(trustLevel.toString(), trustLevel);
        }

        public static TrustLevel fromString(String trustLevelString) {

            TrustLevel trustLevel = fromString.get(trustLevelString.toLowerCase());
            if (trustLevel == null) {
                throw new RuntimeException("Not supported Bitcoinde trust level: " + trustLevelString);
            }
            return trustLevel;
        }

        @Override
        public String toString() {

            return super.toString().toLowerCase();
        }

        static class TrustLevelDeserializer extends JsonDeserializer<TrustLevel> {

            @Override
            public TrustLevel deserialize(JsonParser jsonParser, DeserializationContext ctxt)
                    throws IOException, JsonProcessingException {

                ObjectCodec oc = jsonParser.getCodec();
                JsonNode node = oc.readTree(jsonParser);
                String trustLevelString = node.textValue();
                return fromString(trustLevelString);
            }
        }
    }
}
