package com.poweroutrage.users.register.service;

import com.poweroutrage.users.register.model.UserDTO;
import com.poweroutrage.users.register.model.UserEntity;
import com.poweroutrage.users.register.model.UserInfo;
import com.poweroutrage.users.register.repository.UserInfoRepository;
import com.poweroutrage.users.register.repository.UsersRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class UsersService {

    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private UserInfoRepository userInfoRepository;


    public boolean addUsers(UserEntity userEntity) {
        boolean flag = false;
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(userEntity.getUserId());
        userInfo.setPassword(userEntity.getPassword());
        userInfo.setRole(userEntity.getAccountType());
        try {
            log.info("Inside UsersService::addUsers");
            if (!userEntity.getUserId().isEmpty()) {
                if (userEntity.getAccountType().equalsIgnoreCase("user")) {
                    userEntity.setUserId("User_" + userEntity.getUserId());
                    userInfo.setUserId("User_" + userInfo.getUserId());
                    usersRepository.save(userEntity);
                    userInfoRepository.save(userInfo);
                    flag = true;
                } else if (userEntity.getAccountType().equalsIgnoreCase("admin")) {
                    userEntity.setUserId("Adm_" + userEntity.getUserId());
                    userInfo.setUserId("Adm_" + userInfo.getUserId());
                    usersRepository.save(userEntity);
                    userInfoRepository.save(userInfo);
                    flag = true;
                }
                log.info("Saved the users details");
            }
        } catch (Exception e) {
            log.error("No ID found " + e.getMessage());
        }
        return flag;
    }

    public UserDTO getUsers(String id) {
        log.info("Inside UsersService::getUsers");
        UserEntity userEntity = usersRepository.findById(id).get();
        log.info("Retrieved UserEntity: "+userEntity);
        UserDTO userDTO = new UserDTO();
        return convertUserEntityToUserDTO(userEntity, userDTO);
    }

    public String getUsersRole(String id){
        log.info("To retrieve the roles");
        UserInfo userInfo = userInfoRepository.findById(id).get();
        return userInfo.getRole();

    }

    private UserDTO convertUserEntityToUserDTO(UserEntity userEntity,
                                            UserDTO userDTO){
        ModelMapper modelMapper = new ModelMapper();
        log.info("Inside Model Mapper");
        modelMapper.map(userEntity, userDTO);
        log.info("UserEntity mapped to UserDTO: "+userDTO);
        return userDTO;
    }


}
