package com.fintech.service;

import com.fintech.domain.Account;
import com.fintech.repository.AccountRepository;
import com.fintech.util.LogFileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.ZoneId;
import java.util.Locale;

@Service
@Transactional
public class AuthService {
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private LogFileUtil logFileUtil;

    @Autowired
    private ReloadableResourceBundleMessageSource messageSource;


    public Account doLogin(Account account) {
        account.setStatus(Account.Status.ACTIVE.getInt());
        account.setBlockTime(Instant.now());

        Account accountResult = this.accountRepository.login(account).orElse(null);

        if (null != accountResult) {
            if (accountResult.matchPassword(account.getPassword())) {
                this.updateLoginFailed(accountResult, 0);
                this.updateStatus(Account.Status.ACTIVE.getInt(), accountResult);
                logFileUtil.logFile(accountResult, messageSource.getMessage("dang-nhap", null, Locale.getDefault()));
            } else {
                updateLoginFailed(accountResult, accountResult.getLoginFailed() + 1);
                this.updateTimeBlock(accountResult);
                return null;
            }
        }
        return accountResult;
    }

    public Account doRegister(Account account) {
        return this.accountRepository.save(account);
    }

    private void updateTimeBlock(Account account) {
        account.setBlockTime(Instant.now().atZone(ZoneId.systemDefault()).plusSeconds(5 * 60).toInstant());
        this.accountRepository.updateTimeBlock(account);
    }

    private void updateStatus(int status, Account account) {
        account.setStatus(status);
        this.accountRepository.updateStatus(account);
    }

    private void updateLoginFailed(Account account, int loginFailure) {
        account.setLoginFailed(loginFailure);
        this.accountRepository.updateLoginFailed(account);
        if (account.getLoginFailed() >= 5) {
            this.updateStatus(Account.Status.BLOCK.getInt(), account);
        }
    }

}
