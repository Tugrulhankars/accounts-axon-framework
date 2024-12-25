package com.tugrulhan.accountaxon.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "accounts")
public class Accounts extends BaseEntity {

    @Id
    private Long accountNumber;
    private String firstName;
    private String lastName;
    private Integer age;
    private String emailAddress;
    private String accountType;
    private String mobileNumber;
}
