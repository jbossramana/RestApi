package demo.boot.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import demo.boot.model.BankResponse;

@RestController
@RequestMapping("/bank")
public class BankController {

    @PostMapping("/deposit")
    public HttpEntity<BankResponse> deposit(
            @RequestParam String accountId,
            @RequestParam double amount) {

        // Process deposit (dummy example)
        BankResponse response = new BankResponse("Deposit successful", amount, accountId);

        // Add HATEOAS links to other operations
        response.add(linkTo(methodOn(BankController.class).deposit(accountId, amount))
                .withSelfRel()
                .withType("POST")
                .withTitle("Deposit money into account"));

        response.add(linkTo(methodOn(BankController.class).withdraw(accountId, amount))
                .withRel("withdraw")
                .withType("POST")
                .withTitle("Withdraw money from account"));

        response.add(linkTo(methodOn(BankController.class).getBalance(accountId))
                .withRel("balance")
                .withType("GET")
                .withTitle("Check account balance"));

        response.add(linkTo(methodOn(BankController.class).transfer(accountId, "otherAccountId", amount))
                .withRel("transfer")
                .withType("POST")
                .withTitle("Transfer money to another account"));

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/withdraw")
    public HttpEntity<String> withdraw(@RequestParam String accountId,
                                       @RequestParam double amount) {
        return ResponseEntity.ok("Withdrawn " + amount + " from account " + accountId);
    }

    @GetMapping("/balance")
    public HttpEntity<String> getBalance(@RequestParam String accountId) {
        return ResponseEntity.ok("Balance for account " + accountId + ": 1000.0");
    }

    @PostMapping("/transfer")
    public HttpEntity<String> transfer(@RequestParam String fromAccount,
                                       @RequestParam String toAccount,
                                       @RequestParam double amount) {
        return ResponseEntity.ok("Transferred " + amount + " from " + fromAccount + " to " + toAccount);
    }
}
