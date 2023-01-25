package com.levelup.library.services;

import com.levelup.library.entities.UserEntity;
import com.levelup.library.exceptions.NoSuchIdException;
import com.levelup.library.repositories.UserRepository;
import com.levelup.library.utils.Validator;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class UserServiceImpl {
    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private UserRepository userRepository;

    public List<UserEntity> find(Long userId) {
        if(userId == null){
            return userRepository.findAll();
        }
        List<UserEntity> user = new ArrayList<>();
        user.add(Optional.ofNullable(userRepository.findById(userId).orElseThrow(() -> {
            throw new NoSuchElementException("User with ID " + userId + " not founded.");
        })).get());

        return user;
    }

    public UserEntity findUserByEmail(String userEmail){
        return userRepository.findByEmail(userEmail);
    }

    public void delete(Long id) {
        Optional.ofNullable(userRepository.findById(id).orElseThrow(() -> {
            throw new NoSuchElementException("User with ID " + id + " not founded.");
        }));

        userRepository.deleteById(id);
    }

    public void create(UserEntity newUser) {
        validateUserCPFEmailBirthDate(newUser);

        newUser.setPassword(this.passwordEncrypt(newUser.getPassword()));
        userRepository.save(newUser);
    }

    @Transactional
    public void update(Long id, UserEntity updatedUser) {
        if(id == null)
            throw new NoSuchIdException();

        Optional.ofNullable(userRepository.findById(id).orElseThrow(() -> {
            throw new NoSuchElementException("User with ID " + id + " not founded.");
        }));

        validateUserCPFEmailBirthDate(updatedUser);
        String hashedPass = this.passwordEncrypt(updatedUser.getPassword());
        updatedUser.setPassword(DigestUtils.sha256Hex(hashedPass));

        entityManager.merge(updatedUser);
    }

    private String passwordEncrypt(String rawPassword){
        return DigestUtils.sha256Hex(rawPassword);
    }

    private void validateUserCPFEmailBirthDate(UserEntity user){
        Validator.EmailIsAvailable(user);
        Validator.validateCPF(user.getCpf());
        Validator.CPFAvailable(user);
        Validator.IsAValidDate(user.getBirthDate());
    }
}
