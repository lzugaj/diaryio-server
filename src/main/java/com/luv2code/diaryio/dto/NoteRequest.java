package com.luv2code.diaryio.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
public class NoteRequest {

    @NotBlank(message = "Field title must not be blank")
    private String title;

    @NotBlank(message = "Field description must not be blank")
    private String description;

    private String location;

    @NotNull(message = "Field eventDate must not be null")
    private LocalDate eventDate;

}
