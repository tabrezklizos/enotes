package com.tab.EnoteApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.tab.EnoteApp.entity.Notes;

import java.util.Optional;

public interface NotesRepository extends JpaRepository<Notes,Integer> {

    Optional<Notes> findByTitle(String title);

}
