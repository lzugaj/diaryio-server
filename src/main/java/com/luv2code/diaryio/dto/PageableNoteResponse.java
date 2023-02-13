package com.luv2code.diaryio.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageableNoteResponse {

    private Long totalItems;

    private List<NoteResponse> content;

    private int totalPages;

    private int currentPage;

}
