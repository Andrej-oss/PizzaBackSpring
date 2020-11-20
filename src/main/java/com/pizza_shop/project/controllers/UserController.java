package com.pizza_shop.project.controllers;

import com.pizza_shop.project.dto.AuthRequest;
import com.pizza_shop.project.dto.AuthenticationResponse;
import com.pizza_shop.project.entity.User;
import com.pizza_shop.project.services.IUserService;
import com.pizza_shop.project.services.JwtService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@Slf4j
public class UserController {

  private IUserService userService;
  private JwtService jwtService;
  private AuthenticationManager authenticationManager;

  @Autowired
    public UserController(IUserService userService, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/user")
    public List<User> saveUser(@RequestBody User user){
        log.info("Handling User /save with requestBody" + user);
       userService.createUser(user);
        return userService.getAllUsers();
    }
    @PostMapping("/user/authenticate")
    public AuthenticationResponse generateJwt(@RequestBody AuthRequest authRequest){
      authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(),
              authRequest.getPassword()));
      log.info("Handling AuthRequest in generateJwt with requestBody" + authRequest);
        final UserDetails userDetails = userService.loadUserByUsername(authRequest.getUsername());
        return new AuthenticationResponse(jwtService.generateToken(authRequest.getUsername()), userDetails.getAuthorities().toString());
    }

}
