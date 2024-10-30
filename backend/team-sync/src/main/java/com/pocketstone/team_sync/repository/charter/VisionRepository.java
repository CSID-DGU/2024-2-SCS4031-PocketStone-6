package com.pocketstone.team_sync.repository.charter;

import com.pocketstone.team_sync.entity.charter.Vision;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface VisionRepository extends JpaRepository<Vision, Long> {

    @Modifying
    @Transactional
    @Query("UPDATE Vision v SET v.visionName = :visionName, v.visionContent = :visionContent WHERE v.projectCharter.project.id = :projectId AND v.id = :id")
    void updateVisionByProjectId(Long projectId, Long id, String visionName, String visionContent);
}
