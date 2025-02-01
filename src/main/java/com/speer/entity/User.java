package com.speer.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.speer.dto.UserReqDto;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDateTime;
import java.util.Set;

import static com.speer.utils.DateUtils.getCurrentTime;

@Data
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @JsonIgnore
    @OneToMany(mappedBy = "createdBy")
    private Set<Note> notes;

    public User(UserReqDto userReqDto) {
        this.firstName = userReqDto.getFirstName();
        this.lastName = userReqDto.getLastName();
        this.email = userReqDto.getEmail();
        this.password = userReqDto.getPassword();
    }

    @PrePersist
    public void prePersist() {
        createdAt = getCurrentTime();
    }
}
