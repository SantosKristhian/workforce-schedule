package com.shiftflow.team.dto;

import com.shiftflow.user.entity.User;
import com.shiftflow.user.entity.UserRole;

public class TeamUserDTO {

    private Long id;
    private String name;
    private String email;
    private UserRole role;
    private Boolean active;

    public TeamUserDTO(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.role = user.getRole();
        this.active = user.getActive();
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public UserRole getRole() { return role; }
    public Boolean getActive() { return active; }

    public void setId(Long id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setEmail(String email) { this.email = email; }
    public void setRole(UserRole role) { this.role = role; }
    public void setActive(Boolean active) { this.active = active; }
}
