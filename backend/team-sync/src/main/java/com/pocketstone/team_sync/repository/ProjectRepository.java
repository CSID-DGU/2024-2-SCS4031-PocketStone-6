package com.pocketstone.team_sync.repository;

import com.pocketstone.team_sync.entity.Project;
import com.pocketstone.team_sync.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    Optional<Project> findByProjectNameAndUser(String projectName, User user);

    List<Project> findAllByUser (User user);

}
