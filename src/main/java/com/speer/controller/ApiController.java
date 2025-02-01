package com.speer.controller;


import com.speer.dto.*;
import com.speer.service.NoteService;
import com.speer.service.UserService;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Tag(name = "API Endpoints")
@RestController
@RequestMapping("/api")
public class ApiController {

    @Autowired
    UserService userService;

    @Autowired
    NoteService noteService;

    @Operation(summary = "Create a new user account")
    @PostMapping(value = "/auth/signup")
    @RateLimiter(name = "default")
    public ResponseEntity<ResponseDto> signUp(@RequestBody UserReqDto userReqDto) {
        ResponseDto responseDto = userService.signUp(userReqDto);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @Operation(summary = "Log in to an existing user account and receive an access token")
    @PostMapping(value = "/auth/login")
    @RateLimiter(name = "default")
    public ResponseEntity<AuthUserResDto> login(@RequestBody UserCredentialDto userCredentialDto) {
        AuthUserResDto authUserResDto = userService.login(userCredentialDto);
        return new ResponseEntity<>(authUserResDto, HttpStatus.OK);
    }

    @Operation(summary = "Get a list of all notes for the authenticated user")
    @GetMapping("/notes")
    @RateLimiter(name = "default")
    public ResponseEntity<List<NoteResDto>> getAllNotesForAuthenticatedUser() {
        List<NoteResDto> noteResDtoList = noteService.getAllNotesForAuthenticatedUser();
        return new ResponseEntity<>(noteResDtoList, HttpStatus.OK);
    }

    @Operation(summary = "Get a note by ID for the authenticated user")
    @GetMapping("/notes/{noteId}")
    @RateLimiter(name = "default")
    public ResponseEntity<ResponseDto> getNoteById(@PathVariable("noteId") Long noteId) {
        ResponseDto responseDto = noteService.getNoteById(noteId);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @Operation(summary = "Create a new note for the authenticated user")
    @PostMapping(value = "/notes")
    @RateLimiter(name = "default")
    public ResponseEntity<ResponseDto> createNote(@RequestBody NoteReqDto noteReqDto) {
        ResponseDto responseDto = noteService.createNote(noteReqDto);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @Operation(summary = "Update an existing note by ID for the authenticated user")
    @PutMapping(value = "/notes")
    @RateLimiter(name = "default")
    public ResponseEntity<ResponseDto> updateNote(@RequestBody NoteUpdDto noteUpdDto) {
        ResponseDto responseDto = noteService.updateNote(noteUpdDto);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @Operation(summary = "Delete a note by ID for the authenticated user")
    @DeleteMapping("/notes/{noteId}")
    @RateLimiter(name = "default")
    public ResponseEntity<ResponseDto> deleteNoteById(@PathVariable("noteId") Long noteId) {
        ResponseDto responseDto = noteService.deleteNoteById(noteId);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @Operation(summary = "Share note with other user by email")
    @PostMapping(value = "/notes/{noteId}/share/{beneficiaryEmail}")
    @RateLimiter(name = "default")
    public ResponseEntity<ResponseDto> shareNoteWithUser(@PathVariable("noteId") Long noteId,
                                                         @PathVariable("beneficiaryEmail") String beneficiaryEmail) {
        ResponseDto responseDto = noteService.shareNoteWithUser(noteId, beneficiaryEmail);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @Operation(summary = "Search for notes based on keywords for the authenticated user")
    @GetMapping("/search")
    @RateLimiter(name = "default")
    public ResponseEntity<List<NoteResDto>> searchForNotesByKeyword(@RequestParam("q") String query) {
        List<NoteResDto> noteResDtos = noteService.searchNote(query);
        return new ResponseEntity<>(noteResDtos, HttpStatus.OK);
    }

    @Operation(summary = "This is a test endpoint")
    @GetMapping("/ping")
    @RateLimiter(name = "default")
    public ResponseEntity<Map<String, String>> ping() {
//        int i = 0;
//        while(LocalDateTime.now().isBefore(LocalDateTime.now().plusHours(12))) {
//            // Simulate a long running process
//            i++;
//            System.out.println("Service is running the " + i + "th iteration...");
//        }
        return new ResponseEntity<>(Collections.singletonMap("status", "Service is running..."), HttpStatus.OK);
    }
}
