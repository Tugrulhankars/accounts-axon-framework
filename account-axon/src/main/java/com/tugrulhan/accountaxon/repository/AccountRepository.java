package com.tugrulhan.accountaxon.repository;

import com.tugrulhan.accountaxon.entity.Accounts;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Accounts, Long> {

    Optional<Accounts> findByMobileNumber(String mobileNumber);
    Optional<Accounts> findByAccountNumber(Long accountNumber);
}
