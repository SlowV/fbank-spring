package com.fintech.web.rest;

import com.fintech.domain.Account;
import com.fintech.domain.AccountInformation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;

@RestController
@RequestMapping(value = "/api/v1/account")
public class AccountEndpoint {

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public Account getAccount() {
        AccountInformation accountInformation = AccountInformation.Builder.anAccountInformation()
                .setAddress("252 Phúc diễn")
                .setEmail("quocviet.hn98@gmail.com")
                .setGender(AccountInformation.Gender.MALE.getCode())
                .setIntroduce("Đẹp trai!")
                .setFullName("Trịnh Quốc Việt")
                .setPhone("0349555602")
                .build();

        return Account.Builder.anAccount()
                .setUsername("ben1998em")
                .setPassword("viet1998")
                .setAccountInformation(accountInformation)
                .setStatus(Account.Status.ACTIVE.getInt())
                .setBlockTime(Instant.now())
                .build();
    }
}
