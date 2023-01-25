package com.levelup.library.dataseed;

import com.levelup.library.entities.BookEntity;
import com.levelup.library.entities.UserEntity;
import com.levelup.library.entities.WithdrawEntity;
import com.levelup.library.repositories.BookRepository;
import com.levelup.library.repositories.UserRepository;
import com.levelup.library.repositories.WithdrawRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class UserDataLoader implements CommandLineRunner {
    @Autowired
    UserRepository userRepository;

    @Autowired
    WithdrawRepository withdrawRepository;

    @Autowired
    BookRepository bookRepository;

    public void run(String ...args) throws Exception {
        loadUserData();
    }

    private void loadUserData() {
        if(userRepository.count() == 0) {
            UserEntity user1 = new UserEntity("Renan Dorneles", "renas", "04174928045", "2000-03-23", "+5551998691353", "rdorneles64@gmail.com", "46070d4bf934fb0d4b06d9e2c46e346944e322444900a435d7d9a95e6d7435f5");
            userRepository.save(user1);

            UserEntity user2 = new UserEntity("Teste", "renas", "35280143006", "2000-03-23", "+5551998691353", "rdorneles65@gmail.com", "46070d4bf934fb0d4b06d9e2c46e346944e322444900a435d7d9a95e6d7435f5");
            userRepository.save(user2);

            BookEntity book1 = new BookEntity("My first book.", "Book book", "Jeremias Catalixo", "1995-03-23", "Matarato LTDA", BookEntity.BOOKED);
            bookRepository.save(book1);

            BookEntity book2 = new BookEntity("My second book.", "Book book 2", "Guilhonizo Ribostovski","1995-03-23", "Tibetian Editor", BookEntity.BOOKED);
            bookRepository.save(book2);

            WithdrawEntity withdraw1 = new WithdrawEntity(user1, book1, "2022-01-10");
            withdrawRepository.save(withdraw1);

            WithdrawEntity withdraw2 = new WithdrawEntity(user1, book2, "2022-01-10");
            withdrawRepository.save(withdraw2);
        }
    }
}
