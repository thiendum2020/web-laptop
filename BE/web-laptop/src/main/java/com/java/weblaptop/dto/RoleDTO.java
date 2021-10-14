package com.java.weblaptop.dto;

import com.java.weblaptop.entity.Role;

public class RoleDTO {
    private long role_id;
    private String name;

    public RoleDTO() {
    }

    public RoleDTO(Role role){
        this.role_id = role.getRole_id();
        this.name = role.getName();
    }
    public long getRole_id() {
        return role_id;
    }

    public void setRole_id(long role_id) {
        this.role_id = role_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
