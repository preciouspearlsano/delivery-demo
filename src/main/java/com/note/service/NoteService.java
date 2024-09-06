package com.note.service;
import org.springframework.stereotype.Service;

import com.note.model.Note;
import com.note.util.ServiceException;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class NoteService {
    private final Map<Long, Note> notes = new HashMap<>();
    private final AtomicLong idGenerator = new AtomicLong();
    public static final String NOTE_NOT_FOUND_MESSAGE = "Note with ID %d not found";

    public Note create(Note note) {
        long id = idGenerator.incrementAndGet();
        note.setId(id);
        notes.put(id, note);
        return note;
    }

    public List<Note> getAll() {
        return new ArrayList<>(notes.values());
    }

    public Note getById(Long id) throws ServiceException {
    	Note note = notes.get(id);
        if (note == null) {
            throw new ServiceException(String.format(NOTE_NOT_FOUND_MESSAGE, id));
        }
        return note;
    }

    public Note update(Long id, Note note) throws ServiceException {
    	Note existingRecord = notes.get(id);
    	 if (existingRecord == null) {
             throw new ServiceException(String.format(NOTE_NOT_FOUND_MESSAGE, id));
         }
        if (notes.containsKey(id)) {
            note.setId(id);
            note.setStatusCode("00");
            note.setStatusDescription("Success");
            notes.put(id, note);
            return note;
        }
        return null;
    }

    public Note delete(Long id) throws ServiceException {
    	Note response = new Note();
        if (notes.get(id) == null) {
            throw new ServiceException(String.format(NOTE_NOT_FOUND_MESSAGE, id));
        }
        notes.remove(id);
        response.setStatusCode("00");
        response.setStatusDescription("Success");
        return response;
        
    }
}