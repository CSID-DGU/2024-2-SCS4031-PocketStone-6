package com.pocketstone.team_sync.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Data
@Setter
@Getter
public class FileUploadResponseDto {
    private String filePath;
    private LocalDateTime dateTime;

}
