package com.note.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.note.model.Note;
import com.note.rest.NoteRequest;
import com.note.service.NoteService;
import com.note.util.ServiceException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/notes")
@Tag(name = "Note Demo APIs", description = "Create a RESTful API that allows users to create, retrieve, update, and delete notes. Each note can consist of a title and a body.")
public class NoteController {

    private final NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @Operation(summary = "Create a new note.", operationId = "create")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful reponse", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Note.class)))})
    @PostMapping
    public ResponseEntity<Note> create(@RequestBody @Valid NoteRequest request) {
        Note note = new Note();
        note.setTitle(request.getTitle());
        note.setContent(request.getContent());
        Note createdNote = noteService.create(note);
        return new ResponseEntity<>(createdNote, HttpStatus.CREATED);
    }

    @Operation(summary = "Retrieve all notes.", operationId = "getAll")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful reponse", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Note.class)))})
    @GetMapping
    public ResponseEntity<List<Note>> getAll() {
        List<Note> notes = noteService.getAll();
        return new ResponseEntity<>(notes, HttpStatus.OK);
    }

    @Operation(summary = "Retrieve a specific note by ID.", operationId = "getById")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful reponse", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Note.class)))})
    @GetMapping("/{id}")
    public ResponseEntity<Note> getById(@PathVariable Long id) throws ServiceException {
        return new ResponseEntity<>(noteService.getById(id), HttpStatus.OK);
    }

    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<Object> handleServiceException(ServiceException ex) {
        return ex.toResponseEntity();
    }

    @Operation(summary = "Update a specific note.", operationId = "update")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful reponse", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Note.class)))})
    @PutMapping("/{id}")
    public ResponseEntity<Note> update(@PathVariable Long id, @RequestBody @Valid NoteRequest request) throws ServiceException {
        Note note = new Note();
        note.setTitle(request.getTitle());
        note.setContent(request.getContent());
        return new ResponseEntity<>(noteService.update(id, note), HttpStatus.OK);
    }

    @Operation(summary = "Delete a specific note.", operationId = "delete")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful reponse", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Note.class)))})
    @DeleteMapping("/{id}")
    public ResponseEntity<Note> delete(@PathVariable Long id) throws ServiceException {
        return new ResponseEntity<>(noteService.delete(id), HttpStatus.OK);
    }
}
