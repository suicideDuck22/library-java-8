package com.levelup.library.repositories;

import com.levelup.library.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    @Query(value = "SELECT * FROM person WHERE email = :email AND password = :password", nativeQuery = true)
    UserEntity findByEmailAndPassword(String email, String password);

    @Query(value = "SELECT * FROM person WHERE email = :email", nativeQuery = true)
    UserEntity findByEmail(String email);

    @Query(value = "SELECT * FROM person WHERE cpf = :cpf", nativeQuery = true)
    UserEntity findByCpf(String cpf);
}
