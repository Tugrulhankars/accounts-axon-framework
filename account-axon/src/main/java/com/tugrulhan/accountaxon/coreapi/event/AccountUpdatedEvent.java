package com.tugrulhan.accountaxon.coreapi.event;

import lombok.Data;

@Data
public class AccountUpdatedEvent {
    private Long accountNumber;
    private  String firstName;
    private  String lastName;
    private Integer age;
    private String emailAddress;
    private String mobileNumber;
    private String accountType;
    private String branchAddress;
    private boolean activeSw;
}
