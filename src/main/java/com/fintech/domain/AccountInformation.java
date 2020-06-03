package com.fintech.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.io.Serializable;

@Setter
@Getter
@NoArgsConstructor
@Entity(name = "AccountInformation")
@Table(name = "account_information")
public class AccountInformation implements Serializable {
    @Id
    @OneToOne
    @JoinColumn(name = "account_id")
    @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
    @JsonIgnore
    private Account account;

    @Column(name = "full_name", nullable = false)
    private String fullName;
    @Column(name = "gender")
    private int gender;
    @Column(name = "phone")
    private String phone;
    @Column(name = "address")
    private String address;
    @Column(name = "email")
    private String email;
    @Column(name = "introduce", columnDefinition = "TEXT")
    private String introduce;

    public enum Gender {
        MALE(1, "MALE"), FEMALE(2, "FEMALE"), OTHER(0, "FEMALE");
        private int code;
        private String nameString;
        Gender(int code, String nameString) {
            this.code = code;
            this.nameString = nameString;
        }

        public int getCode() {
            return code;
        }

        public static Gender getStatusByValue(int value) {
            for (Gender gender : Gender.values()) {
                if (gender.code == value) return gender;
            }
            throw new IllegalArgumentException("Giới tính không tồn tại.");
        }

        public String getNameString() {
            return nameString;
        }
    }

    public static final class Builder {
        private Account account;
        private String fullName;
        private int gender;
        private String phone;
        private String address;
        private String email;
        private String introduce;

        private Builder() {
        }

        public static Builder anAccountInformation() {
            return new Builder();
        }

        public Builder setAccount(Account account) {
            this.account = account;
            return this;
        }

        public Builder setFullName(String fullName) {
            this.fullName = fullName;
            return this;
        }

        public Builder setGender(int gender) {
            this.gender = gender;
            return this;
        }

        public Builder setPhone(String phone) {
            this.phone = phone;
            return this;
        }

        public Builder setAddress(String address) {
            this.address = address;
            return this;
        }

        public Builder setEmail(String email) {
            this.email = email;
            return this;
        }

        public Builder setIntroduce(String introduce) {
            this.introduce = introduce;
            return this;
        }

        public AccountInformation build() {
            AccountInformation accountInformation = new AccountInformation();
            accountInformation.setAccount(account);
            accountInformation.setFullName(fullName);
            accountInformation.setGender(gender);
            accountInformation.setPhone(phone);
            accountInformation.setAddress(address);
            accountInformation.setEmail(email);
            accountInformation.setIntroduce(introduce);
            return accountInformation;
        }
    }
}
