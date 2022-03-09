package com.bomen.blogging.services;

import com.bomen.blogging.dtos.UserDto;
import com.bomen.blogging.exceptions.BlogAppException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }

    @Override
    public String createUser(UserDto userDto) {
        try {
            validateUser(userDto);
        } catch (BlogAppException e) {
            e.printStackTrace();
        }
        return userCreated(user);
    }

    private void validateUser(UserDto userDto) throws BlogAppException {
        boolean invalidFirstName = userDto.getFirstName() == null || userDto.getFirstName().isBlank() || userDto.getFirstName().isEmpty();
        if (invalidFirstName){
            throw new BlogAppException("Enter a valid first name");
        }
        boolean invalidUserName = userDto.getUsername() == null || userDto.getUsername().isBlank() || userDto.getUsername().isEmpty();
        if (invalidUserName){
            throw new BlogAppException("Enter a valid user name");
        }
        boolean invalidLastName = userDto.getLastName() == null || userDto.getLastName().isBlank() || userDto.getLastName().isEmpty();
        if (invalidLastName){
            throw new BlogAppException("Enter a valid last name");
        }
        boolean invalidPhoneNumber = userDto.getPhoneNumber().length() != 11;
        if (invalidPhoneNumber){
            throw new BlogAppException("Enter a valid phone number");
        }
    }
}
