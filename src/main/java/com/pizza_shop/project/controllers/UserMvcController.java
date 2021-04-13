package com.pizza_shop.project.controllers;

import com.pizza_shop.project.dto.PasswordUserDto;
import com.pizza_shop.project.entity.User;
import com.pizza_shop.project.services.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@Slf4j
public class UserMvcController {

    private IUserService userService;

    @Autowired
    public UserMvcController(IUserService userService) {
        this.userService = userService;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/email/activate/{activateCode}")
    public String index(@PathVariable String activateCode, Model model){
        final User user = userService.getUserByActivationCode(activateCode);
        assert user != null;
        model.addAttribute("name", user.getUsername());
        model.addAttribute("newPassword", new PasswordUserDto(user.getUsername()));
        return "password";
    }
    @PostMapping("/user/password")
    public String changeUserPassword(PasswordUserDto passwordUserDto) {
        userService.changePassword(passwordUserDto);
        return "email";
    }
    @GetMapping("/")
    public String home(){
        return "forward:index.html";
    }
}
