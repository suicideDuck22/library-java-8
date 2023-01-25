package com.levelup.library.services;

import com.levelup.library.entities.BookEntity;
import com.levelup.library.entities.WithdrawEntity;
import com.levelup.library.exceptions.InvalidReturnException;
import com.levelup.library.exceptions.InvalidWithdrawException;
import com.levelup.library.exceptions.NoSuchIdException;
import com.levelup.library.repositories.BookRepository;
import com.levelup.library.utils.Validator;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class BookServiceImpl {
    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private WithdrawServiceImpl withdrawService;

    @Autowired
    private UserServiceImpl userService;

    public List<BookEntity> find(Integer booked) {
        if(booked == null){
            return bookRepository.findAll();
        }
        return bookRepository.findByStatus(booked);
    }

    public BookEntity findById(Long id) {
        BookEntity book = this.verifyBookIdExists(id);

        return book;
    }

    public void create(BookEntity newBook) {
        Validator.IsAValidDate(newBook.getPublishedAt());
        newBook.setBookStatus(BookEntity.AVAILABLE);
        bookRepository.save(newBook);
    }

    public void delete(Long id) {
        this.verifyBookIdExists(id);

        bookRepository.deleteById(id);
    }

    @Transactional
    public void update(Long id, BookEntity updatedBook) {
        BookEntity book = this.verifyBookIdExists(id);
        Validator.IsAValidDate(updatedBook.getPublishedAt());
        updatedBook.setBookStatus(book.getBookStatus());
        updatedBook.setId(id);
        entityManager.merge(updatedBook);
    }

    @Transactional
    public String returnOrWithdraw(Long bookId, Long userId, Integer status){
        if(status == null)
            throw new InvalidParameterException("Status need to be informed");

        BookEntity book = this.verifyBookIdExists(bookId);

        switch(status){
            case 0:
                return this.returnBook(book, userId);
            case 1:
                return this.withdrawBook(book, userId);
            default:
                throw new InvalidParameterException("Informed status " + status + " is not valid, need be 0 or 1.");
        }
    }

    @Transactional
    private String returnBook(BookEntity book, Long userId){
        if(userId == null)
            throw new NoSuchIdException();

        if(book.getBookStatus() == BookEntity.AVAILABLE)
            throw new InvalidReturnException("This book is already available, cannot return him.");

        book.setBookStatus(BookEntity.AVAILABLE);
        entityManager.merge(book);
        return "Book returned successfully.";
    }

    private WithdrawEntity getBookPendentWithdraw(Long bookId){
        return withdrawService.findPendentWithdrawByBookId(bookId);
    }

    @Transactional
    private String withdrawBook(BookEntity book, Long userId){
        if(userId == null)
            throw new NoSuchIdException();

        if(book.getBookStatus() == BookEntity.BOOKED)
            throw new InvalidWithdrawException("This book is already reserved by a user, cannot reserve him.");

        withdrawService.insert(new WithdrawEntity(userService.find(userId).get(0), book, this.getFormattedCurrentDate()));
        book.setBookStatus(BookEntity.BOOKED);
        entityManager.merge(book);
        return "Book reserved successfully.";
    }

    private String getFormattedCurrentDate(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return  dateFormat.format(new Date());
    }

    private BookEntity verifyBookIdExists(Long id){
        Optional<BookEntity> book =  Optional.ofNullable(bookRepository.findById(id).orElseThrow(() -> {
            throw new NoSuchElementException("Book with ID " + id + " not founded.");
        }));
        return book.get();
    }
}
