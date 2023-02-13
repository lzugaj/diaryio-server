package com.luv2code.diaryio.dto;

import lombok.*;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NoteResponse {
    private Long id;

    private String title;

    private String description;

    private String location;

    private LocalDate eventDate;

}
