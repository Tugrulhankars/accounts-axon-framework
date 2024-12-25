package com.tugrulhan.accountaxon.query.handler;

import com.tugrulhan.accountaxon.constants.AccountsConstants;
import com.tugrulhan.accountaxon.coreapi.event.AccountCreatedEvent;
import com.tugrulhan.accountaxon.coreapi.event.AccountDeletedEvent;
import com.tugrulhan.accountaxon.coreapi.event.AccountUpdatedEvent;
import com.tugrulhan.accountaxon.coreapi.exception.ResourceNotFoundException;
import com.tugrulhan.accountaxon.dto.AccountDto;
import com.tugrulhan.accountaxon.entity.Accounts;
import com.tugrulhan.accountaxon.mapper.AccountMapper;
import com.tugrulhan.accountaxon.query.GetAccountQuery;
import com.tugrulhan.accountaxon.repository.AccountRepository;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class AccountQueryHandler {
    private final AccountRepository accountRepository;


    public AccountQueryHandler(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @EventHandler
    public void on(AccountCreatedEvent event){
        Accounts accounts=new Accounts();
        BeanUtils.copyProperties(event,accounts);
        accountRepository.save(accounts);
    }

    @EventHandler
    public void on(AccountUpdatedEvent event){
        Accounts account = accountRepository.findByMobileNumber(event.getMobileNumber()).orElseThrow(() -> new ResourceNotFoundException("Account", "mobileNumber",
                event.getMobileNumber()));
        AccountMapper.mapEventToAccount(event, account);
        accountRepository.save(account);

    }

    @EventHandler
    public void on(AccountDeletedEvent event){
        Accounts account = accountRepository.findById(event.getAccountNumber()).orElseThrow(
                () -> new ResourceNotFoundException("Account", "accountNumber",event.getAccountNumber().toString())
        );

        accountRepository.save(account);
    }

    @QueryHandler
    public AccountDto getAccount(GetAccountQuery query) {
        Accounts account = accountRepository.findByMobileNumber(query.getMobileNumber())
                .orElseThrow(() -> new ResourceNotFoundException("Account", "mobileNumber", query.getMobileNumber())
                );
        AccountDto accountsDto = AccountMapper.mapToAccountsDto(account, new AccountDto());
        return accountsDto;
    }
}
