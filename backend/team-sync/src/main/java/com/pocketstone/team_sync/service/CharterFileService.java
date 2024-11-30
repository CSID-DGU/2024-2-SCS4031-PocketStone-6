package com.pocketstone.team_sync.service;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.pocketstone.team_sync.dto.FileUploadResponseDto;
import com.pocketstone.team_sync.entity.Company;
import com.pocketstone.team_sync.entity.ProjectCharter;
import com.pocketstone.team_sync.entity.User;
import com.pocketstone.team_sync.entity.charter.CharterPdf;
import com.pocketstone.team_sync.exception.CharterNotFoundException;
import com.pocketstone.team_sync.exception.CharterPdfAlreadyExistsException;
import com.pocketstone.team_sync.exception.CharterPdfNotFoundException;
import com.pocketstone.team_sync.exception.CharterUploadException;
import com.pocketstone.team_sync.exception.ProjectNotFoundException;
import com.pocketstone.team_sync.repository.CompanyRepository;
import com.pocketstone.team_sync.repository.ProjectCharterRepository;
import com.pocketstone.team_sync.repository.charter.CharterPdfRepository;
import com.pocketstone.team_sync.utility.ProjectValidationUtils;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class CharterFileService {
    
    private final CompanyRepository companyRepository;
    private final CharterPdfRepository charterPdfRepository;
    private final ProjectCharterRepository projectCharterRepository;

    private AmazonS3 s3Client;

    @Value("${aws.s3.bucketName}")
    private String bucketName;

    @Value("${aws.s3.accessKey}")
    private String accessKey;

    @Value("${aws.s3.secretKey}")
    private String secretKey;

    @PostConstruct
    private void initialize() {
        BasicAWSCredentials awsCredentials = new BasicAWSCredentials(accessKey, secretKey);
        s3Client = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .withRegion(Regions.AP_NORTHEAST_2)
                .build();
    }

    public FileUploadResponseDto uploadFile(User user, Long projectId, MultipartFile multipartFile) {
        Company company  = companyRepository.findByUserId(user.getId()).orElse(null);
        ProjectCharter projectCharter = projectCharterRepository.findByProjectId(projectId)
                .orElseThrow(() -> new ProjectNotFoundException(" "));
        ProjectValidationUtils.validateCharterOwner(company, projectCharter);


        FileUploadResponseDto fileUploadResponse = new FileUploadResponseDto();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String todayDate = dateTimeFormatter.format(LocalDate.now());
        String filePath = "";

        try {
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentType(multipartFile.getContentType());
            objectMetadata.setContentLength(multipartFile.getSize());
            filePath = projectCharter.getProject().getProjectName() + "_" +
                    projectCharter.getProject().getId() + "_" +
                    projectCharter.getId() + "_" +
                    todayDate;
            boolean isObjectExist = s3Client.doesObjectExist(bucketName, filePath);
            if (isObjectExist) {
                throw new CharterPdfAlreadyExistsException();
            }
            s3Client.putObject(bucketName, filePath, multipartFile.getInputStream(), objectMetadata);
            fileUploadResponse.setFilePath(filePath);
            fileUploadResponse.setDateTime(LocalDateTime.now());
        } catch (IOException e) {
            throw new CharterUploadException(e.getMessage());
        }

        charterPdfRepository.save(CharterPdf.builder()
                .projectCharter(projectCharter)
                .fileName(filePath)
                .createdDate(todayDate)
                .build());
        return fileUploadResponse;
    }

    public ResponseEntity<ByteArrayResource> downloadFile(User user, Long projectId) {
        Company company  = companyRepository.findByUserId(user.getId()).orElse(null);
        ProjectCharter projectCharter = projectCharterRepository.findByProjectId(projectId)
                .orElseThrow(() -> new ProjectNotFoundException(" "));
        ProjectValidationUtils.validateCharterOwner(company, projectCharter);

        CharterPdf charterPdf = charterPdfRepository.findByProjectCharterId(projectCharter.getId())
                .orElseThrow(CharterNotFoundException::new);

        try {
            String filePath = charterPdf.getFileName();
            boolean isObjectExist = s3Client.doesObjectExist(bucketName, filePath);
            if (!isObjectExist) {
                throw new CharterPdfNotFoundException();
            }
            S3Object s3Object = s3Client.getObject(bucketName, filePath);
            S3ObjectInputStream inputStream = s3Object.getObjectContent();
            byte[] content = StreamUtils.copyToByteArray(inputStream);
            inputStream.close();

            ByteArrayResource resource = new ByteArrayResource(content);

            return ResponseEntity.ok()
                    .contentLength(content.length)
                    .contentType(org.springframework.http.MediaType.parseMediaType("application/pdf"))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=" + filePath)
                    .body(resource);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }


    }

    public FileUploadResponseDto reUploadFile(User user, Long projectId, MultipartFile multipartFile) {
        Company company  = companyRepository.findByUserId(user.getId()).orElse(null);
        ProjectCharter projectCharter = projectCharterRepository.findByProjectId(projectId)
                .orElseThrow(() -> new ProjectNotFoundException(" "));
        CharterPdf charterPdf = charterPdfRepository.findByProjectCharterId(projectCharter.getId())
                .orElseThrow(CharterNotFoundException::new);
        ProjectValidationUtils.validateCharterOwner(company, projectCharter);


            boolean isObjectExist = s3Client.doesObjectExist(bucketName, charterPdf.getFileName());
            if (!isObjectExist) {
                throw new CharterPdfNotFoundException();
            } else {
                s3Client.deleteObject(bucketName, charterPdf.getFileName());
                charterPdfRepository.delete(charterPdf);
            }

        return uploadFile(user, projectId, multipartFile);


    }
}


