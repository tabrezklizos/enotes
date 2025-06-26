package com.tab.EnoteApp.repository;

import com.tab.EnoteApp.entity.FavNote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FavNoteRepository extends JpaRepository<FavNote,Integer> {

    List<FavNote> findAllFavNoteByUserId(Integer userId);

}
