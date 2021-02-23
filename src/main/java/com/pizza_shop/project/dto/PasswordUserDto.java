package com.pizza_shop.project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PasswordUserDto {
    String userName;
    String password;

    public PasswordUserDto(String userName) {
        this.userName = userName;
    }
}
