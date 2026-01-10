package com.shiftflow.team.dto;

import com.shiftflow.team.entity.Team;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class TeamResponseDTO {

    private Long id;
    private String name;
    private TeamUserDTO manager;
    private List<TeamUserDTO> employees;
    private Boolean active;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public TeamResponseDTO(Team team) {
        this.id = team.getId();
        this.name = team.getName();
        this.manager = new TeamUserDTO(team.getManager());
        this.employees = team.getEmployees().stream()
                .map(TeamUserDTO::new)
                .collect(Collectors.toList());
        this.active = team.getActive();
        this.createdAt = team.getCreatedAt();
        this.updatedAt = team.getUpdatedAt();
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public TeamUserDTO getManager() { return manager; }
    public List<TeamUserDTO> getEmployees() { return employees; }
    public Boolean getActive() { return active; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }

    public void setId(Long id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setManager(TeamUserDTO manager) { this.manager = manager; }
    public void setEmployees(List<TeamUserDTO> employees) { this.employees = employees; }
    public void setActive(Boolean active) { this.active = active; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
