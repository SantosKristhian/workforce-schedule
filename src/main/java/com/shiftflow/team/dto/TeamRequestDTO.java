package com.shiftflow.team.dto;

import java.util.List;

public class TeamRequestDTO {

    private String name;
    private Long managerId;
    private List<Long> employeeIds;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Long getManagerId() { return managerId; }
    public void setManagerId(Long managerId) { this.managerId = managerId; }

    public List<Long> getEmployeeIds() { return employeeIds; }
    public void setEmployeeIds(List<Long> employeeIds) { this.employeeIds = employeeIds; }
}
