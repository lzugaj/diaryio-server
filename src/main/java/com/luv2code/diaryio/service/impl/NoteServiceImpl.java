package com.luv2code.diaryio.service.impl;

import com.luv2code.diaryio.model.Note;
import com.luv2code.diaryio.repository.NoteRepository;
import com.luv2code.diaryio.service.NoteService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NoteServiceImpl implements NoteService {

    private static final Logger LOGGER = LoggerFactory.getLogger(NoteServiceImpl.class);

    private final NoteRepository noteRepository;

    @Autowired
    public NoteServiceImpl(final NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    @Override
    @Transactional
    public void save(final Note note) {
        LOGGER.debug("Saving new Note to database.");
        note.setCreatedAt(LocalDateTime.now());
        noteRepository.save(note);
    }

    @Override
    public Note findById(final Long id) {
        LOGGER.debug("Searching Note by given id. [id={}]", id);
        return noteRepository.findById(id)
                .orElseThrow(() -> {
                    LOGGER.error("Cannot find searched Note by given id. [id={}]", id);
                    throw new EntityNotFoundException(
                            String.format("Cannot find searched Note by given id. [id=%s]", id)
                    );
                });
    }

    @Override
    public List<Note> findAll() {
        LOGGER.debug("Searching all Notes.");
        return noteRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(Note::getEventDate).reversed())
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void update(final Note note) {
        LOGGER.debug("Updating Note by given id. [id={}]", note.getId());
        note.setModifiedAt(LocalDateTime.now());
        noteRepository.save(note);
    }

    @Override
    @Transactional
    public void delete(final Note note) {
        LOGGER.debug("Deleting selected Note. [id={}]", note.getId());
        noteRepository.delete(note);
    }
}
