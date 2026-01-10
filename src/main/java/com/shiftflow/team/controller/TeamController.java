package com.shiftflow.team.controller;

import com.shiftflow.team.dto.TeamRequestDTO;
import com.shiftflow.team.dto.TeamResponseDTO;
import com.shiftflow.team.entity.Team;
import com.shiftflow.team.service.TeamService;
import com.shiftflow.user.entity.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/teams")
public class TeamController {

    private final TeamService teamService;

    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    @PostMapping
    public ResponseEntity<TeamResponseDTO> createTeam(@RequestBody TeamRequestDTO request) {
        User currentUser = getCurrentUser();
        Team team = teamService.createTeam(
                request.getName(),
                request.getManagerId(),
                request.getEmployeeIds(),
                currentUser
        );

        return ResponseEntity.ok(new TeamResponseDTO(team));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TeamResponseDTO> getTeamById(@PathVariable Long id) {
        Team team = teamService.getTeamById(id);
        return ResponseEntity.ok(new TeamResponseDTO(team));
    }

    @GetMapping
    public ResponseEntity<List<TeamResponseDTO>> listAllTeams() {
        List<TeamResponseDTO> response = teamService.listAllTeams().stream()
                .map(TeamResponseDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/manager/{managerId}")
    public ResponseEntity<List<TeamResponseDTO>> getTeamsByManager(@PathVariable Long managerId) {
        List<TeamResponseDTO> response = teamService.getTeamsByManager(managerId).stream()
                .map(TeamResponseDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<TeamResponseDTO> deactivateTeam(@PathVariable Long id) {
        User currentUser = getCurrentUser();
        Team team = teamService.deactivateTeam(id, currentUser);
        return ResponseEntity.ok(new TeamResponseDTO(team));
    }

    private User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return (User) auth.getPrincipal();
    }
}
