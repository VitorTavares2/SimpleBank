package com.SimpleBank.dtos;

import com.SimpleBank.domain.user.UserType;

import java.math.BigDecimal;

public record UserDTO(String firstName, String lastName, String email, String password, Long document, BigDecimal balance,UserType usertype) { //user data
}
