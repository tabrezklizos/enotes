package com.tab.enote_app.repository;

import com.tab.enote_app.entity.FavNote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FavNoteRepository extends JpaRepository<FavNote,Integer> {

    List<FavNote> findAllFavNoteByUserId(Integer userId);

}
