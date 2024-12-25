package com.tugrulhan.accountaxon.coreapi.command;

import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@Builder
public class CreateAccountCommand {

    @TargetAggregateIdentifier
    private Long accountNumber;
    private String firstName;
    private String lastName;
    private Integer age;
    private String emailAddress;
    private String mobileNumber;
    private String accountType;

}
