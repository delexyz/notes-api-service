package com.speer.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.speer.dto.NoteReqDto;
import com.speer.dto.NoteUpdDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static com.speer.utils.DateUtils.getCurrentTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "notes")
public class Note {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title", length = 2000)
    private String title;

    @Column(name = "body", length = 30000)
    private String body;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @JsonIgnore
    @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User createdBy;

    public Note(NoteReqDto noteReqDto, User createdBy) {
        this.title = noteReqDto.getTitle();
        this.body = noteReqDto.getBody();
        this.createdBy = createdBy;
    }

    public void update(NoteUpdDto noteUpdDto) {
        this.title = noteUpdDto.getTitle();
        this.body = noteUpdDto.getBody();
    }

    @PrePersist
    public void prePersist() {
        createdAt = getCurrentTime();
    }
}
