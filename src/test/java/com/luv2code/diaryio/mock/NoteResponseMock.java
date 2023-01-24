package com.luv2code.diaryio.mock;

import com.luv2code.diaryio.dto.NoteResponse;

import java.time.LocalDate;

public class NoteResponseMock {

    public static NoteResponse createNoteResponse(final Long id, final String title, final String description,
                                                  final String location, final LocalDate eventDate) {
        return NoteResponse.builder()
                .id(id)
                .title(title)
                .description(description)
                .location(location)
                .eventDate(eventDate)
                .build();
    }
}
