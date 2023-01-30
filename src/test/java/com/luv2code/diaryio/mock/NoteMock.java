package com.luv2code.diaryio.mock;

import com.luv2code.diaryio.model.Note;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class NoteMock {

    public static Note createNote(final Long id, final String title, final String description,
                                  final String location, final LocalDate eventDate) {
        return Note.builder()
                .id(id)
                .title(title)
                .description(description)
                .location(location)
                .eventDate(eventDate)
                .createdAt(LocalDateTime.now())
                .modifiedAt(null)
                .build();
    }
}
