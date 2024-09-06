package com.note.test;
import com.note.model.Note;
import com.note.service.NoteService;
import com.note.util.ServiceException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;

import java.util.List;

import org.junit.Before;
import org.junit.Test;


public class NoteServiceTest {

    private NoteService noteService;

    @Before
    void setUp() {
        noteService = new NoteService();
    }

    @Test
    void testCreateNote() {
        Note note = new Note();
        note.setTitle("Test Note");
        note.setContent("This is a test note");

        Note createdNote = noteService.create(note);

        assertNotNull(createdNote.getId());
        assertEquals("Test Note", createdNote.getTitle());
        assertEquals("This is a test note", createdNote.getContent());
    }

    @Test
    void testGetAllNotes() {
        Note note1 = new Note();
        note1.setTitle("Note 1");
        note1.setContent("Content 1");

        Note note2 = new Note();
        note2.setTitle("Note 2");
        note2.setContent("Content 2");

        noteService.create(note1);
        noteService.create(note2);

        List<Note> allNotes = noteService.getAll();

        assertEquals(2, allNotes.size());
    }

    @Test
    void testGetByIdNoteExists() throws ServiceException {
        Note note = new Note();
        note.setTitle("Existing Note");
        note.setContent("Existing Content");

        Note createdNote = noteService.create(note);
        Long noteId = createdNote.getId();

        Note foundNote = noteService.getById(noteId);

        assertNotNull(foundNote);
        assertEquals(noteId, foundNote.getId());
        assertEquals("Existing Note", foundNote.getTitle());
        assertEquals("Existing Content", foundNote.getContent());
    }

    @Test
    void testGetByIdNoteNotExists() {
        Long nonExistentId = 999L;

        ServiceException exception = assertThrows(ServiceException.class, () -> {
            noteService.getById(nonExistentId);
        });

        assertEquals(String.format(NoteService.NOTE_NOT_FOUND_MESSAGE, nonExistentId), exception.getMessage());
    }

    @Test
    void testUpdateNoteExists() throws ServiceException {
        Note note = new Note();
        note.setTitle("Original Note");
        note.setContent("Original Content");

        Note createdNote = noteService.create(note);
        Long noteId = createdNote.getId();

        Note updatedNote = new Note();
        updatedNote.setTitle("Updated Note");
        updatedNote.setContent("Updated Content");

        Note result = noteService.update(noteId, updatedNote);

        assertNotNull(result);
        assertEquals(noteId, result.getId());
        assertEquals("Updated Note", result.getTitle());
        assertEquals("Updated Content", result.getContent());
        assertEquals("00", result.getStatusCode());
        assertEquals("Success", result.getStatusDescription());
    }

    @Test
    void testUpdateNoteNotExists() {
        Long nonExistentId = 999L;
        Note updatedNote = new Note();
        updatedNote.setTitle("Updated Note");
        updatedNote.setContent("Updated Content");

        ServiceException exception = assertThrows(ServiceException.class, () -> {
            noteService.update(nonExistentId, updatedNote);
        });

        assertEquals(String.format(NoteService.NOTE_NOT_FOUND_MESSAGE, nonExistentId), exception.getMessage());
    }

    @Test
    void testDeleteNoteExists() throws ServiceException {
        Note note = new Note();
        note.setTitle("Note to Delete");
        note.setContent("Content to Delete");

        Note createdNote = noteService.create(note);
        Long noteId = createdNote.getId();

        Note result = noteService.delete(noteId);

        assertNotNull(result);
        assertEquals("00", result.getStatusCode());
        assertEquals("Success", result.getStatusDescription());

        ServiceException exception = assertThrows(ServiceException.class, () -> {
            noteService.getById(noteId);
        });
        assertEquals(String.format(NoteService.NOTE_NOT_FOUND_MESSAGE, noteId), exception.getMessage());
    }

    @Test
    void testDeleteNoteNotExists() {
        Long nonExistentId = 999L;

        ServiceException exception = assertThrows(ServiceException.class, () -> {
            noteService.delete(nonExistentId);
        });

        assertEquals(String.format(NoteService.NOTE_NOT_FOUND_MESSAGE, nonExistentId), exception.getMessage());
    }
}