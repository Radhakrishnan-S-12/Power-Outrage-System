package com.poweroutrage.users.register.service;

import com.poweroutrage.users.register.model.UserInfo;
import com.poweroutrage.users.register.repository.UserInfoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
public class LoginService implements UserDetailsService {

    @Autowired
    private UserInfoRepository usersRepository;

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        Optional<UserInfo> userDTO =
                usersRepository.findById(id);
        log.info("Inside LoadUserByUsername: " + userDTO.get());
        userDTO.orElseThrow(() -> new UsernameNotFoundException(id + " ID not" +
                " found"));
        return new User(userDTO.get().getUserId(), userDTO.get().getPassword(),
                getAuthorities(userDTO.get()));
    }

    private Set getAuthorities(UserInfo userInfo) {
        Set<SimpleGrantedAuthority> authorities =
                new HashSet<>();
        SimpleGrantedAuthority authority =
                new SimpleGrantedAuthority(userInfo.getRole());
        authorities.add(authority);
        return authorities;
    }
}
