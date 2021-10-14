package com.java.weblaptop.service.impl;

import com.java.weblaptop.dto.UserDTO;
import com.java.weblaptop.entity.*;
import com.java.weblaptop.exception.ResourceNotFoundException;
import com.java.weblaptop.repository.RoleRepository;
import com.java.weblaptop.repository.UserRepository;
import com.java.weblaptop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Override
    public List<UserDTO> retrieveUsers() {
        List<User> users = userRepository.findAll();
        return new UserDTO().toListDto(users);
    }

    @Override
    public Optional<UserDTO> getUser(Long userId) throws ResourceNotFoundException {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("user not found for this id: "+userId));
        return Optional.of(new UserDTO().convertToDto(user));
    }

    @Override
    public UserDTO saveUser(UserDTO userDTO){
        User user = new UserDTO().convertToEti(userDTO);

        user = new User(userDTO.getUserName(), userDTO.getUserEmail(), encoder.encode(userDTO.getUserPassword()));
        Set<String> strRoles = userDTO.getRoles();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByName("user")
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                if ("admin".equals(role.toLowerCase())) {
                    Role adminRole = roleRepository.findByName("admin")
                            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                    roles.add(adminRole);
                } else {
                    Role userRole = roleRepository.findByName("user")
                            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                    roles.add(userRole);
                }
            });
        }

        user.setRoles(roles);

        return new UserDTO().convertToDto(userRepository.save(user));
    }

    @Override
    public Boolean deleteUser(Long userId) throws ResourceNotFoundException {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("user not found for this id: " + userId));
        this.userRepository.delete(user);
        return true;
    }

    @Override
    public UserDTO updateUser(Long userId, UserDTO userDTO) throws ResourceNotFoundException {
        User userExist = userRepository.findById(userId).orElseThrow(() ->
                new ResourceNotFoundException("user not found for this id: "+userId));

        userExist.setUserName(userDTO.getUserName());
        userExist.setUserEmail(userDTO.getUserEmail());
        //userExist.setUserPassword(userDTO.getUserPassword());
        userExist.setUserPassword(encoder.encode(userDTO.getUserPassword()));

        User user = new User();
        user = userRepository.save(userExist);
        return new UserDTO().convertToDto(user);
    }

}
