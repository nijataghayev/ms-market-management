package com.example.marketmanagement.service;

import com.example.marketmanagement.dao.entity.RoleEntity;
import com.example.marketmanagement.dao.entity.UserCredentialEntity;
import com.example.marketmanagement.dao.repository.UserCredentialsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author: nijataghayev
 */

@Service
@RequiredArgsConstructor
public class MyUserDetailsService implements UserDetailsService {
    private final UserCredentialsRepository credentialsRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var userCredentials = credentialsRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));

        return new User(userCredentials.getUsername(), userCredentials.getPassword(), getAuthorities(userCredentials));
    }

    private Collection<? extends GrantedAuthority> getAuthorities(UserCredentialEntity credentialsEntity) {
        List<RoleEntity> roles = credentialsEntity.getRoles();
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();

        for (RoleEntity role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }

        return authorities;
    }
}
