package com.shiftflow.team.service;

import com.shiftflow.team.entity.Team;
import com.shiftflow.team.repository.TeamRepository;
import com.shiftflow.user.entity.User;
import com.shiftflow.user.entity.UserRole;
import com.shiftflow.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TeamService {

    private final TeamRepository teamRepository;
    private final UserRepository userRepository;

    public TeamService(TeamRepository teamRepository, UserRepository userRepository) {
        this.teamRepository = teamRepository;
        this.userRepository = userRepository;
    }

    public Team createTeam(String name, Long managerId, List<Long> employeeIds, User currentUser) {

        boolean isAdmin = currentUser.getRole() == UserRole.ADMIN;
        boolean isManager = currentUser.getRole() == UserRole.MANAGER;


        if (!isAdmin && !isManager) {
            throw new IllegalArgumentException("Only ADMIN or MANAGER can create a team");
        }


        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Team name is required");
        }


        teamRepository.findByName(name).ifPresent(t -> {
            throw new IllegalArgumentException("Team name must be unique");
        });


        User manager = userRepository.findById(managerId)
                .orElseThrow(() -> new IllegalArgumentException("Manager not found"));


        if (manager.getRole() != UserRole.MANAGER && manager.getRole() != UserRole.ADMIN) {
            throw new IllegalArgumentException("Manager must have role MANAGER or ADMIN");
        }


        List<User> employees = userRepository.findAllById(employeeIds).stream()

                .filter(emp -> !emp.getId().equals(manager.getId()))

                .filter(emp -> emp.getRole() == UserRole.EMPLOYEE)
                .collect(Collectors.toList());


        List<User> invalidUsers = userRepository.findAllById(employeeIds).stream()
                .filter(emp -> emp.getRole() != UserRole.EMPLOYEE && !emp.getId().equals(manager.getId()))
                .collect(Collectors.toList());

        if (!invalidUsers.isEmpty()) {
            throw new IllegalArgumentException("Only users with role EMPLOYEE can be employees");
        }


        Team team = new Team();
        team.setName(name);
        team.setManager(manager);
        team.setEmployees(employees);
        team.setActive(true);

        return teamRepository.save(team);
    }


    public Team getTeamById(Long id) {
        Team team = teamRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Team not found"));

        if (!team.getActive()) {
            throw new IllegalArgumentException("Team is inactive");
        }

        return team;
    }

    public List<Team> listAllTeams() {
        return teamRepository.findAll().stream()
                .filter(Team::getActive)
                .collect(Collectors.toList());
    }

    public List<Team> getTeamsByManager(Long managerId) {
        User manager = userRepository.findById(managerId)
                .orElseThrow(() -> new IllegalArgumentException("Manager not found"));

        return teamRepository.findByManager(manager).stream()
                .filter(Team::getActive)
                .collect(Collectors.toList());
    }

    public Team deactivateTeam(Long id, User currentUser) {
        Team team = teamRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Team not found"));

        boolean isAdmin = currentUser.getRole() == UserRole.ADMIN;
        boolean isManager = currentUser.getRole() == UserRole.MANAGER;

        if (!isAdmin && (!isManager || !team.getManager().getId().equals(currentUser.getId()))) {
            throw new IllegalArgumentException("Only ADMIN or the team manager can deactivate the team");
        }

        team.setActive(false);
        return teamRepository.save(team);
    }
}
