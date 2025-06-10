package com.tab.EnoteApp.controller;

import com.tab.EnoteApp.dto.NotesDto;
import com.tab.EnoteApp.exception.ResourceExistsException;
import com.tab.EnoteApp.repository.CategoryRepository;
import com.tab.EnoteApp.repository.NotesRepository;
import com.tab.EnoteApp.service.NotesService;
import com.tab.EnoteApp.util.CommonUtil;
import com.tab.EnoteApp.util.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/notes")
public class NotesController {

    @Autowired
    Validation validation;
    @Autowired
    NotesService notesService;

    @PostMapping("/save")
    public ResponseEntity<?> saveNotes(@RequestParam String notes,
                                       @RequestParam(required = false) MultipartFile file) throws Exception {

       // Boolean saveNotes=notesService.saveNotes(notesDto);
        Boolean saveNotes=notesService.saveNotes(notes,file);

        if(saveNotes){
            return CommonUtil.createResponseMessage("saved success", HttpStatus.CREATED);
        }

        return CommonUtil.errorResponse(saveNotes,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/")
    public ResponseEntity<?> getAllNotes(){

        List<NotesDto> allNotes = notesService.findAllNotes();

        if(ObjectUtils.isEmpty(allNotes)){
            return ResponseEntity.noContent().build();
        }
        return CommonUtil.createResponse(allNotes,HttpStatus.OK);
    }

}
