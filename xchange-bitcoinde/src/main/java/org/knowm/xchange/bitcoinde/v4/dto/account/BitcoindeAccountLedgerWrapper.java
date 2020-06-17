package org.knowm.xchange.bitcoinde.v4.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.bitcoinde.v4.dto.BitcoindePage;

import java.math.BigDecimal;
import java.util.*;

public class BitcoindeAccountLedgerWrapper {
    private final List<BitcoindeAccountLedger> account_ledger;
    private final BitcoindePage page;
    private final List<Object> errors;
    private final Integer credits;

    public BitcoindeAccountLedgerWrapper(
            @JsonProperty("account_ledger") List<BitcoindeAccountLedger> account_ledger,
            @JsonProperty("page") BitcoindePage page,
            @JsonProperty("errors") List<Object> errors,
            @JsonProperty("credits") Integer credits) {
        this.account_ledger = account_ledger;
        this.page = page;
        this.errors = errors;
        this.credits = credits;
    }

    public List<BitcoindeAccountLedger> getAccountLedger() {
        return account_ledger;
    }

    public BitcoindePage getPage() {
        return page;
    }

    @JsonProperty("errors")
    public List<Object> getErrors() {
        return errors;
    }

    @JsonProperty("credits")
    public Integer getCredits() {
        return credits;
    }

    /**
     * Split the account ledger into fees and other ledger entries.
     * @return BitcoindeSplitAccountLedgerWrapper holding fees and other entries as two separate lists.
     */
    public BitcoindeSplitAccountLedgerWrapper splitAccountLedger() {
        LinkedList<BitcoindeAccountLedger> accountLedgerEntries = new LinkedList<>();
        LinkedList<BigDecimal> fees = new LinkedList<>();

        for (BitcoindeAccountLedger accountLedgerEntry: account_ledger) {
            BitcoindeLedgerType type;
            try {
                type = BitcoindeLedgerType.fromString(accountLedgerEntry.getType());
            }
            catch (RuntimeException e) {
                // Unknown ledger type
                continue;
            }

            if (type == BitcoindeLedgerType.OUTGOING_FEE_VOLUNTARY) {
                fees.add(accountLedgerEntry.getCashflow().abs());
            }
            else {
                accountLedgerEntries.add(accountLedgerEntry);
            }
        }

        return new BitcoindeSplitAccountLedgerWrapper(accountLedgerEntries, fees);
    }

    /**
     * Helper class that stores fees separately from other ledger entries.
     * The lists that are held by an object are intended to be consumed.
     * An object should, thus, only be reused if you know what you are doing.
     * Otherwise, create a new copy by using BitcoindeAccountLedgerWrapper.splitAccountLedger().
     */
    public class BitcoindeSplitAccountLedgerWrapper {

        private LinkedList<BitcoindeAccountLedger> accountLedgerEntries;
        private LinkedList<BigDecimal> accountLedgerFees;

        public BitcoindeSplitAccountLedgerWrapper(LinkedList<BitcoindeAccountLedger> accountLedgerEntries,
                                                  LinkedList<BigDecimal> accountLedgerFees) {
            this.accountLedgerEntries = accountLedgerEntries;
            this.accountLedgerFees = accountLedgerFees;
        }

        public LinkedList<BitcoindeAccountLedger> getAccountLedgerEntries() {
            return accountLedgerEntries;
        }

        public LinkedList<BigDecimal> getAccountLedgerFees() {
            return accountLedgerFees;
        }

        public void setAccountLedgerEntries(LinkedList<BitcoindeAccountLedger> accountLedgerEntries) {
            this.accountLedgerEntries = accountLedgerEntries;
        }

        public void setAccountLedgerFees(LinkedList<BigDecimal> accountLedgerFees) {
            this.accountLedgerFees = accountLedgerFees;
        }

    }

}

