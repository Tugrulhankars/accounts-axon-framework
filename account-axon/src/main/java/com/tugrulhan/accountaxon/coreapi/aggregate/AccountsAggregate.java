package com.tugrulhan.accountaxon.coreapi.aggregate;


import com.tugrulhan.accountaxon.coreapi.command.CreateAccountCommand;
import com.tugrulhan.accountaxon.coreapi.command.DeleteAccountCommand;
import com.tugrulhan.accountaxon.coreapi.command.UpdateAccountCommand;
import com.tugrulhan.accountaxon.coreapi.event.AccountCreatedEvent;
import com.tugrulhan.accountaxon.coreapi.event.AccountDeletedEvent;
import com.tugrulhan.accountaxon.coreapi.event.AccountUpdatedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.modelling.command.TargetAggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

@Aggregate
public class AccountsAggregate {
    @AggregateIdentifier
    private Long accountNumber;
    private String firstName;
    private String lastName;
    private Integer age;
    private String emailAddress;
    private String mobileNumber;
    private String accountType;
    private String branchAddress;
    private boolean activeSw;
    private String errorMessage;

    public AccountsAggregate(){}

    @CommandHandler
    public AccountsAggregate(CreateAccountCommand createCommand){
        AccountCreatedEvent createdEvent=new AccountCreatedEvent();
        BeanUtils.copyProperties(createCommand,createdEvent);
        AggregateLifecycle.apply(createdEvent);
    }

    @EventSourcingHandler
    public void on(AccountCreatedEvent createdEvent){
        this.accountNumber=createdEvent.getAccountNumber();
        this.firstName=createdEvent.getFirstName();
        this.lastName=createdEvent.getLastName();
        this.age=createdEvent.getAge();
        this.emailAddress=createdEvent.getEmailAddress();
        this.mobileNumber=createdEvent.getMobileNumber();
        this.accountType=createdEvent.getAccountType();
        this.branchAddress=createdEvent.getBranchAddress();
        this.activeSw=createdEvent.isActiveSw();

    }

    @CommandHandler
    public void handle(UpdateAccountCommand updateCommand){
        AccountUpdatedEvent updatedEvent=new AccountUpdatedEvent();
        BeanUtils.copyProperties(updateCommand,updatedEvent);
        AggregateLifecycle.apply(updatedEvent);
    }

    @EventSourcingHandler
    public void on(AccountUpdatedEvent updatedEvent){
        this.accountType=updatedEvent.getAccountType();
        this.branchAddress=updatedEvent.getBranchAddress();
    }

    @CommandHandler
    public void on(DeleteAccountCommand deleteCommand){
        AccountDeletedEvent deletedEvent=new AccountDeletedEvent();
        BeanUtils.copyProperties(deleteCommand,deletedEvent);
        AggregateLifecycle.apply(deletedEvent);
    }

    @EventSourcingHandler
    public void on(AccountDeletedEvent deletedEvent){
        this.accountNumber=deletedEvent.getAccountNumber();
    }
}
