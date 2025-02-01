package com.speer.dto;

import com.speer.entity.Note;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class NoteResDto {
    private Long id;
    private String title;
    private String body;
    private UserResDto createdBy;

    public NoteResDto(Note note) {
        this.id = note.getId();
        this.title = note.getTitle();
        this.body = note.getBody();
        this.createdBy = new UserResDto(note.getCreatedBy());
    }
}
