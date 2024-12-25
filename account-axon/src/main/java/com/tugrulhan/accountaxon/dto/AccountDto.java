package com.tugrulhan.accountaxon.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class AccountDto {
    private Long accountNumber;
    @NotEmpty(message = "FirstName can not be a null or empty")
    private String firstName;
    @NotEmpty(message = "LastName can not be a null or empty")
    private String lastName;
    private Integer age;
    @NotEmpty(message = "EmailAddress can not be a null or empty")
    private String emailAddress;
    private String mobileNumber;

    @NotEmpty(message = "AccountType can not be a null or empty")
    private String accountType;


}
