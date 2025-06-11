package com.tab.EnoteApp.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.tab.EnoteApp.entity.Notes;

import java.util.List;
import java.util.Optional;

public interface NotesRepository extends JpaRepository<Notes,Integer> {

    Optional<Notes> findByTitle(String title);

    Page <Notes> findByCreatedBy(Integer userId, Pageable pageable);
}
