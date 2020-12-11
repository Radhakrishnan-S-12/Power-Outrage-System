package com.poweroutrage.users.register.controller;

import com.poweroutrage.users.register.configuration.BaseConfiguration;
import com.poweroutrage.users.register.model.UserDTO;
import com.poweroutrage.users.register.model.UserEntity;
//import com.poweroutrage.users.register.service.LoginService;
import com.poweroutrage.users.register.service.LoginService;
import com.poweroutrage.users.register.service.UsersService;
//import com.poweroutrage.users.register.util.JwtUtil;
//import org.apache.catalina.User;
import com.poweroutrage.users.register.util.JwtUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.AuthenticationManager;

import java.io.IOException;
import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class UsersControllerTest extends BaseConfiguration {

    @Autowired
    private UsersController usersController;

    private JwtUtil jwtUtil;

    @Mock
    private UsersService usersService;

    private AuthenticationManager authenticationManager;


    @BeforeEach
    public void init() {
        super.setUp();
        this.usersController = new UsersController(jwtUtil, usersService, authenticationManager);
    }

    @Test
    public void getCallTests() {
        ResponseEntity<UserDTO> actualResponse =
                ResponseEntity.ok(getUserDTO());
        when(usersService.getUsers("Adm_1")).thenReturn(getUserDTO());
        ResponseEntity<UserDTO> expectedResponse =
                usersController.getUsers("Adm_1");
        Assertions.assertEquals(actualResponse, expectedResponse);
        verify(usersService, times(2)).getUsers("Adm_1");

    }

    @Test
    public void putCallTests() throws IOException {
        UserEntity userEntity = getUsersEntityFromFile("src/test/resources/json" +
                "/Users.json").get();
        ResponseEntity<String> actualResponse =
                ResponseEntity.ok("Updated");
        when(usersService.addUsers(userEntity)).thenReturn(true);
        ResponseEntity<String> expectedResponse =
                usersController.updateUser(userEntity);
        Assertions.assertEquals(actualResponse, expectedResponse);
        actualResponse =
                ResponseEntity.badRequest().build();
        when(usersService.addUsers(userEntity)).thenReturn(false);
        expectedResponse =
                usersController.updateUser(userEntity);
        Assertions.assertEquals(actualResponse, expectedResponse);
        verify(usersService, times(2)).addUsers(userEntity);


    }

    @Test
    public void postCallTests() throws IOException {
        UserEntity userEntity = getUsersEntityFromFile("src/test/resources/json" +
                "/Users.json").get();
        ResponseEntity<Boolean> actualResponse =
                ResponseEntity.ok(true);
        when(usersService.addUsers(userEntity)).thenReturn(true);
        ResponseEntity<Boolean> expectedResponse =
                usersController.addUser(userEntity);
        Assertions.assertEquals(actualResponse, expectedResponse);
        actualResponse =
                ResponseEntity.unprocessableEntity().build();
        when(usersService.addUsers(userEntity)).thenReturn(false);
        expectedResponse =
                usersController.addUser(userEntity);
        Assertions.assertEquals(actualResponse, expectedResponse);
        verify(usersService, times(2)).addUsers(userEntity);

    }

    private UserDTO getUserDTO() {
        UserDTO userDTO = new UserDTO();
        userDTO.setUserId("1");
        userDTO.setAddress("Chennai");
        userDTO.setContactNumber(Long.parseLong("9003527157"));
        userDTO.setZipCode("613009");
        userDTO.setName("Radha");
        userDTO.setCountry("India");
        userDTO.setEmail("srkrocky0@gmail.com");
        return userDTO;
    }

    private Optional<UserEntity> getUsersEntityFromFile(String jsonPath) throws IOException {
        return Optional.of(objectMapper.readValue(readFromFile(jsonPath),
                UserEntity.class));
    }

}
