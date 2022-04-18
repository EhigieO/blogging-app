package com.bomen.blogging.controllers;

import com.bomen.blogging.models.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class UserInfoResponse {
    private String id;
    private String email;
    private String username;
    private List<String> roles;
}
