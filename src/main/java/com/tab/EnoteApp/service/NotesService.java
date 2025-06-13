package com.tab.EnoteApp.service;

import com.tab.EnoteApp.dto.FavNoteDto;
import com.tab.EnoteApp.dto.NotesDto;
import com.tab.EnoteApp.dto.NotesResponse;
import com.tab.EnoteApp.entity.FileDetails;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface NotesService {

    List<NotesDto> findAllNotes();

    Boolean saveNotes(String notes, MultipartFile file) throws Exception;

    byte[] downloadFile(FileDetails fileDetails) throws Exception;

    FileDetails getFileDetails(Integer id) throws Exception;

    NotesResponse findAllNotesByUserId(Integer userId,Integer pageNo,Integer pageSize);

    Boolean softDeleteNote(Integer userId, Integer id) throws Exception;

    Boolean restoreNote(Integer userId, Integer id) throws Exception;

    List<NotesDto> findByCreatedByAndIsDeletedTrue(Integer userId) throws Exception;

    void emptyRecycleBin(Integer userId) throws Exception;

    void hardDeleteNote(Integer id) throws Exception;


    void saveFavNote(Integer noteId) throws Exception;
    void unFavNote(Integer favNoteId) throws Exception;
    List<FavNoteDto> getFavNotes() throws Exception;

}
