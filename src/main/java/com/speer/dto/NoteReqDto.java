package com.speer.dto;


import java.util.Collections;
import java.util.Map;


public class NoteReqDto {
    private String title;
    private String body;

    public NoteReqDto() {
    }

    public NoteReqDto(String title, String body) {
        this.title = title;
        this.body = body;
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

    public Map<Boolean, String> isValid() {
        if (title == null || title.isEmpty()) {
            return Collections.singletonMap(false, "Title is required");
        }
        return Collections.singletonMap(true, "Valid");
    }
}
