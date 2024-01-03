package com.blog2.controller;

import com.blog2.entity.Role;
import com.blog2.entity.User;
import com.blog2.payload.LoginDto;
import com.blog2.payload.SignUPDto;
import com.blog2.repository.RoleRepository;
import com.blog2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private PasswordEncoder passwordEncoder;

    //for siginUp
    //http://localhost:8080/api/auth/siginup

    @PostMapping("/siginup")
    public ResponseEntity<?>  registerUser(
            @RequestBody SignUPDto signUPDto
    ){

        if(userRepository.existsByEmail(signUPDto.getEmail())){
            return new ResponseEntity<>("Email id is exist :"+signUPDto.getEmail(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (userRepository.existsByUsername(signUPDto.getUsername())){
            return new ResponseEntity<>("Username is exist :" +signUPDto.getUsername(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        //crete entity object
        User user = new User();
        user.setName(signUPDto.getUsername());
        user.setEmail(signUPDto.getEmail());
        user.setUsername(signUPDto.getUsername());
        user.setPassword(passwordEncoder.encode(signUPDto.getPassword()));

        //set the role

        Role role = roleRepository.findByName("ROLE_ADMIN").get();
        user.setRoles(Collections.singleton(role));

        //user is save in db
        userRepository.save(user);

        return new ResponseEntity<>("User Registered Successfully", HttpStatus.CREATED);
    }

    //for signin
    //http://localhost:8080/api/auth/signin

    @PostMapping("/signin")
    public ResponseEntity<String> authenticateUser(
            @RequestBody LoginDto loginDto
    ){
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(loginDto.getUsernameOrEmail(), loginDto.getPassword());
        //value compare logindto to database
        Authentication authenticate = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        SecurityContextHolder.getContext().setAuthentication(authenticate);
        return new ResponseEntity<>("User Signin Successfully :" , HttpStatus.OK);



    }

}
