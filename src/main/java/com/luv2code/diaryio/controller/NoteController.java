package com.luv2code.diaryio.controller;

import com.luv2code.diaryio.dto.NoteRequest;
import com.luv2code.diaryio.dto.NoteResponse;
import com.luv2code.diaryio.mapper.EntityToResponseMapper;
import com.luv2code.diaryio.mapper.RequestToEntityMapper;
import com.luv2code.diaryio.model.Note;
import com.luv2code.diaryio.service.NoteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/notes")
public class NoteController {

    private static final Logger LOGGER = LoggerFactory.getLogger(NoteController.class);

    private final NoteService noteService;

    private final RequestToEntityMapper<Note, NoteRequest> requestToEntityMapper;

    private final EntityToResponseMapper<Note, NoteResponse> entityToResponseMapper;

    @Autowired
    public NoteController(final NoteService noteService,
                          final RequestToEntityMapper<Note, NoteRequest> requestToEntityMapper,
                          final EntityToResponseMapper<Note, NoteResponse> entityToResponseMapper) {
        this.noteService = noteService;
        this.requestToEntityMapper = requestToEntityMapper;
        this.entityToResponseMapper = entityToResponseMapper;
    }

    @PostMapping
    public ResponseEntity<HttpStatus> save(@Valid @RequestBody final NoteRequest dto) {
        final Note mappedNote = requestToEntityMapper.map(dto, Note.class);
        LOGGER.debug("Successfully mapped NoteRequest to Note.");

        noteService.save(mappedNote);
        LOGGER.info("Successfully created new Note.");
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<NoteResponse> findById(@PathVariable final Long id) {
        final Note searchedNote = noteService.findById(id);
        LOGGER.info("Successfully found searched Note. [id={}]", searchedNote.getId());

        final NoteResponse dto = entityToResponseMapper.map(searchedNote, NoteResponse.class);
        LOGGER.debug("Successfully mapped Note to NoteResponse.");
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }

    @GetMapping
    public ResponseEntity<List<NoteResponse>> findAll() {
        final List<Note> searchedNotes = noteService.findAll();
        LOGGER.info("Successfully found all searched Notes. [total={}]", searchedNotes.size());

        final List<NoteResponse> dtos = entityToResponseMapper.mapToList(searchedNotes, NoteResponse.class);
        LOGGER.debug("Successfully mapped list of Note to list of NoteResponse.");
        return ResponseEntity.status(HttpStatus.OK).body(dtos);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<HttpStatus> update(@PathVariable final Long id,
                                             @Valid @RequestBody final NoteRequest dto) {
        final Note searchedNote = noteService.findById(id);
        LOGGER.info("Successfully found searched Note. [id={}]", searchedNote.getId());

        requestToEntityMapper.map(dto, searchedNote);
        LOGGER.debug("Successfully mapped Note with new field from NoteRequest.");

        noteService.update(searchedNote);
        LOGGER.info("Successfully updated selected Note. [id={}]", searchedNote.getId());
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable final Long id) {
        final Note selectedNote = noteService.findById(id);
        LOGGER.info("Successfully found searched Note. [id={}]", selectedNote.getId());

        noteService.delete(selectedNote);
        LOGGER.info("Successfully deleted selected Note.");
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
