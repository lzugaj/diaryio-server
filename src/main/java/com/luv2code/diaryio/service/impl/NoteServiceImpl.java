package com.luv2code.diaryio.service.impl;

import com.luv2code.diaryio.model.Note;
import com.luv2code.diaryio.repository.NoteRepository;
import com.luv2code.diaryio.service.NoteService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

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
    public Page<Note> findAll(final Integer pageNumber, final Integer pageSize) {
        LOGGER.debug("Searching all Notes for current page. [currentPage={}]", pageNumber);

        final Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("eventDate").descending());
        return noteRepository.findAll(pageable);
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
