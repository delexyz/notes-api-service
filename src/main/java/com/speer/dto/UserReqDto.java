package com.speer.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserReqDto {
    private String firstName;
    private String lastName;
    private String email;
    private String password;

    public Map<Boolean, String> isValid() {
        if (firstName == null || firstName.isEmpty()) {
            return Collections.singletonMap(false, "First name is required");
        }
        if (lastName == null || lastName.isEmpty()) {
            return Collections.singletonMap(false, "Last name is required");
        }
        if (email == null || email.isEmpty()) {
            return Collections.singletonMap(false, "Email is required");
        }
        if (password == null || password.isEmpty()) {
            return Collections.singletonMap(false, "Password is required");
        }
        return Collections.singletonMap(true, "Valid");
    }
}
