package com.pizza_shop.project.controllers;

import com.pizza_shop.project.dto.AuthRequest;
import com.pizza_shop.project.dto.AuthenticationResponse;
import com.pizza_shop.project.entity.User;
import com.pizza_shop.project.exceptions.ActivationByEmailException;
import com.pizza_shop.project.services.IUserService;
import com.pizza_shop.project.services.JwtService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
//@CrossOrigin(origins = "http://localhost:4200")
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

    @GetMapping("/user")
    public List<User> getAllUsers(){
      return userService.getAllUsers()
              .stream()
              .peek(user -> user.setPassword(""))
              .collect(Collectors.toList());
    }

    @PostMapping("/user")
    @ResponseStatus(HttpStatus.CREATED)
    public User saveUser(@RequestBody @Valid User user){
        log.info("Handling User /save with requestBody" + user);
       return userService.createUser(user);
    }
    @PutMapping("/user/{id}")
    public User updateUser(@PathVariable int id, @Valid @RequestBody User user){
      final StringBuilder stringBuilder = new StringBuilder();
      log.info(stringBuilder.append("Handling post /user/").append(id).append(" With body : ").append(user.toString()).toString());
      return userService.updateUser(id, user);
    }
    @PostMapping("/user/authenticate")
    public AuthenticationResponse generateJwt(@RequestBody AuthRequest authRequest){
      authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(),
              authRequest.getPassword()));
      log.info("Handling AuthRequest in generateJwt with requestBody" + authRequest);
        final UserDetails userDetails = userService.loadUserByUsername(authRequest.getUsername());
        if (!userDetails.isEnabled()){
          new ActivationByEmailException("Your account is not activated");
        }
        return new AuthenticationResponse(jwtService.generateToken(authRequest.getUsername()),
                userDetails.getAuthorities().toString(), userDetails.getUsername());
    }
    @GetMapping("/user/authenticate/{name}")
    public User getUserByName(@PathVariable String name){
      log.info("Handling /get User by name = " + name);
      final User user = userService.getUserByUserName(name);
      user.setPassword("");
      return user;
    }
    @GetMapping("/user/remind/{email}")
    public String sendPasswordByEmail(@PathVariable String email){
      log.info("Handling /get forgotten password by email = " + email);
      return userService.sendPasswordUserByEmail(email);
    }
    @GetMapping("/activate/{activateCode}")
    public String activateUser(@PathVariable String activateCode){
      boolean isActivate = userService.activateUser(activateCode);
      if (isActivate){
        log.info("User activated ");
      }
      else {
        log.info("Activated code is not found");
      }
    return "Your account is activated.";
    }
    @DeleteMapping("/user/{id}")
    public List<User> deleteUser(@PathVariable int id){
      log.info("Handling delete User with id = " + id);
      return userService.deleteUser(id);
    }
}
