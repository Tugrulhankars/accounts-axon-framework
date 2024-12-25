package com.tugrulhan.accountaxon.mapper;

import com.tugrulhan.accountaxon.coreapi.event.AccountUpdatedEvent;
import com.tugrulhan.accountaxon.dto.AccountDto;
import com.tugrulhan.accountaxon.entity.Accounts;

public class AccountMapper {
    public static AccountDto mapToAccountsDto(Accounts accounts, AccountDto accountsDto) {
        accountsDto.setAccountNumber(accounts.getAccountNumber());
        accountsDto.setFirstName(accounts.getFirstName());
        accountsDto.setLastName(accounts.getLastName());
        accountsDto.setAge(accounts.getAge());
        accountsDto.setEmailAddress(accounts.getEmailAddress());
        accountsDto.setMobileNumber(accounts.getMobileNumber());
        accountsDto.setAccountType(accounts.getAccountType());

        return accountsDto;
    }

    public static Accounts mapToAccounts(AccountDto accountsDto, Accounts accounts) {
        accounts.setAccountType(accountsDto.getAccountType());

        return accounts;
    }

    public static Accounts mapEventToAccount(AccountUpdatedEvent event, Accounts account) {
        account.setAccountType(event.getAccountType());
        return account;
    }
}
