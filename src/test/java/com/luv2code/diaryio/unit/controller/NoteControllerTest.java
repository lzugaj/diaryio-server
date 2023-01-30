package com.luv2code.diaryio.unit.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.luv2code.diaryio.dto.NoteRequest;
import com.luv2code.diaryio.dto.NoteResponse;
import com.luv2code.diaryio.mapper.EntityToResponseMapper;
import com.luv2code.diaryio.mapper.RequestToEntityMapper;
import com.luv2code.diaryio.mock.NoteMock;
import com.luv2code.diaryio.mock.NoteRequestMock;
import com.luv2code.diaryio.mock.NoteResponseMock;
import com.luv2code.diaryio.model.Note;
import com.luv2code.diaryio.service.NoteService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.List;

@WebMvcTest
public class NoteControllerTest {

    @MockBean
    private NoteService noteService;

    @MockBean
    private RequestToEntityMapper<Note, NoteRequest> requestToEntityMapper;

    @MockBean
    private EntityToResponseMapper<Note, NoteResponse> entityToResponseMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("201 - POST /notes")
    public void should_Save_New_Note() throws Exception {
        final NoteRequest dto = NoteRequestMock.createNoteRequest("First note", "Test description 1", "Paris", LocalDate.of(2020, 12, 23));
        final Note note = NoteMock.createNote(1L, "First note", "Test description 1", "Paris", LocalDate.of(2020, 12, 23));

        BDDMockito.given(requestToEntityMapper.map(dto, Note.class)).willReturn(note);
        BDDMockito.doNothing().when(noteService).save(note);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/notes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    /*@Test
    @DisplayName("400 - POST /notes")
    void should_Throw_Exception_When_Required_Attribute_Is_Null() throws Exception {
        final NoteRequest dto = NoteRequestMock.createNoteRequest("", "Test description 1", "Paris", LocalDate.of(2020, 12, 23));

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/notes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.httpStatusCode").value(400))
                .andExpect(MockMvcResultMatchers.jsonPath("$.httpStatus").value("BAD_REQUEST"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Field title must not be blank"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.path").value("/api/v1/notes"));
    }*/

    /*@Test
    @DisplayName("400 - POST /notes")
    void should_Throw_Exception_When_Required_Attribute_Is_Null() throws Exception {
        final NoteRequest dto = NoteRequestMock.createNoteRequest(null, "Test description 1", "Paris", LocalDate.of(2020, 12, 23));

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/notes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.httpStatusCode").value(400))
                .andExpect(MockMvcResultMatchers.jsonPath("$.httpStatus").value("BAD_REQUEST"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Field title must not be blank"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.path").value("/api/v1/notes"));
    }*/

    @Test
    @DisplayName("200 - GET /notes/2")
    public void should_Find_Note_By_Id() throws Exception {
        final Note searchedNote = NoteMock.createNote(2L, "Second note", "Test description 2", "London", LocalDate.of(2021, 5, 1));
        final NoteResponse dto = NoteResponseMock.createNoteResponse(2L, "Second note", "Test description 2", "London", LocalDate.of(2021, 5, 1));

        final Long searchedId = 2L;
        BDDMockito.given(noteService.findById(searchedId)).willReturn(searchedNote);
        BDDMockito.given(entityToResponseMapper.map(searchedNote, NoteResponse.class)).willReturn(dto);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/notes/{id}", 2)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Second note"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("Test description 2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.location").value("London"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.eventDate").value("2021-05-01"));
    }

    /*@Test
    @DisplayName("404 - GET /notes/109")
    public void should_Throw_Exception_When_Entity_Was_Not_Founded_In_Database() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/notes/{id}", 109)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.httpStatusCode").value(404))
                .andExpect(MockMvcResultMatchers.jsonPath("$.httpStatus").value("NOT_FOUND"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Cannot find Note by given id. [id=109]"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.path").value("/api/v1/note/109"));
    }*/

    @Test
    @DisplayName("200 - GET /notes")
    public void should_Find_All_Notes() throws Exception {
        final Note firstNote = NoteMock.createNote(1L, "First note", "Test description 1", "Paris", LocalDate.of(2020, 12, 23));
        final Note secondNote = NoteMock.createNote(2L, "Second note", "Test description 2", "London", LocalDate.of(2023, 4, 1));
        final Note thirdNote = NoteMock.createNote(3L, "Third note", "Test description 3", "Istanbul", LocalDate.of(2021, 7, 15));

        final List<Note> notes = List.of(firstNote, secondNote, thirdNote);

        final NoteResponse firstNoteResponse = NoteResponseMock.createNoteResponse(1L, "First note", "Test description 1", "Paris", LocalDate.of(2020, 12, 23));
        final NoteResponse secondNoteResponse = NoteResponseMock.createNoteResponse(2L, "Second note", "Test description 2", "London", LocalDate.of(2023, 4, 1));
        final NoteResponse thirdNoteResponse = NoteResponseMock.createNoteResponse(3L, "Third note", "Test description 3", "Istanbul", LocalDate.of(2021, 7, 15));

        final List<NoteResponse> dtos = List.of(firstNoteResponse, secondNoteResponse, thirdNoteResponse);

        BDDMockito.given(noteService.findAll()).willReturn(notes);
        BDDMockito.given(entityToResponseMapper.mapToList(notes, NoteResponse.class)).willReturn(dtos);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/notes")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(dtos.size()));
    }

    @Test
    @DisplayName("200 - UPDATE /notes/1")
    public void should_Update_Selected_Note() throws Exception {
        final Note searchedNote = NoteMock.createNote(1L, "First note", "Test description 1", "Paris", LocalDate.of(2020, 12, 23));
        final NoteRequest dto = NoteRequestMock.createNoteRequest("First note", "Test description 1", "Paris", LocalDate.of(2020, 12, 23));

        final Long searchedId = 1L;
        BDDMockito.given(noteService.findById(searchedId)).willReturn(searchedNote);
        BDDMockito.doNothing().when(requestToEntityMapper).map(dto, searchedNote);
        BDDMockito.doNothing().when(noteService).update(searchedNote);

        mockMvc.perform(MockMvcRequestBuilders.patch("/api/v1/notes/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("200 - DELETE /notes/2")
    public void should_Delete_Selected_Note() throws Exception {
        final Note searchedNote = NoteMock.createNote(2L, "Second note", "Test description 2", "London", LocalDate.of(2021, 5, 1));

        final Long searchedId = 2L;
        BDDMockito.given(noteService.findById(searchedId)).willReturn(searchedNote);
        BDDMockito.doNothing().when(noteService).delete(searchedNote);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/notes/{id}", 2)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
