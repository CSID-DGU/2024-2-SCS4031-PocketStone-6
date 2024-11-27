package com.pocketstone.team_sync.controller;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/")
public class ExcelController {
    
    
    //엑셀 양식 다운로드
    @GetMapping("/{type}/download-template")
    public ResponseEntity<Resource> downloadTemplate(@PathVariable("type") String type) {
        // ClassPath에서 엑셀 파일 로드
        
        try {
             // ClassPath에서 엑셀 파일 로드
            Resource resource = new ClassPathResource("excel/"+type+"_template.xlsx");

                // 파일 이름 설정
            String filename = type+"_template.xlsx";

            // 응답 헤더 설정
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"");
            headers.add(HttpHeaders.CONTENT_TYPE, "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");

            // 파일 리소스를 ResponseEntity로 반환
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .headers(headers)
                    .body(resource);
    
        } catch (Exception e) {
            throw new IllegalStateException("Failed to load Excel template: " +  e);
        }
    }

        
}
