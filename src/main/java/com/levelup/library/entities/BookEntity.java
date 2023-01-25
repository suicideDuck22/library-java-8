package com.levelup.library.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import org.springframework.validation.annotation.Validated;

@JsonIgnoreProperties("hibernateLazyInitializer")
@Validated
@Entity
@Table(name = "book")
public class BookEntity {

    public static final int AVAILABLE = 0;
    public static final int BOOKED = 1;
    protected BookEntity(){}

    public BookEntity(String title, String synopsis, String authorName, String publishedAt, String publisher, Integer bookStatus) {
        this.title = title;
        this.synopsis = synopsis;
        this.authorName = authorName;
        this.publishedAt = publishedAt;
        this.publisher = publisher;
        this.bookStatus = bookStatus;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false)
    @NotBlank(message = "Title is mandatory.")
    private String title;

    @Column(nullable = false)
    @NotBlank(message = "Synopsis is mandatory.")
    private String synopsis;

    @Column(nullable = false)
    @NotBlank(message = "Author Name is mandatory.")
    private String authorName;

    @Column(nullable = false)
    private String publishedAt;

    @Column(nullable = false)
    @NotBlank(message = "Publisher is mandatory.")
    private String publisher;

    @Column(nullable = false)
    private Integer bookStatus;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public String getAuthorName() {
        return authorName;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public String getPublisher() {
        return publisher;
    }

    public Integer getBookStatus() {
        return bookStatus;
    }

    public void setBookStatus(Integer bookStatus) {
        this.bookStatus = bookStatus;
    }
}
