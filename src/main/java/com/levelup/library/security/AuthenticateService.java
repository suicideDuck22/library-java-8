package com.levelup.library.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

@Service
public class AuthenticateService {
    public static final int TOKEN_EXPIRED = 600_000;
    public static final String TOKEN_SALT = "66B43598-B1C1-4690-BE47-77FA67C18FE1";

    public String createToken( String userEmail )
    {
        return JWT.create()
                .withSubject( userEmail )
                .withExpiresAt( new Date( System.currentTimeMillis() + TOKEN_EXPIRED ))
                .sign( Algorithm.HMAC512( TOKEN_SALT ));
    }

    public String verifyToken( String token )
    {
        String userEmail = JWT.require( Algorithm.HMAC512( TOKEN_SALT ))
                .build()
                .verify( token )
                .getSubject();

        return userEmail;
    }

    public boolean verifyUserDetails( String userEmail, UserDetails userDetails )
    {
        return userEmail.equals( userDetails.getUsername() );
    }

    public static String hash( String password ) throws NoSuchAlgorithmException
    {
        return "<" + String.format( "%040x", new BigInteger( MessageDigest.getInstance( "SHA1" ).digest( password.getBytes() ) ).abs() ) + ">";
    }
}
