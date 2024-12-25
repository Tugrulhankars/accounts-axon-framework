package com.tugrulhan.accountaxon.gui;

import com.tugrulhan.accountaxon.dto.AccountDto;
import com.tugrulhan.accountaxon.query.GetAccountQuery;
import jakarta.validation.constraints.Pattern;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/accounts/queries")
public class AccountsQueryController {
    private final QueryGateway queryGateway;

    public AccountsQueryController(QueryGateway queryGateway) {
        this.queryGateway = queryGateway;
    }

    @GetMapping("/get")
    public ResponseEntity<AccountDto> fetchAccountDetails(@RequestParam("mobileNumber")
                                                          @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits")
                                                          String mobileNumber) {
        GetAccountQuery findAccountQuery = new GetAccountQuery(mobileNumber);
        AccountDto customer = queryGateway.query(findAccountQuery, ResponseTypes.instanceOf(AccountDto.class)).join();
        return ResponseEntity.status(org.springframework.http.HttpStatus.OK).body(customer);
    }
}
