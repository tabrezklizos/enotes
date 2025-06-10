package com.tab.EnoteApp.service;

import com.tab.EnoteApp.dto.NotesDto;
import com.tab.EnoteApp.exception.ResourceExistsException;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface NotesService {

    List<NotesDto> findAllNotes();

    Boolean saveNotes(String notes, MultipartFile file) throws Exception;
}
