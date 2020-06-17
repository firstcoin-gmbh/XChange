package org.knowm.xchange.bitcoinde.v4.dto.account;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.knowm.xchange.bitcoinde.v4.dto.account.BitcoindeLedgerType.LedgerTypeDeserializer;
import org.knowm.xchange.dto.account.FundingRecord;

@JsonDeserialize(using = LedgerTypeDeserializer.class)
public enum BitcoindeLedgerType {
    ALL,
    BUY,
    SELL,
    INPAYMENT,
    PAYOUT,
    AFFILIATE,
    WELCOME_BTC,
    BUY_YUBIKEY,
    BUY_GOLDSHOP,
    BUY_DIAMONDSHOP,
    KICKBACK,
    OUTGOING_FEE_VOLUNTARY;

    private static final Map<String, BitcoindeLedgerType> fromString = new HashMap<>();

    static {
        for (BitcoindeLedgerType ledgerType : values()) fromString.put(ledgerType.toString(), ledgerType);
    }

    private static final Map<String, FundingRecord.Type> fromStringFundingRecord = new HashMap<>();

    static {
        fromStringFundingRecord.put(BUY.toString(), FundingRecord.Type.INTERNAL_DEPOSIT);
        fromStringFundingRecord.put(SELL.toString(), FundingRecord.Type.INTERNAL_WITHDRAWAL);
        fromStringFundingRecord.put(INPAYMENT.toString(), FundingRecord.Type.DEPOSIT);
        fromStringFundingRecord.put(PAYOUT.toString(), FundingRecord.Type.WITHDRAWAL);
        fromStringFundingRecord.put(AFFILIATE.toString(), FundingRecord.Type.OTHER_INFLOW);
        fromStringFundingRecord.put(WELCOME_BTC.toString(), FundingRecord.Type.OTHER_INFLOW);
        fromStringFundingRecord.put(BUY_YUBIKEY.toString(), FundingRecord.Type.OTHER_OUTFLOW);
        fromStringFundingRecord.put(BUY_DIAMONDSHOP.toString(), FundingRecord.Type.OTHER_OUTFLOW);
        fromStringFundingRecord.put(BUY_GOLDSHOP.toString(), FundingRecord.Type.OTHER_OUTFLOW);
        fromStringFundingRecord.put(KICKBACK.toString(), FundingRecord.Type.KICKBACK);
        fromStringFundingRecord.put(OUTGOING_FEE_VOLUNTARY.toString(), FundingRecord.Type.OTHER_OUTFLOW);
    }

    public static BitcoindeLedgerType fromString(String ledgerTypeString) {

        BitcoindeLedgerType ledgerType = fromString.get(ledgerTypeString.toLowerCase());
        if (ledgerType == null) {
            throw new RuntimeException("Not supported Bitcoinde ledger type: " + ledgerTypeString);
        }
        return ledgerType;
    }

    public FundingRecord.Type toFundingRecordType() {
        String ledgerTypeString = this.toString();
        return fromStringFundingRecord.get(ledgerTypeString);
    }

    @Override
    public String toString() {

        return super.toString().toLowerCase();
    }

    static class LedgerTypeDeserializer extends JsonDeserializer<BitcoindeLedgerType> {

        @Override
        public BitcoindeLedgerType deserialize(JsonParser jsonParser, DeserializationContext ctxt)
                throws IOException, JsonProcessingException {

            ObjectCodec oc = jsonParser.getCodec();
            JsonNode node = oc.readTree(jsonParser);
            String ledgerTypeString = node.textValue();
            return fromString(ledgerTypeString);
        }
    }
}
