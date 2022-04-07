package com.bomen.blogging.controllers;


import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
public class Response {
    private HttpStatus responseCode;
    private boolean success;
    private String token;

}
