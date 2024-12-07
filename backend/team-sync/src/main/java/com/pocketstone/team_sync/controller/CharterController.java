package com.pocketstone.team_sync.controller;


import com.pocketstone.team_sync.dto.FileUploadResponseDto;
import com.pocketstone.team_sync.dto.projectdto.ProjectCharterDto;
import com.pocketstone.team_sync.entity.User;
import com.pocketstone.team_sync.service.CharterFileService;
import com.pocketstone.team_sync.service.ProjectCharterService;
import jakarta.validation.Valid;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@NoArgsConstructor
@RequestMapping("/api/projects/charter")
public class CharterController {

    @Autowired
    private ProjectCharterService projectCharterService;
    @Autowired
    private CharterFileService charterFileService;

    //프로젝트 차터 저장
    @PostMapping("/{projectId}")
    public ResponseEntity<Object> saveProjectCharter(@AuthenticationPrincipal User user,
                                                                @PathVariable("projectId") Long projectId,
                                                                @Valid @RequestBody ProjectCharterDto projectCharterDto) {
        return new ResponseEntity<>(projectCharterService.saveProjectCharter(user, projectId, projectCharterDto), HttpStatus.CREATED);

    }

    //프로젝트 id로 차터 검색
    @GetMapping("/{projectId}")
    public ResponseEntity<ProjectCharterDto> getProjectCharter(@AuthenticationPrincipal User user,
                                                            @PathVariable("projectId") Long projectId) {
        return new ResponseEntity<>(projectCharterService.findByProjectId(user, projectId), HttpStatus.OK);
    }

    //프로젝트 차터 업데이트
    @PutMapping("/{projectId}")
    public ResponseEntity<Object> updateProjectCharter(@AuthenticationPrincipal User user,
                                                                  @PathVariable("projectId") Long projectId,
                                                                  @RequestBody ProjectCharterDto projectCharterDto) {

        return new ResponseEntity<>(projectCharterService.updateProjectCharterByProjectId(user, projectId, projectCharterDto), HttpStatus.OK);
    }


    @PostMapping(value ="/{projectId}/pdf", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<FileUploadResponseDto> uploadPDF(@AuthenticationPrincipal User user,
                                                            @PathVariable("projectId") Long projectId,
                                                            @RequestParam("file") MultipartFile file,
                                                            @RequestParam(value = "reUpload", defaultValue = "false") boolean reUpload) {
        FileUploadResponseDto response = reUpload
                ? charterFileService.reUploadFile(user, projectId, file)
                : charterFileService.uploadFile(user, projectId, file);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @GetMapping( "/{projectId}/pdf")
    public ResponseEntity<ByteArrayResource> downloadPDF (@AuthenticationPrincipal User user,
                                                          @PathVariable("projectId") Long projectId) {
        return (charterFileService.downloadFile(user, projectId));
    }

    @DeleteMapping("/{projectId}")
    public ResponseEntity<Object> deleteProjectCharter(@AuthenticationPrincipal User user,
                                                       @PathVariable("projectId") Long projectId) {
        projectCharterService.deleteProjectCharter(user, projectId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
