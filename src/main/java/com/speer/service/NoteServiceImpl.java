package com.speer.service;

import com.speer.configuration.security.JwtProvider;
import com.speer.dto.NoteReqDto;
import com.speer.dto.NoteResDto;
import com.speer.dto.NoteUpdDto;
import com.speer.dto.ResponseDto;
import com.speer.entity.Note;
import com.speer.entity.User;
import com.speer.repository.NoteRepository;
import com.speer.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class NoteServiceImpl implements NoteService {

    @Autowired
    private NoteRepository noteRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    JwtProvider jwtProvider;

    @Override
    public ResponseDto createNote(NoteReqDto noteReqDto) {
        if(noteReqDto.isValid().containsKey(false)) {
            return new ResponseDto(false, noteReqDto.isValid().get(false), noteReqDto);
        }
        String token = (String) SecurityContextHolder.getContext().getAuthentication().getCredentials();
        Long userId = jwtProvider.extractUserId(token);
        Optional<User> optionalUser = userRepository.findById(userId);
        if(optionalUser.isEmpty()) {
            return new ResponseDto(false, "User not found with this ID", noteReqDto);
        }
        User user = optionalUser.get();
        Note note = new Note(noteReqDto, user);
        noteRepository.save(note);
        NoteResDto noteResDto = new NoteResDto(note);
        return new ResponseDto(true, "Note created successfully", noteResDto);
    }

    @Override
    public ResponseDto updateNote(NoteUpdDto noteUpdDto) {
        Optional<Note> optionalNote = noteRepository.findById(noteUpdDto.getId());
        if(optionalNote.isEmpty()) {
            return new ResponseDto(false, "No note was found with this ID", noteUpdDto);
        }
        Note note = optionalNote.get();
        String token = (String) SecurityContextHolder.getContext().getAuthentication().getCredentials();
        Long userId = jwtProvider.extractUserId(token);
        Optional<User> optionalUser = userRepository.findById(userId);
        if(optionalUser.isEmpty()) {
            return new ResponseDto(false, "Unauthorized user attempting to update note", noteUpdDto);
        }
        User user = optionalUser.get();
        if(!note.getCreatedBy().getId().equals(user.getId())) {
            return new ResponseDto(false, "Unauthorized user attempting to update note", noteUpdDto);
        }
        note.update(noteUpdDto);
        noteRepository.save(note);
        NoteResDto updateNoteResDto = new NoteResDto(note);
        return new ResponseDto(true, "Note updated successfully", updateNoteResDto);
    }

    @Override
    public ResponseDto deleteNoteById(Long noteId) {
        Optional<Note> optionalNote = noteRepository.findById(noteId);
        if(optionalNote.isEmpty()) {
            return new ResponseDto(false, "No note was found with this ID", noteId);
        }
        Note note = optionalNote.get();
        String token = (String) SecurityContextHolder.getContext().getAuthentication().getCredentials();
        Long userId = jwtProvider.extractUserId(token);
        Optional<User> optionalUser = userRepository.findById(userId);
        if(optionalUser.isEmpty()) {
            return new ResponseDto(false, "Unauthorized user attempting to delete note", noteId);
        }
        User user = optionalUser.get();
        if(!note.getCreatedBy().getId().equals(user.getId())) {
            return new ResponseDto(false, "Unauthorized user attempting to delete note", noteId);
        }
        noteRepository.deleteById(noteId);
        return new ResponseDto(true, "Note deleted successfully", noteId);
    }

    @Override
    public ResponseDto getNoteById(Long noteId) {
        Optional<Note> optionalNote = noteRepository.findById(noteId);
        if(optionalNote.isEmpty()) {
            return new ResponseDto(false, "No note was found with this ID", noteId);
        }
        Note note = optionalNote.get();
        NoteResDto noteResDto = new NoteResDto(note);
        return new ResponseDto(true, "Note found successfully", noteResDto);
    }

    @Override
    public List<NoteResDto> getAllNotesForAuthenticatedUser() {
        String token = (String) SecurityContextHolder.getContext().getAuthentication().getCredentials();
        Long userId = jwtProvider.extractUserId(token);
        List<NoteResDto> noteResDtoList = noteRepository.getNotesByUserId(userId)
                .stream()
                .map(NoteResDto::new)
                .toList();
        return noteResDtoList;
    }

    @Override
    public ResponseDto shareNoteWithUser(Long noteId, String email) {
        String token = (String) SecurityContextHolder.getContext().getAuthentication().getCredentials();
        Long userId = jwtProvider.extractUserId(token);
        Optional<User> optionalUser = userRepository.findById(userId);
        if(optionalUser.isEmpty()) {
            return new ResponseDto(false, "Unauthorized user attempting to share note", noteId);
        }
        User user = optionalUser.get();
        Optional<Note> optionalNote = noteRepository.findById(noteId);
        Optional<User> optionalBeneficiaryUser = userRepository.findByEmail(email);
        if(optionalNote.isEmpty()) {
            return new ResponseDto(false, "No note was found with this ID", noteId);
        }
        if(optionalBeneficiaryUser.isEmpty()) {
            return new ResponseDto(false, "No user was found with this email", email);
        }
        Note note = optionalNote.get();
        if(!note.getCreatedBy().getId().equals(user.getId())) {
            return new ResponseDto(false, "Unauthorized user attempting to share note", noteId);
        }
        User beneficiaryUser = optionalBeneficiaryUser.get();
        Note sharedNote = new Note();
        sharedNote.setTitle(note.getTitle());
        sharedNote.setBody(note.getBody());
        sharedNote.setCreatedBy(beneficiaryUser);
        noteRepository.save(sharedNote);
        NoteResDto noteResDto = new NoteResDto(sharedNote);
        return new ResponseDto(true, "Note shared successfully with " + email, noteResDto);
    }

    @Override
    public List<NoteResDto> searchNote(String searchKey) {
        String token = (String) SecurityContextHolder.getContext().getAuthentication().getCredentials();
        Long userId = jwtProvider.extractUserId(token);
        Optional<User> optionalUser = userRepository.findById(userId);
        if(optionalUser.isEmpty()) {
            return new ArrayList<>();
        }
        List<NoteResDto> noteResDtoList = noteRepository.searchByBodyOrTitleForUserId(searchKey, userId)
                .stream()
                .map(NoteResDto::new)
                .toList();
        return noteResDtoList;
    }
}
