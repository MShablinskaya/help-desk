package com.innowise.training.shablinskaya.helpdesk.security;

import com.innowise.training.shablinskaya.helpdesk.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class JwtUser implements UserDetails {
    private String email;
    private String password;
    private Collection<? extends GrantedAuthority> grantedAuthorities;

    public JwtUser(){}

    public JwtUser(String email, String password, Collection<? extends GrantedAuthority> grantedAuthorities) {
        this.email = email;
        this.password = password;
        this.grantedAuthorities = grantedAuthorities;
    }

    public static JwtUser fromUserToJwtUser(User user){
        JwtUser jwtUser = new JwtUser();
        jwtUser.email  = user.getEmail();
        jwtUser.password = user.getPassword();
        jwtUser.grantedAuthorities = Collections.singletonList(new SimpleGrantedAuthority(user.getRoleId().name()));

        return jwtUser;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return grantedAuthorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
