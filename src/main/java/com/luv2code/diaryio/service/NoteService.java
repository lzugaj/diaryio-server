package com.luv2code.diaryio.service;

import com.luv2code.diaryio.model.Note;

import java.util.List;

public interface NoteService {

    void save(final Note note);

    Note findById(final Long id);

    List<Note> findAll();

    void update(final Note note);

    void delete(final Note note);

}
