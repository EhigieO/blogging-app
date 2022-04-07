package com.bomen.blogging.services;

import com.bomen.blogging.dtos.UserDto;
import com.bomen.blogging.exceptions.BlogAppException;
import com.bomen.blogging.models.Post;
import com.bomen.blogging.models.User;
import com.bomen.blogging.models.UserRole;
import com.bomen.blogging.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    ModelMapper mapper;
    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }

    @Override
    public User createUser(UserDto userDto) throws BlogAppException {
            validateUser(userDto);

            User user = mapper.map(userDto, User.class);
            user.setUserRole(UserRole.AUTHOR);
        return userRepository.save(user);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow();
    }

    @Override
    public User updateUserListOfPost(String email, Post post) {
        User user = userRepository.findByEmail(email).orElseThrow();
        user.addPost(post);
        return userRepository.save(user);
    }

    private void validateUser(UserDto userDto) throws BlogAppException {
        boolean invalidFirstName = userDto.getFirstName() == null
                || userDto.getFirstName().isBlank() || userDto.getFirstName().isEmpty();
        if (invalidFirstName){
            throw new BlogAppException("Enter a valid first name");
        }
        boolean invalidUserName = userDto.getUserName() == null
                || userDto.getUserName().isBlank() || userDto.getUserName().isEmpty();
        if (invalidUserName){
            throw new BlogAppException("Enter a valid user name");
        }
        boolean invalidLastName = userDto.getLastName() == null
                || userDto.getLastName().isBlank() || userDto.getLastName().isEmpty();
        if (invalidLastName){
            throw new BlogAppException("Enter a valid last name");
        }
        boolean invalidPhoneNumber = userDto.getPhoneNumber().length() != 11;
        if (invalidPhoneNumber){
            throw new BlogAppException("Enter a valid phone number");
        }
        boolean userNameExists = isExisting(userDto.getUserName());
        if (userNameExists){
            throw new BlogAppException(userDto.getUserName() + " already exist");
        }
        boolean phoneNumberExists = numberExist(userDto.getPhoneNumber());
        if (phoneNumberExists){
            throw new BlogAppException(userDto.getPhoneNumber() + " already exits");
        }
        boolean emailExists = emailExist(userDto.getEmail());
        if (emailExists){
            throw new BlogAppException(userDto.getEmail() + " already exits");
        }
        if (userDto.getPassword().length() < 8){
            throw new BlogAppException("password too short enter at least eight characters");
        }
    }

    private boolean emailExist(String email) {
        Optional<User> user;
        user = userRepository.findByEmail(email);
        return user.isPresent();
    }

    private boolean isExisting(String username) {
        Optional<User> user;
        user = userRepository.findByUserName(username);
        return user.isPresent();
    }

    private boolean numberExist(String phoneNumber) {
        Optional<User> user;
        user = userRepository.findByPhoneNumber(phoneNumber);
        return user.isPresent();
    }

    public void reset() {
        userRepository.deleteAll();
    }
}
