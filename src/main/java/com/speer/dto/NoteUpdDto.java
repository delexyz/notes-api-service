package com.speer.dto;

import java.util.Collections;
import java.util.Map;


public class NoteUpdDto {
    private Long id;
    private String title;
    private String body;

    public NoteUpdDto() {
    }

    public NoteUpdDto(Long id, String title, String body) {
        this.id = id;
        this.title = title;
        this.body = body;
    }

    public Map<Boolean, String> isValid() {
        if (id == null) {
            return Collections.singletonMap(false, "Note ID is required");
        }
        if (title == null || title.isEmpty()) {
            return Collections.singletonMap(false, "Title is required");
        }
        return Collections.singletonMap(true, "Valid");
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
}
