package com.shiftflow.team.repository;

import com.shiftflow.team.entity.Team;
import com.shiftflow.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TeamRepository extends JpaRepository<Team, Long> {

    Optional<Team> findByName(String name);

    List<Team> findByManager(User manager);
}
