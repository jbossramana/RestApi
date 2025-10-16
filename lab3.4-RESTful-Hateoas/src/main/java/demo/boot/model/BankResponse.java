package demo.boot.model;

import java.time.Instant;

import org.springframework.hateoas.RepresentationModel;

public class BankResponse extends RepresentationModel<BankResponse> {

    private final String message;
    private final double amount;
    private final String accountId;
    private final Instant timestamp;

    public BankResponse(String message, double amount, String accountId) {
        this.message = message;
        this.amount = amount;
        this.accountId = accountId;
        this.timestamp = Instant.now();
    }

    public String getMessage() {
        return message;
    }

    public double getAmount() {
        return amount;
    }

    public String getAccountId() {
        return accountId;
    }
    
    public Instant getTimestamp() {
        return timestamp;
    }
}
