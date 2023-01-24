package com.luv2code.diaryio.mock;

import com.luv2code.diaryio.dto.NoteRequest;

import java.time.LocalDate;

public class NoteRequestMock {

    public static NoteRequest createNoteRequest(final String title, final String description,
                                                final String location, final LocalDate eventDate) {
        return NoteRequest.builder()
                .title(title)
                .description(description)
                .location(location)
                .eventDate(eventDate)
                .build();
    }
}
