package com.tab.EnoteApp.controller;

import com.tab.EnoteApp.dto.FavNoteDto;
import com.tab.EnoteApp.dto.NotesDto;
import com.tab.EnoteApp.dto.NotesResponse;
import com.tab.EnoteApp.entity.FileDetails;
import com.tab.EnoteApp.service.NotesService;
import com.tab.EnoteApp.util.CommonUtil;
import com.tab.EnoteApp.util.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/notes")
public class NotesController {

    @Autowired
    private Validation validation;
    @Autowired
    private NotesService notesService;

    @PostMapping("/save")
    @PreAuthorize("hasRole('USER')")
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
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAllNotes(){
        List<NotesDto> allNotes = notesService.findAllNotes();
        if(CollectionUtils.isEmpty(allNotes)){
            return ResponseEntity.noContent().build();
        }
        return CommonUtil.createResponse(allNotes,HttpStatus.OK);
    }

        @GetMapping("/user-notes")
        @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getAllNotesByUserId(
            @RequestParam(name="pageNo",defaultValue ="0") Integer pageNo,
            @RequestParam(name="pageSize",defaultValue = "10") Integer pageSize
        ){

        Integer userId=1;

        NotesResponse allNotes = notesService.findAllNotesByUserId(userId,pageNo,pageSize);
        if(ObjectUtils.isEmpty(allNotes)){
            return ResponseEntity.noContent().build();
        }
        return CommonUtil.createResponse(allNotes,HttpStatus.OK);
    }


            @GetMapping("/downloadFile/{id}")
            @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<?> downloadFile(@PathVariable Integer id) throws Exception {

        FileDetails fileDetails =notesService.getFileDetails(id);
        byte[] data =notesService.downloadFile(fileDetails);
        String contentType =CommonUtil.getContentType(fileDetails.getOriginalFileName());

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.parseMediaType(contentType));
        httpHeaders.setContentDispositionFormData("attachment",fileDetails.getOriginalFileName());
        return ResponseEntity.ok().headers(httpHeaders).body(data);
    }


    @GetMapping("/delete-note/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> deleteNote(@PathVariable Integer id) throws Exception {

       Integer userId=1;

        Boolean softDeleted = notesService.softDeleteNote(userId, id);

        if(softDeleted){
            return CommonUtil.createResponseMessage("delete success", HttpStatus.OK);
        }
        return CommonUtil.errorResponse("internal server error  ",HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @GetMapping("/restore-note/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> restoreNote(@PathVariable Integer id) throws Exception {
        Integer userId=1;
        Boolean restored = notesService.restoreNote(userId, id);

        if(restored){
            return CommonUtil.createResponseMessage("restore success", HttpStatus.OK);
        }
        return CommonUtil.errorResponse("internal server error  ",HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/recycle-bin")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> recycleBinNotes() throws Exception {
        Integer userId=1;
        List<NotesDto> recycleNotes = notesService.findByCreatedByAndIsDeletedTrue(userId);
        if(CollectionUtils.isEmpty(recycleNotes)){
            return ResponseEntity.noContent().build();
        }
        return CommonUtil.createResponse(recycleNotes,HttpStatus.OK);
    }
    @DeleteMapping("/empty-recycle-bin")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> emptyRecycleBin( ) throws Exception {
            Integer userId=1;
            notesService.emptyRecycleBin(userId);
            return CommonUtil.createResponse("recycle bin emptied",HttpStatus.OK);
    }
    @DeleteMapping("/hard-delete-note/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> hardDeleteNote(@PathVariable Integer id) throws Exception {
        Integer userId = 1;
        notesService.hardDeleteNote(id);
        return CommonUtil.createResponse("delete success",HttpStatus.OK);
    }

    @PostMapping("/fav-note/{noteId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> saveFavNote(@PathVariable Integer noteId) throws Exception {
        notesService.saveFavNote(noteId);
        return CommonUtil.createResponse("favourite note added",HttpStatus.OK);
    }

    @DeleteMapping("/unFav-note/{favNoteId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> unFavNote(@PathVariable Integer favNoteId) throws Exception {
        notesService.unFavNote(favNoteId);
        return CommonUtil.createResponse("note un favourited",HttpStatus.OK);
    }
    @GetMapping("/fav-notes")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getFavNotes() throws Exception {
        List<FavNoteDto> favNotes = notesService.getFavNotes();
        return CommonUtil.createResponse(favNotes,HttpStatus.FOUND);
    }

    @GetMapping("/copy-notes/{noteId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> copyNotes(@PathVariable Integer noteId) throws Exception {
       Boolean copyNote= notesService.copyNote(noteId);

       if(copyNote){
           return CommonUtil.createResponse(" copied",HttpStatus.OK);

       }
        return CommonUtil.createResponse("not copied",HttpStatus.OK);
    }






}



















