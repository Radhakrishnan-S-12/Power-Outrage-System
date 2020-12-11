package com.poweroutrage.users.register.controller;

import com.poweroutrage.users.register.model.UserDTO;
import com.poweroutrage.users.register.model.UserEntity;
import com.poweroutrage.users.register.model.UserInfo;
import com.poweroutrage.users.register.service.UsersService;
import com.poweroutrage.users.register.util.JwtUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping("/users-api")
public class UsersController {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UsersService usersService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/authenticate")
    public String generateToken(@RequestBody UserInfo authRequest) throws Exception {
        Authentication authentication = null;
        try {
            log.info("Id: " + authRequest.getUserId() + "\nPassword: " + authRequest.getPassword());
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUserId(), authRequest.getPassword())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (Exception ex) {
            throw new Exception("inavalid username/password");
        }
        return jwtUtil.generateToken(authentication);
    }

    @PostMapping("/addusers")
    public ResponseEntity<Boolean> addUser(@RequestBody UserEntity userEntity) {
        if (usersService.addUsers(userEntity))
            return ResponseEntity.ok(true);
        else
            return ResponseEntity.unprocessableEntity().build();

    }

    @PreAuthorize("hasRole('ROLE_Admin') or hasRole('ROLE_User')")
    @GetMapping("/users/{id}")
    public ResponseEntity<UserDTO> getUsers(@PathVariable String id) {
        UserDTO userDTO = usersService.getUsers(id);
        if (!userDTO.getUserId().isEmpty()) {
            return ResponseEntity.ok(usersService.getUsers(id));
        } else
            return ResponseEntity.badRequest().build();
    }

    @PreAuthorize("hasRole('ROLE_Admin')")
    @PutMapping("/admin/updateusers")
    public ResponseEntity<String> updateUser(@RequestBody UserEntity userEntity) {
        if (usersService.addUsers(userEntity)) {
            return ResponseEntity.ok("Updated");
        } else
            return ResponseEntity.badRequest().build();
    }
}
