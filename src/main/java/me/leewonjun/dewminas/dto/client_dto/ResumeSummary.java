package me.leewonjun.dewminas.dto.client_dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class ResumeSummary {
    private long id;
    private String desiredPosition;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
