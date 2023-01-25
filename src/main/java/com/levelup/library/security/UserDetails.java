package com.levelup.library.security;

import com.levelup.library.entities.UserEntity;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class UserDetails implements  org.springframework.security.core.userdetails.UserDetails{
    private String userEmail;
    private String password;

    public UserDetails( UserEntity user )
    {
        this.userEmail = user.getEmail();
        this.password = user.getPassword();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    /**
     * @return String
     */
    @Override
    public String getPassword()
    {
        return password;
    }

    /**
     * @return String
     */
    @Override
    public String getUsername()
    {
        return userEmail;
    }

    /**
     * @return boolean
     */
    @Override
    public boolean isAccountNonExpired()
    {
        return true;
    }

    /**
     * @return boolean
     */
    @Override
    public boolean isAccountNonLocked()
    {
        return true;
    }

    /**
     * @return boolean
     */
    @Override
    public boolean isCredentialsNonExpired()
    {
        return true;
    }

    /**
     * @return boolean
     */
    @Override
    public boolean isEnabled()
    {
        return true;
    }
}
