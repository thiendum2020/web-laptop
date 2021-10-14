package com.java.weblaptop.dto;

import com.java.weblaptop.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class UserDTO {
    private long user_id;

    @NotNull
    @Size(min = 2, max = 10)
    private String userName;

    @NotNull
    @Email
    private String userEmail;

    @NotNull
    private String userPassword;

    private Set<String> roles;


    public UserDTO(String userName, String userEmail, String userPassword) {
        this.userName = userName;
        this.userEmail = userEmail;
        this.userPassword = userPassword;
    }

    public UserDTO convertToDto(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setUser_id(user.getUser_id());
        userDTO.setUserName(user.getUserName());
        userDTO.setUserEmail(user.getUserEmail());
        userDTO.setUserPassword(user.getUserPassword());

        Set<String> roles = new HashSet<>();
        user.getRoles().forEach(r -> {
            roles.add(r.getName());
        });
        userDTO.setRoles(roles);

        return userDTO;
    }

    public User convertToEti(UserDTO userDTO) {
        User user = new User();

        user.setUserName(userDTO.getUserName());
        user.setUserEmail(userDTO.getUserEmail());
        user.setUserPassword(userDTO.getUserPassword());

//        Set<Role> roles = new HashSet<>();
//        userDTO.getRoles().forEach(r ->{
//            roles.add(r.get)
//        });
//        user.setRoles(userDTO.getRoles());
//        user.setRoles(userDTO.getRoleDTOS());
        return user;
    }


    public List<UserDTO> toListDto(List<User> listEntity) {
        List<UserDTO> listDto = new ArrayList<>();

        listEntity.forEach(e->{
            listDto.add(this.convertToDto(e));
        });
        return listDto;
    }

}
