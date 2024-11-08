package com.pocketstone.team_sync.repository.charter;

import com.pocketstone.team_sync.entity.charter.CharterPdf;
import org.apache.http.entity.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CharterPdfRepository extends JpaRepository<CharterPdf, Long> {
    Optional<CharterPdf> findByProjectCharterId(Long projectCharterId);

}
