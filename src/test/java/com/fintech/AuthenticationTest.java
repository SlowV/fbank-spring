package com.fintech;

import com.fintech.config.JpaConfig;
import com.fintech.domain.Account;
import com.fintech.domain.AccountInformation;
import com.fintech.repository.AccountRepository;
import com.fintech.service.AuthService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = JpaConfig.class)
@WebAppConfiguration
public class AuthenticationTest {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AuthService authService;
    
    @Test
    public void itShouldRegisterSaveSuccess() {
        AccountInformation accountInformation = AccountInformation.Builder.anAccountInformation()
                .setAddress("252 Phúc diễn")
                .setEmail("quocviet.hn98@gmail.com")
                .setGender(AccountInformation.Gender.MALE.getCode())
                .setIntroduce("Đẹp trai!")
                .setFullName("Trịnh Quốc Việt")
                .setPhone("0349555602")
                .build();

        Account account = Account.Builder.anAccount()
                .setUsername("ben1998em")
                .setPassword("viet1998")
                .setAccountInformation(accountInformation)
                .setStatus(Account.Status.ACTIVE.getInt())
                .build();

        Account accountResult = authService.doRegister(account);
        assertThat(accountResult.getUsername()).isEqualTo(account.getUsername());
    }

    @Test
    public void itShouldLoginSuccess() {
        Account account = Account.Builder.anAccount()
                .setUsername("ben1998em")
                .setPassword("viet1998")
                .build();
        Account accountResult = authService.doLogin(account);
        assertThat(accountResult).isNotNull();
        assertThat(accountResult.getUsername()).isEqualTo(account.getUsername());
    }

    @Test
    public void itShouldLoginFailureByPasswordAndUsernameNotMatch() {
        Account account = Account.Builder.anAccount()
                .setUsername("ben1998em11")
                .setPassword("abcxyz")
                .build();
        Account accountResult = authService.doLogin(account);
        assertThat(accountResult).isNull();
    }
}
