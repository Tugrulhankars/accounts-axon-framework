package com.tugrulhan.accountaxon.coreapi.interceptor;

import com.tugrulhan.accountaxon.constants.AccountsConstants;
import com.tugrulhan.accountaxon.coreapi.command.CreateAccountCommand;
import com.tugrulhan.accountaxon.coreapi.command.DeleteAccountCommand;
import com.tugrulhan.accountaxon.coreapi.command.UpdateAccountCommand;
import com.tugrulhan.accountaxon.coreapi.exception.ResourceNotFoundException;
import com.tugrulhan.accountaxon.entity.Accounts;
import com.tugrulhan.accountaxon.repository.AccountRepository;
import org.axonframework.commandhandling.CommandMessage;
import org.axonframework.messaging.MessageDispatchInterceptor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;

@Component
public class AccountsInterceptors implements MessageDispatchInterceptor<CommandMessage<?>> {

    private final AccountRepository accountRepository;

    public AccountsInterceptors(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public BiFunction<Integer, CommandMessage<?>, CommandMessage<?>> handle(List<? extends CommandMessage<?>> list) {
        return (index, command) ->{
            if (CreateAccountCommand.class.equals(command.getPayloadType())){
                CreateAccountCommand createAccountCommand=(CreateAccountCommand) command.getPayload();
                Optional<Accounts> optionalAccounts=accountRepository.findByMobileNumber(
                        createAccountCommand.getMobileNumber()
                );
                if (optionalAccounts.isPresent()){
                    throw new  RuntimeException("Account already exists");
                }
            }else if (UpdateAccountCommand.class.equals(command.getPayloadType())){
                UpdateAccountCommand updateAccountCommand=(UpdateAccountCommand) command.getPayload();
                Accounts accounts=accountRepository.findByMobileNumber(
                        updateAccountCommand.getMobileNumber()
                ).orElse(null);
            }else if (DeleteAccountCommand.class.equals(command.getPayloadType())){
                DeleteAccountCommand deleteAccountCommand = (DeleteAccountCommand) command.getPayload();
                Accounts account = accountRepository.findByAccountNumber(deleteAccountCommand.getAccountNumber()).orElseThrow(() -> new ResourceNotFoundException("Account", "accountNumber",
                        deleteAccountCommand.getAccountNumber().toString()));
            }
            return command;
        };

    }
}
