package com.speer.dto;


import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.Map;

@Data
@NoArgsConstructor
public class NoteReqDto {
    private String title;
    private String body;

    public Map<Boolean, String> isValid() {
        if (title == null || title.isEmpty()) {
            return Collections.singletonMap(false, "Title is required");
        }
        return Collections.singletonMap(true, "Valid");
    }
}
