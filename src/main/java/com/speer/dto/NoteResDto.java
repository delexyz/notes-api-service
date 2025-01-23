package com.speer.dto;

import com.speer.entity.Note;


public class NoteResDto {
    private Long id;
    private String title;
    private String body;
    private UserResDto createdBy;

    public NoteResDto() {
    }

    public NoteResDto(Long id, String title, String body, UserResDto createdBy) {
        this.id = id;
        this.title = title;
        this.body = body;
        this.createdBy = createdBy;
    }

    public NoteResDto(Note note) {
        this.id = note.getId();
        this.title = note.getTitle();
        this.body = note.getBody();
        this.createdBy = new UserResDto(note.getCreatedBy());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public UserResDto getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(UserResDto createdBy) {
        this.createdBy = createdBy;
    }
}
