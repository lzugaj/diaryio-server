package com.luv2code.diaryio.service;

import com.luv2code.diaryio.model.Note;
import org.springframework.data.domain.Page;

import java.util.List;

public interface NoteService {

    void save(final Note note);

    Note findById(final Long id);

    Page<Note> findAll(final Integer pageNumber, final Integer pageSize);

    void update(final Note note);

    void delete(final Note note);

}
