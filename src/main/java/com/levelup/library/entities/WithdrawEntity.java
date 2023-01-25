package com.levelup.library.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import org.springframework.validation.annotation.Validated;

@Validated
@Entity
@Table(name = "withdrawal")
@JsonIgnoreProperties("hibernateLazyInitializer")
public class WithdrawEntity {
    protected WithdrawEntity(){}

    public WithdrawEntity(UserEntity user, BookEntity book, String withdrawDate) {
        this.user = user;
        this.book = book;
        this.withdrawDate = withdrawDate;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "book_id", referencedColumnName = "id")
    private BookEntity book;

    @Column
    private String withdrawDate;

    @Column
    private String returnDate;

    public Long getId() {
        return id;
    }

    public UserEntity getUserId() {
        return user;
    }

    public BookEntity getBookId() {
        return book;
    }

    public String getWithdrawDate() {
        return withdrawDate;
    }

    public String getReturnDate() {
        return returnDate;
    }
}
