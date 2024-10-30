package com.pocketstone.team_sync.repository.charter;

import com.pocketstone.team_sync.entity.charter.Scope;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ScopeRepository extends JpaRepository<Scope, Long> {

    @Modifying
    @Transactional
    @Query ("UPDATE Scope s SET s.scopeName = :scopeName, s.scopeContent = :scopeContent WHERE s.projectCharter.project.id = :projectId AND s.id = :id")
    void updateScopeByProjectId(Long projectId, Long id, String scopeName, String scopeContent);
}
