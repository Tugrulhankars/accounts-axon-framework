package com.tugrulhan.accountaxon.gui;

import com.tugrulhan.accountaxon.constants.AccountsConstants;
import com.tugrulhan.accountaxon.coreapi.command.CreateAccountCommand;
import com.tugrulhan.accountaxon.coreapi.command.DeleteAccountCommand;
import com.tugrulhan.accountaxon.coreapi.command.UpdateAccountCommand;
import com.tugrulhan.accountaxon.dto.AccountDto;
import com.tugrulhan.accountaxon.dto.ResponseDto;
import jakarta.validation.Valid;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Random;

@RestController
@RequestMapping("/api/v1/accounts/commands")
public class AccountsCommandController {
    private final CommandGateway commandGateway;

    public AccountsCommandController(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createAccount(@RequestBody CreateAccountCommand createAccountCommand){
        long randomAccNumber = 1000000000L + new Random().nextInt(900000000);
        createAccountCommand.setAccountNumber(randomAccNumber);
        commandGateway.sendAndWait(createAccountCommand);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto(AccountsConstants.STATUS_201, AccountsConstants.MESSAGE_201));
    }

    @PutMapping("/update")
    public ResponseEntity<ResponseDto> updateAccountDetails(@Valid @RequestBody AccountDto accountsDto) {
        UpdateAccountCommand updateCommand = UpdateAccountCommand.builder()
                .accountNumber(accountsDto.getAccountNumber()).mobileNumber(accountsDto.getMobileNumber())
                        .accountType(accountsDto.getAccountType()).build();
        commandGateway.sendAndWait(updateCommand);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseDto(AccountsConstants.STATUS_200, AccountsConstants.MESSAGE_200));
    }

    @PatchMapping("/delete")
    public ResponseEntity<ResponseDto> deleteAccountDetails(@RequestParam("accountNumber") Long accountNumber) {
        DeleteAccountCommand deleteCommand = DeleteAccountCommand.builder()
                .accountNumber(accountNumber).activeSw(AccountsConstants.IN_ACTIVE_SW).build();
        commandGateway.sendAndWait(deleteCommand);
        return ResponseEntity
                .status(org.springframework.http.HttpStatus.OK)
                .body(new ResponseDto(AccountsConstants.STATUS_200, AccountsConstants.MESSAGE_200));
    }

}
