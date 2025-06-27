package com.tab.EnoteApp.controllerImpl;

import com.tab.EnoteApp.controller.NotesController;
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
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
public class NotesControllerImpl implements NotesController {

    @Autowired
    private Validation validation;
    @Autowired
    private NotesService notesService;

    @Override
    public ResponseEntity<?> saveNotes(String notes,MultipartFile file) throws Exception {
       // Boolean saveNotes=notesService.saveNotes(notesDto);
        Boolean saveNotes=notesService.saveNotes(notes,file);
        if(saveNotes){
            return CommonUtil.createResponseMessage("saved success", HttpStatus.CREATED);
        }
        return CommonUtil.errorResponse(saveNotes,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<?> getAllNotes(){
        List<NotesDto> allNotes = notesService.findAllNotes();
        if(CollectionUtils.isEmpty(allNotes)){
            return ResponseEntity.noContent().build();
        }
        return CommonUtil.createResponse(allNotes,HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getAllNotesByUserId(Integer pageNo,Integer pageSize){

        NotesResponse allNotes = notesService.findAllNotesByUserId(pageNo,pageSize);
        if(ObjectUtils.isEmpty(allNotes)){
            return ResponseEntity.noContent().build();
        }
        return CommonUtil.createResponse(allNotes,HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getSearchNotesByUser(
             String keyword,
             Integer pageNo,
             Integer pageSize
    ){

        NotesResponse allNotes = notesService.searchNotesByUser(keyword,pageNo,pageSize);
        if(ObjectUtils.isEmpty(allNotes)){
            return ResponseEntity.noContent().build();
        }
        return CommonUtil.createResponse(allNotes,HttpStatus.OK);
    }



    @Override
    public ResponseEntity<?> downloadFile( Integer id) throws Exception {

        FileDetails fileDetails =notesService.getFileDetails(id);
        byte[] data =notesService.downloadFile(fileDetails);
        String contentType =CommonUtil.getContentType(fileDetails.getOriginalFileName());

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.parseMediaType(contentType));
        httpHeaders.setContentDispositionFormData("attachment",fileDetails.getOriginalFileName());
        return ResponseEntity.ok().headers(httpHeaders).body(data);
    }

    @Override
    public ResponseEntity<?> deleteNoteByUser( Integer id) throws Exception {
        Boolean softDeleted = notesService.softDeleteNote(id);
        if(softDeleted){
            return CommonUtil.createResponseMessage("delete success", HttpStatus.OK);
        }
        return CommonUtil.errorResponse("internal server error  ",HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<?> restoreNoteByUser( Integer id) throws Exception {
        Boolean restored = notesService.restoreNote(id);
        if(restored){
            return CommonUtil.createResponseMessage("restore success", HttpStatus.OK);
        }
        return CommonUtil.errorResponse("internal server error  ",HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<?> recycleBinNotesByUser() throws Exception {
        List<NotesDto> recycleNotes = notesService.findByCreatedByAndIsDeletedTrue();
        if(CollectionUtils.isEmpty(recycleNotes)){
            return ResponseEntity.noContent().build();
        }
        return CommonUtil.createResponse(recycleNotes,HttpStatus.OK);
    }
    @Override
    public ResponseEntity<?> emptyRecycleBin( ) throws Exception {
            notesService.emptyRecycleBin();
            return CommonUtil.createResponse("recycle bin emptied",HttpStatus.OK);
    }
    @Override
    public ResponseEntity<?> hardDeleteNote( Integer id) throws Exception {
        notesService.hardDeleteNote(id);
        return CommonUtil.createResponse("delete success",HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> saveFavNote( Integer noteId) throws Exception {
        notesService.saveFavNote(noteId);
        return CommonUtil.createResponse("favourite note added",HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> unFavNote( Integer favNoteId) throws Exception {
        notesService.unFavNote(favNoteId);
        return CommonUtil.createResponse("note un favourited",HttpStatus.OK);
    }
    @Override
    public ResponseEntity<?> getFavNotes() throws Exception {
        List<FavNoteDto> favNotes = notesService.getFavNotes();
        return CommonUtil.createResponse(favNotes,HttpStatus.FOUND);
    }

    @Override
    public ResponseEntity<?> copyNotes( Integer noteId) throws Exception {
       Boolean copyNote= notesService.copyNote(noteId);
       if(copyNote){
           return CommonUtil.createResponse(" copied",HttpStatus.OK);
       }
        return CommonUtil.createResponse("not copied",HttpStatus.OK);
    }
}



















