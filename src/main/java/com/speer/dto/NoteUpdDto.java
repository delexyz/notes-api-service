package com.speer.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.Map;

@Data
@NoArgsConstructor
public class NoteUpdDto {
    private Long id;
    private String title;
    private String body;

    public Map<Boolean, String> isValid() {
        if (id == null) {
            return Collections.singletonMap(false, "Note ID is required");
        }
        if (title == null || title.isEmpty()) {
            return Collections.singletonMap(false, "Title is required");
        }
        return Collections.singletonMap(true, "Valid");
    }
}
