package com.fintech.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;

@Setter
@Getter
@NoArgsConstructor
@Entity(name = "Account")
@Table(name = "account")
public class Account implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, length = 30, nullable = false, name = "username")
    private String username;

    @Column(nullable = false, name = "password")
    private String password;

    @Column(columnDefinition = "integer default 0", name = "login_failed", nullable = false)
    private int loginFailed;

    @Column(name = "time_block", nullable = false)
    @CreationTimestamp
    @DateTimeFormat(pattern = "dd-MM-yyyy hh:mm:ss")
    private Instant blockTime;

    @Column(name = "status", columnDefinition = "int default 1", nullable = false)
    private Integer status;

    @OneToOne(mappedBy = "account", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private AccountInformation accountInformation;

    public boolean matchPassword(String password) {
        return this.password.equals(password);
    }

    public enum Status {
        ACTIVE(1), BLOCK(-1);
        private int number;

        Status(int number) {
            this.number = number;
        }

        public int getInt() {
            return number;
        }

        public static Status getStatusByValue(int value) {
            for (Status status : Status.values()) {
                if (status.number == value) return status;
            }
            throw new IllegalArgumentException("Kiểu trạng thái không tồn tại!");
        }
    }


    public static final class Builder {
        private Long id;
        private String username;
        private String password;
        private Integer loginFailed;
        private Instant blockTime;
        private Integer status;
        private AccountInformation accountInformation;

        private Builder() {
        }

        public static Builder anAccount() {
            return new Builder();
        }

        public Builder setId(Long id) {
            this.id = id;
            return this;
        }

        public Builder setUsername(String username) {
            this.username = username;
            return this;
        }

        public Builder setPassword(String password) {
            this.password = password;
            return this;
        }

        public Builder setLoginFailed(Integer loginFailed) {
            this.loginFailed = loginFailed;
            return this;
        }

        public Builder setBlockTime(Instant blockTime) {
            this.blockTime = blockTime;
            return this;
        }

        public Builder setStatus(Integer status) {
            this.status = status;
            return this;
        }

        public Builder setAccountInformation(AccountInformation accountInformation) {
            this.accountInformation = accountInformation;
            return this;
        }

        public Account build() {
            Account account = new Account();
            account.setId(id);
            account.setUsername(username);
            account.setPassword(password);
            if (loginFailed != null) {
                account.setLoginFailed(loginFailed);
            }
            account.setBlockTime(blockTime);
            account.setStatus(status);
            account.setAccountInformation(accountInformation);
            if (accountInformation != null) {
                accountInformation.setAccount(account);
            }
            return account;
        }
    }
}