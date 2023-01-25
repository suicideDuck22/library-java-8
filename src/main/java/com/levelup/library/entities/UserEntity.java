package com.levelup.library.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.springframework.validation.annotation.Validated;

import java.util.Set;

@Validated
@Entity
@Table(name = "person")
@JsonIgnoreProperties("hibernateLazyInitializer")
public class UserEntity {

    protected UserEntity(){}

    public UserEntity(String name, String nickname, String cpf, String birthDate, String phone, String email, String password) {
        this.name = name;
        this.nickname = nickname;
        this.cpf = cpf;
        this.birthDate = birthDate;
        this.phone = phone;
        this.email = email;
        this.password = password;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 45, nullable = false)
    @NotEmpty(message = "Name is mandatory.")
    private String name;
    @Column(length = 45)
    private String nickname;
    @Column(length = 11, nullable = false)
    @Size(max = 11, min = 11, message = "CPF need to have 11 characters.")
    @NotEmpty(message = "CPF is mandatory.")
    private String cpf;
    @Column(nullable = false)
    @NotEmpty(message = "Birth Date is mandatory")
    private String birthDate;
    @Column(length = 14)
    private String phone;
    @Column(length = 45, nullable = false)
    @NotEmpty(message = "Email is mandatory.")
    @Email(message = "Email need contain dot and @")
    private String email;
    @Column(length = 64, nullable = false)
    @NotEmpty(message = "Password is mandatory.")
    private String password;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", targetEntity = WithdrawEntity.class)
    private Set<WithdrawEntity> withdraws;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getNickname() {
        return nickname;
    }

    public String getCpf() {
        return cpf;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return String.format(
                "Person[id=%d, name=%s, nickname=%s]",
                id, name, nickname
        );
    }
}
