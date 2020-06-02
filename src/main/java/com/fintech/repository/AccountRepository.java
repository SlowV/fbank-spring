package com.fintech.repository;

import com.fintech.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    @Query("from Account a where a.username = :#{#account.username} and a.status = :#{#account.status} or a.blockTime <= :#{#account.blockTime} ")
    Optional<Account> login(
            @Param("account") Account account
    );

    @Transactional
    @Modifying
    @Query("update Account a set a.blockTime = :#{#account.blockTime} where a.username = :#{#account.username}")
    void updateTimeBlock(
            @Param("account") Account account
    );

    @Transactional
    @Modifying
    @Query("update Account a set a.status = :#{#account.status} where a.username = :#{#account.username}")
    void updateStatus(
            @Param("account") Account account
    );

    @Transactional
    @Modifying
    @Query("update Account a set a.loginFailed = :#{#account.loginFailed} where a.username = :#{#account.username}")
    void updateLoginFailed(
            @Param("account") Account account
    );
}
