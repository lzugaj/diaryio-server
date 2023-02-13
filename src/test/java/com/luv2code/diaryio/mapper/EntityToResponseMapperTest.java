package com.luv2code.diaryio.mapper;

import com.luv2code.diaryio.dto.NoteResponse;
import com.luv2code.diaryio.mock.NoteMock;
import com.luv2code.diaryio.mock.NoteResponseMock;
import com.luv2code.diaryio.model.Note;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class EntityToResponseMapperTest {

    private EntityToResponseMapper<Note, NoteResponse> entityToResponseMapper;

    @Mock
    private ModelMapper modelMapper;

    private Note firstNote;
    private Note secondNote;
    private Note thirdNote;

    private NoteResponse noteResponse;

    private Collection<Note> notes;

    @BeforeEach
    public void setup() {
        entityToResponseMapper = new EntityToResponseMapper<>(modelMapper);

        firstNote = NoteMock.createNote(1L, "Title 1", "Description 1", "Zagreb", LocalDate.of(2023, 2, 10));
        secondNote = NoteMock.createNote(2L, "Title 2", "Description 2", "London", LocalDate.of(2022, 10, 23));
        thirdNote = NoteMock.createNote(3L, "Title 3", "Description 3", "Tokyo", LocalDate.of(2021, 5, 7));

        noteResponse = NoteResponseMock.createNoteResponse(1L, "Title 1", "Description 1", "Zagreb", LocalDate.of(2023, 2, 10));

        notes = List.of(firstNote, secondNote, thirdNote);
    }

    @Test
    @DisplayName(value = "Test map Note to NoteResponse, expected ok")
    public void should_Map_Note_To_NoteResponse_And_Return_Mapped_NoteResponse() {
        // Given
        BDDMockito.given(modelMapper.map(firstNote, NoteResponse.class)).willReturn(noteResponse);

        // When
        final NoteResponse mappedNoteResponse = entityToResponseMapper.map(firstNote, NoteResponse.class);

        // Then
        Assertions.assertEquals(1L, mappedNoteResponse.getId());
        Assertions.assertEquals("Title 1", mappedNoteResponse.getTitle());
        Assertions.assertEquals("Description 1", mappedNoteResponse.getDescription());
        Assertions.assertEquals("Zagreb", mappedNoteResponse.getLocation());
        Assertions.assertEquals(LocalDate.of(2023, 2, 10), mappedNoteResponse.getEventDate());
    }

    @Test
    @DisplayName(value = "Test map list of Notes to list of NoteResponse, expected ok")
    public void should_Map_List_Of_Notes_To_List_Of_NoteResponse_And_Return_Mapped_List_Of_NoteResponse() {
        // When
        final List<NoteResponse> mappedNoteResponse = entityToResponseMapper.mapToList(notes, NoteResponse.class);

        // Then
        Assertions.assertEquals(3, mappedNoteResponse.size());
    }
}
