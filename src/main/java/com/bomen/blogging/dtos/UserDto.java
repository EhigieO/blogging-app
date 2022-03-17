package com.bomen.blogging.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class UserDto {
    private String firstName;
    private String lastName;
    private String userName;
    private String email;
    private String phoneNumber;
    private String password;
}
