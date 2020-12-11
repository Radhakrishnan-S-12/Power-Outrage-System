package com.poweroutrage.users.register.service;

import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;

import com.poweroutrage.users.register.model.UserDTO;
import com.poweroutrage.users.register.repository.UserInfoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import com.poweroutrage.users.register.configuration.BaseConfiguration;
import com.poweroutrage.users.register.model.UserEntity;
import com.poweroutrage.users.register.repository.UsersRepository;

@ExtendWith(MockitoExtension.class)
public class UsersServiceTest extends BaseConfiguration {

    @Autowired
    private UsersService usersService;
    @Mock
    private UsersRepository usersRepository;
    @Mock
    private UserInfoRepository userInfoRepository;

    @BeforeEach
    public void init() {
        this.usersService = new UsersService(usersRepository, userInfoRepository);
        super.setUp();
    }

    @Test
    public void getUsersTest() throws IOException {
        when(usersRepository.findById("1")).thenReturn(getUsersFromFile("src/test" +
                "/resources/json/UserEntity.json"));
        assertEquals("1", usersService.getUsers("1").getUserId());
        verify(usersRepository, times(1)).findById("1");

    }

    @Test
    public void addAdminTest() throws IOException {
        UserEntity userEntity = getUsersEntityFromFile("src/test/resources/json" +
                "/Users.json");
        when(usersRepository.save(userEntity)).thenReturn(userEntity);
        assertTrue(usersService.addUsers(userEntity));
        verify(usersRepository, times(1)).save(userEntity);

    }

    @Test
    public void addAdminFalseTest() {
        UserEntity userEntity = new UserEntity();
        assertFalse(usersService.addUsers(userEntity));
    }

    @Test
    public void addUserTest() throws IOException {
        UserEntity userEntity = getUsersEntityFromFile("src/test/resources/json" +
                "/Users.json");
        userEntity.setAccountType("User");
        when(usersRepository.save(userEntity)).thenReturn(userEntity);
        assertTrue(usersService.addUsers(userEntity));
        verify(usersRepository).save(userEntity);

    }

    private UserEntity getUsersEntityFromFile(String jsonPath) throws IOException {
        return objectMapper.readValue(readFromFile(jsonPath),
                UserEntity.class);
    }


    private Optional<UserEntity> getUsersFromFile(String jsonPath) throws IOException {
        Optional<UserEntity> userEntity = Optional.of(
                objectMapper.readValue(readFromFile(jsonPath),
                        UserEntity.class));
        return userEntity;
    }
}
