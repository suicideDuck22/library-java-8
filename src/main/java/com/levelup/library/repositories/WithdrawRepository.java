package com.levelup.library.repositories;

import com.levelup.library.entities.WithdrawEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WithdrawRepository extends JpaRepository<WithdrawEntity, Long> {
    @Query(value = "SELECT id, withdraw_date, return_date, user_id, book_id FROM withdrawal WHERE user_id = :userId AND return_date IS NULL", nativeQuery = true)
    List<WithdrawEntity> getAllPendentWithdrawsByUserId(Long userId);

    @Query(value = "SELECT * FROM withdrawal WHERE user_id = :userId AND return_date IS NOT NULL", nativeQuery = true)
    List<WithdrawEntity> getAllReturnedWithdrawsByUserId(Long userId);

    @Query(value = "SELECT * FROM withdrawal WHERE user_id = :userId", nativeQuery = true)
    List<WithdrawEntity> getAllWithdrawsByUserId(Long userId);

    @Query(value = "SELECT * FROM withdrawal WHERE book_id = :bookId AND return_date IS NULL", nativeQuery = true)
    WithdrawEntity findPendentWithdrawByBookId(Long bookId);
}
