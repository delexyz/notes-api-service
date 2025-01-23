package com.speer.service;

import com.speer.dto.NoteReqDto;
import com.speer.dto.NoteResDto;
import com.speer.dto.NoteUpdDto;
import com.speer.dto.ResponseDto;

import java.util.List;

public interface NoteService {
    ResponseDto createNote(NoteReqDto noteReqDto);
    ResponseDto updateNote(NoteUpdDto noteUpdDto);
    ResponseDto deleteNoteById(Long noteId);
    ResponseDto getNoteById(Long noteId);
    List<NoteResDto> getAllNotesForAuthenticatedUser();
    ResponseDto shareNoteWithUser(Long noteId, String email);
    List<NoteResDto> searchNote(String searchKey);
}
