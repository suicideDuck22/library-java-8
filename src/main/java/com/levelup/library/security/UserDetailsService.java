package com.levelup.library.security;

import com.levelup.library.entities.UserEntity;
import com.levelup.library.services.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService{
    @Autowired
    private UserServiceImpl userService;

    @Override
    public UserDetails loadUserByUsername( String userEmail ) throws UsernameNotFoundException
    {
        UserEntity user = userService.findUserByEmail( userEmail );

        return new UserDetails( user );
    }
}
