package com.blog2.seurity;

import com.blog2.entity.Role;
import com.blog2.entity.User;
import com.blog2.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //Search the record in database based on email nd username
    //loadUserByUsername() method called database automatically
    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        User user = userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail).orElseThrow(
                () -> new UsernameNotFoundException("User not found with email or username : " + usernameOrEmail)
        );
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), mapRoleAuthorities(user.getRoles()));



    }

    private Collection<? extends GrantedAuthority> mapRoleAuthorities(Set<Role> roles) {


        return  roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }
}
