package com.poweroutrage.security.service;

import com.poweroutrage.users.register.model.UserEntity;
import com.poweroutrage.users.register.model.UserInfo;
import com.poweroutrage.users.register.repository.UserInfoRepository;
import com.poweroutrage.users.register.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoginService implements UserDetailsService {

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<UserInfo> userDTO =
                userInfoRepository.findById(s);
        userDTO.orElseThrow(() -> new UsernameNotFoundException(s + " ID not found"));
        return userDTO.map(MyUserDetails::new).get();
    }
}
