package com.speer.repository;

import com.speer.entity.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {
    @Query(value = "SELECT n FROM Note n WHERE n.createdBy.id = :userId")
    List<Note> getNotesByUserId(@Param("userId") Long userId);

    @Query(value = "SELECT n FROM Note n WHERE n.createdBy.id = :userId AND (n.body LIKE %:searchKey% OR n.title LIKE %:searchKey%)")
    List<Note> searchByBodyOrTitleForUserId(@Param("searchKey") String searchKey, @Param("userId") Long userId);
}
