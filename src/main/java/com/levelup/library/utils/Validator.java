package com.levelup.library.utils;

import com.levelup.library.entities.UserEntity;
import com.levelup.library.exceptions.CpfInUseException;
import com.levelup.library.exceptions.EmailInUseException;
import com.levelup.library.exceptions.InvalidCpfException;
import com.levelup.library.exceptions.InvalidDateException;
import com.levelup.library.repositories.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.Optional;

@Component
public class Validator {
    private static UserRepository staticUserRepository;

    @Autowired
    private UserRepository userRepository;

    @PostConstruct
    public void initStaticUserRepository(){
        staticUserRepository = userRepository;
    }

    public static void EmailIsAvailable(UserEntity user) {
        Optional.ofNullable(staticUserRepository.findByEmail(user.getEmail())).map(UserEntity::getId).ifPresent(foundedId -> {
            if (!foundedId.equals(user.getId())){
                throw new EmailInUseException(user.getEmail());
            }
        });
    }

    public static void validateCPF(String cpf){
        //Validation sourced from "https://iqcode.com/code/java/validate-cpf-java"
        if (cpf.equals("00000000000") ||
                cpf.equals("11111111111") ||
                cpf.equals("22222222222") || cpf.equals("33333333333") ||
                cpf.equals("44444444444") || cpf.equals("55555555555") ||
                cpf.equals("66666666666") || cpf.equals("77777777777") ||
                cpf.equals("88888888888") || cpf.equals("99999999999") ||
                (cpf.length() != 11))
            throw new InvalidCpfException(cpf);

        char dig10, dig11;
        int sm, i, r, num, peso;

        try {
            sm = 0;
            peso = 10;
            for (i=0; i<9; i++) {
                num = cpf.charAt(i) - 48;
                sm = sm + (num * peso);
                peso = peso - 1;
            }

            r = 11 - (sm % 11);
            if ((r == 10) || (r == 11))
                dig10 = '0';
            else dig10 = (char)(r + 48);

            sm = 0;
            peso = 11;
            for(i=0; i<10; i++) {
                num = cpf.charAt(i) - 48;
                sm = sm + (num * peso);
                peso = peso - 1;
            }

            r = 11 - (sm % 11);
            if ((r == 10) || (r == 11))
                dig11 = '0';
            else dig11 = (char)(r + 48);

            if (!(dig10 == cpf.charAt(9)) && (dig11 == cpf.charAt(10)))
                throw new InvalidCpfException(cpf);
        } catch (InputMismatchException ignored) {

        }
    }

    public static void CPFAvailable(UserEntity user) {
        Optional.ofNullable(staticUserRepository.findByCpf(user.getCpf())).map(UserEntity::getId).ifPresent(foundedId -> {
            if(foundedId.equals(user.getId())){
                throw new CpfInUseException(user.getCpf());
            }
        });
    }

    public static void IsAValidDate(String date){
        DateTimeFormatter dateFormatter = DateTimeFormatter.ISO_LOCAL_DATE;

        try{
            LocalDate.parse(date, dateFormatter);
        } catch(DateTimeParseException ex){
            throw new InvalidDateException(date);
        }
    }
}
