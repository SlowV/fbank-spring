package com.fintech.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "person")
public class Person implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private Integer age;
    private Double salary;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date dob;
    private int status;

    public enum StatusType {
        MALE(1, "ALL"), FEMALE(2, "ACTIVE"), OTHER(0, "INACTIVE");
        private int code;
        private String nameStr;
        StatusType(int code, String name) {
            this.code = code;
            this.nameStr = name;
        }

        public int getCode() {
            return code;
        }

        public static StatusType getStatusByValue(int value) {
            for (StatusType status : StatusType.values()) {
                if (status.code == value) return status;
            }
            throw new IllegalArgumentException("Trạng thái không tồn tại.");
        }

        public String getNameStr() {
            return nameStr;
        }
    }
}
