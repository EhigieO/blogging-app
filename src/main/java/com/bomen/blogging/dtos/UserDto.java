package com.bomen.blogging.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class UserDto {
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String phoneNumber;
    private String password;
}
