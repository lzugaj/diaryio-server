package com.luv2code.diaryio.unit.service;

import com.luv2code.diaryio.mock.NoteMock;
import com.luv2code.diaryio.model.Note;
import com.luv2code.diaryio.repository.NoteRepository;
import com.luv2code.diaryio.service.NoteService;
import com.luv2code.diaryio.service.impl.NoteServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class NoteServiceImplTest {

    private NoteService noteService;

    @Mock
    private NoteRepository noteRepository;

    private Note firstNote;
    private Note secondNote;
    private Note thirdNote;

    private List<Note> notes;

    @BeforeEach
    public void setup() {
        noteService = new NoteServiceImpl(noteRepository);

        firstNote = NoteMock.createNote(1L, "First note", "Test description 1", "Paris", LocalDate.of(2020, 12, 23));
        secondNote = NoteMock.createNote(2L, "Second note", "Test description 2", "London", LocalDate.of(2023, 4, 1));
        thirdNote = NoteMock.createNote(3L, "Third note", "Test description 3", "Istanbul", LocalDate.of(2021, 7, 15));

        notes = List.of(firstNote, secondNote, thirdNote);
    }

    @Test
    @DisplayName(value = "Test save Note, expect ok")
    public void should_Save_New_Note() {
        // Given
        BDDMockito.given(noteRepository.save(firstNote)).willReturn(firstNote);

        // When
        noteService.save(firstNote);

        // Then
        Mockito.verify(noteRepository).save(firstNote);
    }

    @Test
    @DisplayName(value = "Test find Note by id, expect ok")
    public void should_Find_Note_By_Id() {
        // Given
        BDDMockito.given(noteRepository.findById(secondNote.getId())).willReturn(Optional.ofNullable(secondNote));

        // When
        final Note searchedNote = noteService.findById(secondNote.getId());

        // Then
        Assertions.assertEquals(2, searchedNote.getId());
    }

    @Test
    @DisplayName(value = "Test find Note by id, expected failed")
    public void should_Throw_Exception_When_Id_Not_Found() {
        // Given
        final Long notFoundId = 4L;

        // When
        final Exception exception = Assertions.assertThrows(
                EntityNotFoundException.class,
                () -> noteService.findById(notFoundId)
        );

        final String expectedMessage = String.format("Cannot find searched Note by given id. [id=%s]", notFoundId);
        final String actualMessage = exception.getMessage();

        // Then
        Assertions.assertEquals(expectedMessage, actualMessage);
    }

    @Test
    @DisplayName(value = "Test find all Notes, expected ok")
    public void should_Find_All_Notes() {
        // Given
        final List<Note> paginatedNotes = List.of(secondNote, thirdNote);
        final Page<Note> pageable = new PageImpl<>(paginatedNotes);
        BDDMockito.given(noteRepository.findAll(PageRequest.of(0, 2, Sort.by("eventDate").descending()))).willReturn(pageable);

        // When
        final Page<Note> searchedNotes = noteService.findAll(0, 2);

        // Then
        Assertions.assertEquals(2, searchedNotes.getContent().size());
        Assertions.assertEquals(LocalDate.of(2023, 4, 1), searchedNotes.getContent().get(0).getEventDate());
    }

    @Test
    @DisplayName(value = "Test update selected Note, expected ok")
    public void should_Update_Selected_Note() {
        // Given
        BDDMockito.given(noteRepository.save(thirdNote)).willReturn(thirdNote);

        // When
        noteService.update(thirdNote);

        // Then
        Mockito.verify(noteRepository).save(thirdNote);
    }

    @Test
    @DisplayName(value = "Test delete selected Note, expected ok")
    public void should_Delete_Selected_Note() {
        // Given
        BDDMockito.doNothing().when(noteRepository).delete(secondNote);

        // When
        noteService.delete(secondNote);

        // Then
        Mockito.verify(noteRepository).delete(secondNote);
    }
}
