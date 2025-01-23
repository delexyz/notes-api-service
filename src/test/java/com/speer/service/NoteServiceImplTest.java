package com.speer.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import com.speer.configuration.security.JwtProvider;
import com.speer.dto.*;
import com.speer.entity.Note;
import com.speer.entity.User;
import com.speer.repository.NoteRepository;
import com.speer.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;

import java.util.*;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class NoteServiceImplTest {

    @Mock
    private NoteRepository noteRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private JwtProvider jwtProvider;

    @Mock
    private Authentication authentication;

    @Mock
    private SecurityContext securityContext;

    @InjectMocks
    private NoteServiceImpl noteService;

    @BeforeEach
    public void setUp() {
        SecurityContextHolder.setContext(securityContext);
        lenient().when(securityContext.getAuthentication()).thenReturn(authentication);
    }

    @Test
    public void testCreateNote() {
        NoteReqDto noteReqDto = new NoteReqDto("Title", "Body");
        User user = new User();
        user.setId(1L);

        when(authentication.getCredentials()).thenReturn("token");
        when(jwtProvider.extractUserId("token")).thenReturn(1L);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(noteRepository.save(any(Note.class))).thenReturn(new Note());

        ResponseDto response = noteService.createNote(noteReqDto);

        assertTrue(response.getSuccessful());
        assertEquals("Note created successfully", response.getMessage());
    }

    @Test
    public void testUpdateNote() {
        NoteUpdDto noteUpdDto = new NoteUpdDto(1L, "Updated Title", "Updated Body");
        Note note = new Note();
        note.setId(1L);
        User user = new User();
        user.setId(1L);
        note.setCreatedBy(user);

        when(authentication.getCredentials()).thenReturn("token");
        when(jwtProvider.extractUserId("token")).thenReturn(1L);
        when(noteRepository.findById(1L)).thenReturn(Optional.of(note));
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(noteRepository.save(any(Note.class))).thenReturn(note);

        ResponseDto response = noteService.updateNote(noteUpdDto);

        assertTrue(response.getSuccessful());
        assertEquals("Note updated successfully", response.getMessage());
    }

    @Test
    public void testDeleteNoteById() {
        Note note = new Note();
        note.setId(1L);
        User user = new User();
        user.setId(1L);
        note.setCreatedBy(user);

        when(authentication.getCredentials()).thenReturn("token");
        when(jwtProvider.extractUserId("token")).thenReturn(1L);
        when(noteRepository.findById(1L)).thenReturn(Optional.of(note));
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        ResponseDto response = noteService.deleteNoteById(1L);

        assertTrue(response.getSuccessful());
        assertEquals("Note deleted successfully", response.getMessage());
    }

    @Test
    public void testGetNoteById() {
        Note note = new Note();
        note.setId(1L);
        User user = new User();
        user.setId(1L);
        note.setCreatedBy(user);

        when(noteRepository.findById(1L)).thenReturn(Optional.of(note));

        ResponseDto response = noteService.getNoteById(1L);

        assertTrue(response.getSuccessful());
        assertEquals("Note found successfully", response.getMessage());
    }

    @Test
    public void testGetAllNotesForAuthenticatedUser() {
        User user = new User();
        user.setId(1L);
        Note note = new Note();
        note.setCreatedBy(user);
        List<Note> notes = List.of(note);

        when(authentication.getCredentials()).thenReturn("token");
        when(jwtProvider.extractUserId("token")).thenReturn(1L);
        when(noteRepository.getNotesByUserId(1L)).thenReturn(notes);

        List<NoteResDto> response = noteService.getAllNotesForAuthenticatedUser();

        assertFalse(response.isEmpty());
    }

    @Test
    public void testShareNoteWithUser() {
        Note note = new Note();
        note.setId(1L);
        User user = new User();
        user.setId(1L);
        note.setCreatedBy(user);
        User beneficiaryUser = new User();
        beneficiaryUser.setEmail("test@example.com");

        when(authentication.getCredentials()).thenReturn("token");
        when(jwtProvider.extractUserId("token")).thenReturn(1L);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(noteRepository.findById(1L)).thenReturn(Optional.of(note));
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(beneficiaryUser));
        when(noteRepository.save(any(Note.class))).thenReturn(note);

        ResponseDto response = noteService.shareNoteWithUser(1L, "test@example.com");

        assertTrue(response.getSuccessful());
        assertEquals("Note shared successfully with test@example.com", response.getMessage());
    }

    @Test
    public void testSearchNote() {
        User user = new User();
        user.setId(1L);
        Note note = new Note();
        note.setCreatedBy(user);
        List<Note> notes = List.of(note);

        when(authentication.getCredentials()).thenReturn("token");
        when(jwtProvider.extractUserId("token")).thenReturn(1L);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(noteRepository.searchByBodyOrTitleForUserId("searchKey", 1L)).thenReturn(notes);

        List<NoteResDto> response = noteService.searchNote("searchKey");

        assertFalse(response.isEmpty());
    }
}