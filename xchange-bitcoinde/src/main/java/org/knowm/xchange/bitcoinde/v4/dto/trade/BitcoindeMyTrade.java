package org.knowm.xchange.bitcoinde.v4.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.knowm.xchange.bitcoinde.v4.dto.trade.BitcoindeMyTrade.Type.TradeTypeDeserializer;
import org.knowm.xchange.bitcoinde.v4.dto.trade.BitcoindeMyTrade.State.TradeStateDeserializer;
import org.knowm.xchange.bitcoinde.v4.dto.trade.BitcoindeMyTrade.PaymentMethod.PaymentMethodDeserializer;
import org.knowm.xchange.currency.CurrencyPair;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class BitcoindeMyTrade {

    private final String trade_id;
    private final CurrencyPair trading_pair;
    private final boolean is_external_wallet_trade;
    private final Type type;
    private final BigDecimal amount_currency_to_trade;
    private final BigDecimal price;
    private final BigDecimal volume_currency_to_pay;
    private final BigDecimal volume_currency_to_pay_after_fee;
    private final BigDecimal amount_currency_to_trade_after_fee;
    private final BigDecimal fee_currency_to_pay;
    private final BigDecimal fee_currency_to_trade;
    private final String created_at;
    private final String successfully_finished_at;
    private final State state;
    private final boolean is_trade_marked_as_paid;
    private final String trade_marked_as_paid_at;
    private final PaymentMethod payment_method;
    private final String my_rating_for_trading_partner;
    private final BitcoindeTradingPartnerInformation trading_partner_information;

    public BitcoindeMyTrade(
            @JsonProperty("trade_id") String trade_id,
            @JsonProperty("trading_pair") String trading_pair,
            @JsonProperty("is_external_wallet_trade") boolean is_external_wallet_trade,
            @JsonProperty("type") Type type,
            @JsonProperty("amount_currency_to_trade") BigDecimal amount_currency_to_trade,
            @JsonProperty("price") BigDecimal price,
            @JsonProperty("volume_currency_to_pay") BigDecimal volume_currency_to_pay,
            @JsonProperty("volume_currency_to_pay_after_fee") BigDecimal volume_currency_to_pay_after_fee,
            @JsonProperty("amount_currency_to_trade_after_fee") BigDecimal amount_currency_to_trade_after_fee,
            @JsonProperty("fee_currency_to_pay") BigDecimal fee_currency_to_pay,
            @JsonProperty("fee_currency_to_trade") BigDecimal fee_currency_to_trade,
            @JsonProperty("created_at") String created_at,
            @JsonProperty("successfully_finished_at") String successfully_finished_at,
            @JsonProperty("state") State state,
            @JsonProperty("is_trade_marked_as_paid") boolean is_trade_marked_as_paid,
            @JsonProperty("trade_marked_as_paid_at") String trade_marked_as_paid_at,
            @JsonProperty("payment_method") PaymentMethod payment_method,
            @JsonProperty("my_rating_for_trading_partner") String my_rating_for_trading_partner,
            @JsonProperty("trading_partner_information") BitcoindeTradingPartnerInformation trading_partner_information) {
        this.trade_id = trade_id;
        trading_pair = trading_pair.substring(0, 3) + "/" + trading_pair.substring(3);
        this.trading_pair = new CurrencyPair(trading_pair);
        this.is_external_wallet_trade = is_external_wallet_trade;
        this.type = type;
        this.amount_currency_to_trade = amount_currency_to_trade;
        this.price = price;
        this.volume_currency_to_pay = volume_currency_to_pay;
        this.volume_currency_to_pay_after_fee = volume_currency_to_pay_after_fee;
        this.amount_currency_to_trade_after_fee = amount_currency_to_trade_after_fee;
        this.fee_currency_to_pay = fee_currency_to_pay;
        this.fee_currency_to_trade = fee_currency_to_trade;
        this.created_at = created_at;
        this.successfully_finished_at = successfully_finished_at;
        this.state = state;
        this.is_trade_marked_as_paid = is_trade_marked_as_paid;
        this.trade_marked_as_paid_at = trade_marked_as_paid_at;
        this.payment_method = payment_method;
        this.my_rating_for_trading_partner = my_rating_for_trading_partner;
        this.trading_partner_information = trading_partner_information;
    }

    public String getTradeId() {
        return trade_id;
    }

    public CurrencyPair getTradingPair() {
        return trading_pair;
    }

    public boolean isExternalWalletTrade() {
        return is_external_wallet_trade;
    }

    public Type getType() {
        return type;
    }

    public BigDecimal getAmountCurrencyToTrade() {
        return amount_currency_to_trade;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public BigDecimal getVolumeCurrencyToPay() {
        return volume_currency_to_pay;
    }

    public BigDecimal getVolumeCurrencyToPayAfterFee() {
        return volume_currency_to_pay_after_fee;
    }

    public BigDecimal getAmountCurrencyToTradeAfterFee() {
        return amount_currency_to_trade_after_fee;
    }

    public BigDecimal getFeeCurrencyToPay() {
        return fee_currency_to_pay;
    }

    public BigDecimal getFeeCurrencyToTrade() {
        return fee_currency_to_trade;
    }

    public String getCreatedAt() {
        return created_at;
    }

    public String getSuccessfullyFinishedAt() {
        return successfully_finished_at;
    }

    public State getState() {
        return state;
    }

    public boolean isTradeMarkedAsPaid() {
        return is_trade_marked_as_paid;
    }

    public String getTradeMarkedAsPaidAt() {
        return trade_marked_as_paid_at;
    }

    public PaymentMethod getPaymentMethod() {
        return payment_method;
    }

    public String getMyRatingForTradingPartner() {
        return my_rating_for_trading_partner;
    }

    public BitcoindeTradingPartnerInformation getTradingPartnerInformation() {
        return trading_partner_information;
    }

    @Override
    public String toString() {
        return "BitcoindeTrade{" +
                "trade_id='" + trade_id + '\'' +
                ", trading_pair=" + trading_pair +
                ", is_external_wallet_trade=" + is_external_wallet_trade +
                ", type=" + type +
                ", amount_currency_to_trade=" + amount_currency_to_trade +
                ", price=" + price +
                ", volume_currency_to_pay=" + volume_currency_to_pay +
                ", volume_currency_to_pay_after_fee=" + volume_currency_to_pay_after_fee +
                ", amount_currency_to_trade_after_fee=" + amount_currency_to_trade_after_fee +
                ", fee_currency_to_pay=" + fee_currency_to_pay +
                ", fee_currency_to_trade=" + fee_currency_to_trade +
                ", created_at='" + created_at + '\'' +
                ", successfully_finished_at='" + successfully_finished_at + '\'' +
                ", state=" + state +
                ", is_trade_marked_as_paid=" + is_trade_marked_as_paid +
                ", trade_marked_as_paid_at='" + trade_marked_as_paid_at + '\'' +
                ", payment_method=" + payment_method +
                ", my_rating_for_trading_partner='" + my_rating_for_trading_partner + '\'' +
                ", trading_partner_information=" + trading_partner_information +
                '}';
    }

    @JsonDeserialize(using = TradeTypeDeserializer.class)
    public enum Type {
        BUY,
        SELL;

        private static final Map<String, Type> fromString = new HashMap<>();

        static {
            for (Type ledgerType : values()) fromString.put(ledgerType.toString(), ledgerType);
        }

        public static Type fromString(String ledgerTypeString) {

            Type ledgerType = fromString.get(ledgerTypeString.toLowerCase());
            if (ledgerType == null) {
                throw new RuntimeException("Not supported Bitcoinde ledger type: " + ledgerTypeString);
            }
            return ledgerType;
        }

        @Override
        public String toString() {

            return super.toString().toLowerCase();
        }

        static class TradeTypeDeserializer extends JsonDeserializer<Type> {

            @Override
            public Type deserialize(JsonParser jsonParser, DeserializationContext ctxt)
                    throws IOException, JsonProcessingException {

                ObjectCodec oc = jsonParser.getCodec();
                JsonNode node = oc.readTree(jsonParser);
                String ledgerTypeString = node.textValue();
                return fromString(ledgerTypeString);
            }
        }
    }

    @JsonDeserialize(using = TradeStateDeserializer.class)
    public static enum State {
        CANCELLED(-1),
        PENDING(0),
        SUCCESSFUL(1);

        private int code;

        private static final Map<String, State> fromString = new HashMap<>();

        static {
            for (State tradeState : values()) fromString.put(tradeState.toString(), tradeState);
        }

        private static final Map<Integer, State> fromInt = new HashMap<>();

        static {
            for (State tradeState : values()) fromInt.put(tradeState.code, tradeState);
        }

        State(int code) {
            this.code = code;
        }

        public static State fromString(String tradeStateString) {

            State tradeState = fromString.get(tradeStateString.toLowerCase());
            if (tradeState == null) {
                throw new RuntimeException("Not supported Bitcoinde trade state: " + tradeStateString);
            }
            return tradeState;
        }

        public static State fromInt(int code) {

            State tradeState = fromInt.get(code);
            if (tradeState == null) {
                throw new RuntimeException("Not supported Bitcoinde trade state code: " + code);
            }
            return tradeState;
        }

        @Override
        public String toString() {

            return super.toString().toLowerCase();
        }

        public int toInt() {
            return code;
        }

        static class TradeStateDeserializer extends JsonDeserializer<State> {

            @Override
            public State deserialize(JsonParser jsonParser, DeserializationContext ctxt)
                    throws IOException, JsonProcessingException {

                ObjectCodec oc = jsonParser.getCodec();
                JsonNode node = oc.readTree(jsonParser);
                int tradeStateInt = node.intValue();
                return fromInt(tradeStateInt);
            }
        }
    }

    @JsonDeserialize(using = PaymentMethodDeserializer.class)
    public static enum PaymentMethod {
        SEPA(1),
        EXPRESS(2);

        private int code;

        private static final Map<String, PaymentMethod> fromString = new HashMap<>();

        static {
            for (PaymentMethod tradeState : values()) fromString.put(tradeState.toString(), tradeState);
        }

        private static final Map<Integer, PaymentMethod> fromInt = new HashMap<>();

        static {
            for (PaymentMethod tradeState : values()) fromInt.put(tradeState.code, tradeState);
        }

        PaymentMethod(int code) {
            this.code = code;
        }

        public static PaymentMethod fromString(String paymentMethodString) {

            PaymentMethod paymentMethod = fromString.get(paymentMethodString.toLowerCase());
            if (paymentMethod == null) {
                throw new RuntimeException("Not supported Bitcoinde trade payment method: " + paymentMethodString);
            }
            return paymentMethod;
        }

        public static PaymentMethod fromInt(int code) {

            PaymentMethod paymentMethod = fromInt.get(code);
            if (paymentMethod == null) {
                throw new RuntimeException("Not supported Bitcoinde trade payment method code: " + code);
            }
            return paymentMethod;
        }

        @Override
        public String toString() {

            return super.toString().toLowerCase();
        }

        static class PaymentMethodDeserializer extends JsonDeserializer<PaymentMethod> {

            @Override
            public PaymentMethod deserialize(JsonParser jsonParser, DeserializationContext ctxt)
                    throws IOException, JsonProcessingException {

                ObjectCodec oc = jsonParser.getCodec();
                JsonNode node = oc.readTree(jsonParser);
                int paymentMethodInt = node.intValue();
                return fromInt(paymentMethodInt);
            }
        }
    }

}
