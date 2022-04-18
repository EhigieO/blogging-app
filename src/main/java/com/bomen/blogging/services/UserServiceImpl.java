package com.bomen.blogging.services;

import com.bomen.blogging.dtos.LoginRequest;
import com.bomen.blogging.dtos.SignUpRequest;
import com.bomen.blogging.exceptions.BlogAppException;
import com.bomen.blogging.models.Post;
import com.bomen.blogging.models.Role;
import com.bomen.blogging.models.User;
import com.bomen.blogging.models.UserRole;
import com.bomen.blogging.repositories.RoleRepository;
import com.bomen.blogging.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    ModelMapper mapper;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder encoder;
    @Autowired
    AuthenticationManager authenticationManager;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }

    @Override
    public User createUser(SignUpRequest signUpRequest) throws BlogAppException {
        validateUser(signUpRequest);
        Set<String> strRoles = signUpRequest.getRoles();
        Set<Role> roles = new HashSet<>();
        if (strRoles == null) {
            Role userRole = roleRepository.findByRole(UserRole.USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "editor":
                        Role editorRole = roleRepository.findByRole(UserRole.EDITOR)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(editorRole);
                        break;
                    case "author":
                        Role authorRole = roleRepository.findByRole(UserRole.AUTHOR)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(authorRole);
                        break;
                    default:
                        Role userRole = roleRepository.findByRole(UserRole.USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        }
            User user = mapper.map(signUpRequest, User.class);
            user.setPassword(encoder.encode(signUpRequest.getPassword()));
            user.setRoles(roles);
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

    @Override
    public boolean existsByUserName(String userName) {
        return userRepository.existsByUserName(userName);
    }

    @Override
    public boolean existsByPhoneNumber(String phoneNumber) {
        return userRepository.existsByPhoneNumber(phoneNumber);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public void update(User user) {
        userRepository.save(user);
    }

    @Override
    public User login(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUserName(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return (User) authentication.getPrincipal();
    }

    private void validateUser(SignUpRequest signUpRequest) throws BlogAppException {

//        boolean invalidFirstName = signUpRequest.getFirstName() == null
//                || signUpRequest.getFirstName().isBlank() || signUpRequest.getFirstName().isEmpty();
//        if (invalidFirstName){
//            throw new BlogAppException("Enter a valid first name");
//        }
        boolean invalidUserName = signUpRequest.getUserName() == null
                || signUpRequest.getUserName().isBlank() || signUpRequest.getUserName().isEmpty();
        if (invalidUserName){
            throw new BlogAppException("Enter a valid user name");
        }
//        boolean invalidLastName = signUpRequest.getLastName() == null
//                || signUpRequest.getLastName().isBlank() || signUpRequest.getLastName().isEmpty();
//        if (invalidLastName){
//            throw new BlogAppException("Enter a valid last name");
//        }
        boolean invalidPhoneNumber = signUpRequest.getPhoneNumber().length() != 11;
        if (invalidPhoneNumber){
            throw new BlogAppException("Enter a valid phone number");
        }
        if (existsByUserName(signUpRequest.getUserName())){
            throw new BlogAppException(signUpRequest.getUserName() + " already exist");
        }

        if (existsByPhoneNumber(signUpRequest.getPhoneNumber())){
            throw new BlogAppException(signUpRequest.getPhoneNumber() + " already exits");
        }

        if (existsByEmail(signUpRequest.getEmail())){
            throw new BlogAppException(signUpRequest.getEmail() + " already exits");
        }
        if (signUpRequest.getPassword().length() < 6){
            throw new BlogAppException("password too short enter at least eight characters");
        }
    }

    public void reset() {
        userRepository.deleteAll();
    }
}
