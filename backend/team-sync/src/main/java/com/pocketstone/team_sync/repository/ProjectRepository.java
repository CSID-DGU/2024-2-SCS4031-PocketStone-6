package com.pocketstone.team_sync.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pocketstone.team_sync.entity.Project;
import com.pocketstone.team_sync.entity.User;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    Optional<Project> findByProjectNameAndUser(String projectName, User user);

    List<Project> findAllByUser (User user);

    Optional<Project> findByUserAndId(User user, Long projectId);//동시 조회

}
