package com.luv2code.diaryio.mapper;

import com.luv2code.diaryio.dto.NoteRequest;
import com.luv2code.diaryio.mock.NoteMock;
import com.luv2code.diaryio.mock.NoteRequestMock;
import com.luv2code.diaryio.model.Note;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;

@ExtendWith(MockitoExtension.class)
public class RequestToEntityMapperTest {

    private RequestToEntityMapper<Note, NoteRequest> requestToEntityMapper;

    @Mock
    private ModelMapper modelMapper;

    private Note note;

    private NoteRequest noteRequest;

    @BeforeEach
    public void setup() {
        requestToEntityMapper = new RequestToEntityMapper<>(modelMapper);

        note = NoteMock.createNote(1L, "Title 1", "Description 1", "Zagreb", LocalDate.of(2023, 2, 10));
        noteRequest = NoteRequestMock.createNoteRequest("Title 1", "Description 1", "Zagreb", LocalDate.of(2023, 2, 10));
    }

    @Test
    @DisplayName(value = "Test map NoteRequest to Note, expected ok")
    public void should_Map_NoteRequest_To_Note_And_Return_Mapped_Entity() {
        // Given
        BDDMockito.given(modelMapper.map(noteRequest, Note.class)).willReturn(note);

        // When
        final Note mappedNote = requestToEntityMapper.map(noteRequest, Note.class);

        // Then
        Assertions.assertEquals(1L, mappedNote.getId());
        Assertions.assertEquals("Title 1", mappedNote.getTitle());
        Assertions.assertEquals("Description 1", mappedNote.getDescription());
        Assertions.assertEquals("Zagreb", mappedNote.getLocation());
        Assertions.assertEquals(LocalDate.of(2023, 2, 10), mappedNote.getEventDate());
    }

    @Test
    @DisplayName(value = "Test map NoteRequest to Note, expected ok")
    public void should_Map_NoteRequest_To_Note_And_Return_Void() {
        // Given
        BDDMockito.doNothing().when(modelMapper).map(noteRequest, note);

        // When
        requestToEntityMapper.map(noteRequest, note);

        // Then
        Mockito.verify(modelMapper).map(noteRequest, note);
    }
}
