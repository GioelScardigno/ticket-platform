package com.lessons.java.wdpt6.ticket_platform.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lessons.java.wdpt6.ticket_platform.model.Note;

public interface NoteRepo extends JpaRepository<Note, Integer> {
    
}
