package com.moviemicroservice.users.Security;


import com.moviemicroservice.users.Entities.Roles;
import com.moviemicroservice.users.Entities.Users;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
@AllArgsConstructor
public class UserPrincipal implements UserDetails {

    private final Long id;
    private final String name;
    private final String email;
    private final String password;
    private final Roles role;
    private Collection<? extends GrantedAuthority> authorities;

    public static UserPrincipal create(Users user) {
        List<GrantedAuthority> authorities = List.of(
            new SimpleGrantedAuthority("ROLE_" + user.getRole().name())
        );
        return new UserPrincipal(
            user.getId(),
            user.getName(),
            user.getEmail(),
            user.getPassword(),
            user.getRole(),
            authorities
        );
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
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
