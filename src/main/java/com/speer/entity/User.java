package com.speer.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.speer.dto.UserReqDto;
import jakarta.persistence.*;


import java.time.LocalDateTime;
import java.util.Set;

import static com.speer.utils.DateUtils.getCurrentTime;

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

    public User() {
    }

    public User(UserReqDto userReqDto) {
        this.firstName = userReqDto.getFirstName();
        this.lastName = userReqDto.getLastName();
        this.email = userReqDto.getEmail();
        this.password = userReqDto.getPassword();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Set<Note> getNotes() {
        return notes;
    }

    public void setNotes(Set<Note> notes) {
        this.notes = notes;
    }

    @PrePersist
    public void prePersist() {
        createdAt = getCurrentTime();
    }
}
