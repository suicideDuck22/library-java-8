package com.levelup.library.services;

import com.levelup.library.entities.WithdrawEntity;
import com.levelup.library.enums.WithdrawStatus;
import com.levelup.library.repositories.WithdrawRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.security.InvalidParameterException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class WithdrawServiceImpl {

    @Autowired
    private WithdrawRepository withdrawRepository;

    public List<WithdrawEntity> getWithdrawsByUser(Long id, WithdrawStatus status){
        if(StringUtils.isEmpty(status)){
            return withdrawRepository.getAllWithdrawsByUserId(id);
        }

        switch (status){
            case PENDENT:
                return withdrawRepository.getAllPendentWithdrawsByUserId(id);
            case RETURNED:
                return withdrawRepository.getAllReturnedWithdrawsByUserId(id);
            default:
                throw new InvalidParameterException("Parameter " + status + " is not valid.");
        }
    }
    public List<WithdrawEntity> getAll() {
        return withdrawRepository.findAll();
    }

    public WithdrawEntity find(Long id) {
        Optional<WithdrawEntity> withdraw = Optional.ofNullable(withdrawRepository.findById(id).orElseThrow(() -> {
            throw new NoSuchElementException("Couldn't found this reserve.");
        }));
        return withdraw.get();
    }

    public void insert(WithdrawEntity newWithdraw) {

    }

    public void returnBook(WithdrawEntity withdrawToReturn) {

    }

    protected WithdrawEntity findPendentWithdrawByBookId(Long bookId){
        Optional<WithdrawEntity> pendentWithdraw = Optional.ofNullable(withdrawRepository.findPendentWithdrawByBookId(bookId));
        return null;
    }
}
