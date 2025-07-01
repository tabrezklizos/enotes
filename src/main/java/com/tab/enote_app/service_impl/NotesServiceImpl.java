package com.tab.enote_app.service_impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tab.enote_app.dto.FavNoteDto;
import com.tab.enote_app.dto.NotesDto;
import com.tab.enote_app.dto.NotesResponse;
import com.tab.enote_app.entity.FavNote;
import com.tab.enote_app.entity.FileDetails;
import com.tab.enote_app.entity.Notes;
import com.tab.enote_app.exception.ResourceExistsException;
import com.tab.enote_app.exception.ResourceNotFoundException;
import com.tab.enote_app.repository.CategoryRepository;
import com.tab.enote_app.repository.FavNoteRepository;
import com.tab.enote_app.repository.FileDetailsRepository;
import com.tab.enote_app.repository.NotesRepository;
import com.tab.enote_app.service.NotesService;
import com.tab.enote_app.util.CommonUtil;
import com.tab.enote_app.util.Validation;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class NotesServiceImpl implements NotesService {

    private final  ModelMapper mapper;
    private final  NotesRepository notesRepository;
    private final CategoryRepository categoryRepository;
    private final Validation validation;
    private final FileDetailsRepository fileDetailsRepository;
    private final FavNoteRepository favNoteRepository;

    @Value("${file.upload.path}")
    private String uploadpath;

    @Override
    public Boolean saveNotes(String notes, MultipartFile file) throws Exception {

        ObjectMapper ob = new ObjectMapper();
        NotesDto notesDto = ob.readValue(notes, NotesDto.class);

        notesDto.setIsDeleted(false);
        notesDto.setDeletedOn(null);

        if(!ObjectUtils.isEmpty(notesDto.getId())){
            updateNotes(notesDto,file);
        }

         validation.notesValidation(notesDto);
        Optional<Notes> NotesExist=notesRepository.findByTitle(notesDto.getTitle());
        if(NotesExist.isPresent()){
            throw new ResourceExistsException("Notes exist with title "+notesDto.getTitle());
        }
        checkCategoryExist(notesDto.getCategory());
        Notes notesMap = mapper.map(notesDto, Notes.class);
        FileDetails fileDlts=saveFileDetails(file);

        if(!ObjectUtils.isEmpty(fileDlts)){
            notesMap.setFileDetails(fileDlts);
        }
        else{
            if(ObjectUtils.isEmpty(notesDto.getId())){
                notesMap.setFileDetails(null);
            }
        }

        Notes notesSaved = notesRepository.save(notesMap);
        if(!ObjectUtils.isEmpty(notesSaved)){
            return true;
        }

        return false;
    }

    private void updateNotes(NotesDto notesDto, MultipartFile file) throws ResourceNotFoundException {

        Notes existNote = notesRepository.findById(notesDto.getId())
                            .orElseThrow(() -> new ResourceNotFoundException("invalid notes Id"));
        if(ObjectUtils.isEmpty(file)){
            notesDto.setFileDetails(mapper.map(existNote.getFileDetails(), NotesDto.FilesDto.class));
        }


    }

    @Override
    public byte[] downloadFile(FileDetails fileDetails) throws Exception {
        FileInputStream fileInputStream = new FileInputStream(fileDetails.getPath());
        return StreamUtils.copyToByteArray(fileInputStream);
    }

    @Override
    public FileDetails getFileDetails(Integer id)  throws Exception  {
        FileDetails fileDetails = fileDetailsRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("file not found"));
        return fileDetails;
    }

    @Override
    public NotesResponse findAllNotesByUserId(Integer pageNo,Integer pageSize) {
        Integer userId=CommonUtil.getLogUser().getId();
        Pageable pageable  = PageRequest.of(pageNo, pageSize);
        Page<Notes> pageNotes = notesRepository.findByCreatedByAndIsDeletedFalse(userId,pageable);

        List<NotesDto>  notesDto =  pageNotes.get().map(n -> mapper.map(n, NotesDto.class)).toList();

        NotesResponse notesResponse = NotesResponse.builder()
                .notes(notesDto)
                .pageNo(pageNotes.getNumber())
                .pageSize(pageNotes.getSize())
                .totalPages(pageNotes.getTotalPages())
                .totalElements(Integer.valueOf((int)pageNotes.getTotalElements()))
                .isFirst(pageNotes.isFirst())
                .isLast(pageNotes.isLast())
                .build();

        return notesResponse;
    }

    @Override
    public NotesResponse searchNotesByUser(String keyword, Integer pageNo, Integer pageSize) {
        Integer userId=CommonUtil.getLogUser().getId();
        Pageable pageable  = PageRequest.of(pageNo, pageSize);
        Page<Notes> pageNotes = notesRepository.userSearchNotes(keyword,userId,pageable);

        List<NotesDto>  notesDto =  pageNotes.get().map(n -> mapper.map(n, NotesDto.class)).toList();

        NotesResponse notesResponse = NotesResponse.builder()
                .notes(notesDto)
                .pageNo(pageNotes.getNumber())
                .pageSize(pageNotes.getSize())
                .totalPages(pageNotes.getTotalPages())
                .totalElements(Integer.valueOf((int)pageNotes.getTotalElements()))
                .isFirst(pageNotes.isFirst())
                .isLast(pageNotes.isLast())
                .build();

        return notesResponse;
    }

    @Override
    public Boolean softDeleteNote(Integer id) throws Exception {
        Integer userId=CommonUtil.getLogUser().getId();
       Notes existNote = notesRepository.findByIdAndCreatedBy(id,userId);
               if(ObjectUtils.isEmpty(existNote)){
                   throw new ResourceNotFoundException("Notes not found");
               }

               existNote.setIsDeleted(true);
               existNote.setDeletedOn(LocalDateTime.now());

               notesRepository.save(existNote);

        return true;
    }

    @Override
    public Boolean restoreNote(Integer id) throws Exception {
        Integer userId=CommonUtil.getLogUser().getId();
        Notes existNote = notesRepository.findByIdAndCreatedBy(id,userId);
        if(ObjectUtils.isEmpty(existNote)){
            throw new ResourceNotFoundException("Notes not found");
        }

        existNote.setIsDeleted(false);
        existNote.setDeletedOn(null);

        notesRepository.save(existNote);

        return true;

    }

    @Override
    public List<NotesDto> findByCreatedByAndIsDeletedTrue() throws Exception {
        Integer userId=CommonUtil.getLogUser().getId();
        List<Notes> recycleNotes = notesRepository.findByCreatedByAndIsDeletedTrue(userId);
        if (CollectionUtils.isEmpty(recycleNotes)){
            throw new ResourceNotFoundException("recycle bin is empty");
        }
        List<NotesDto> notes = recycleNotes.stream().map(n -> mapper.map(n, NotesDto.class)).toList();

        return notes;


    }

    @Override
    public void emptyRecycleBin() throws Exception {
        Integer userId=CommonUtil.getLogUser().getId();
        List<Notes> recycleNotes = notesRepository.findByCreatedByAndIsDeletedTrue(userId);
        if (CollectionUtils.isEmpty(recycleNotes)){
            throw new ResourceNotFoundException("recycle bin is empty");
        }
        notesRepository.deleteAll(recycleNotes);

    }

    @Override
    public void hardDeleteNote(Integer id) throws Exception {
      Notes recycleNote = notesRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("note does not exist"));
        if(recycleNote.getIsDeleted()){
            notesRepository.delete(recycleNote);
        }
        else{
            throw new IllegalArgumentException("sorry you cant delete directly");
        }
    }

    @Override
    public void saveFavNote(Integer noteId) throws Exception {
        Integer userId=1;
        Notes note = notesRepository.findById(noteId).orElseThrow(() -> new ResourceNotFoundException("Invalid Note id"));
        FavNote favNote = FavNote.builder()
                .note(note)
                .userId(userId)
                .build();
        favNoteRepository.save(favNote);
    }

    @Override
    public void unFavNote(Integer favNoteId) throws Exception {
        FavNote favNoteExist = favNoteRepository.findById(favNoteId).orElseThrow(() -> new ResourceNotFoundException("favourite note does not exist"));
        favNoteRepository.delete(favNoteExist);
    }

    @Override
    public List<FavNoteDto> getFavNotes() throws Exception {
        Integer userId= CommonUtil.getLogUser().getId();
        List<FavNote> allFavNoteByUserId = favNoteRepository.findAllFavNoteByUserId(userId);
        if(CollectionUtils.isEmpty(allFavNoteByUserId)){
            throw new ResourceNotFoundException("favourite notes are empty");
        }
        List<FavNoteDto> list = allFavNoteByUserId.stream().map(n -> mapper.map(n, FavNoteDto.class)).toList();

        return list;
    }

    @Override
    public Boolean copyNote(Integer noteId) throws Exception {
        Notes note = notesRepository.findById(noteId).orElseThrow(() -> new ResourceNotFoundException("note does not exist"));

         Notes copyNote = Notes.builder()
                                 .title(note.getTitle())
                                 .isDeleted(false)
                                 .category(note.getCategory())
                                 .description(note.getDescription())
                                 .fileDetails(null)
                                .build();

         notesRepository.save(copyNote);

         if(!ObjectUtils.isEmpty(copyNote)){
             return true;
         }
         return false;
    }

    private FileDetails saveFileDetails(MultipartFile file) throws IOException {

        if(!ObjectUtils.isEmpty(file) && !file.isEmpty()){

            List<String>  allowedDocs = Arrays.asList("jpg", "png", "pdf","txt","jpeg");
            String originalFileName = file.getOriginalFilename();
            String fileExtension = FilenameUtils.getExtension(originalFileName);

            if(!allowedDocs.contains(fileExtension)){
                throw new  IllegalArgumentException("Only pdf , jpg and png accepted");
            }


            FileDetails fileDetails =new FileDetails();

            fileDetails.setOriginalFileName(originalFileName);
            fileDetails.setDisplayFileName(getfileDisplayName(originalFileName));

            String rndString = UUID.randomUUID().toString();
            String uploadFileName = rndString+"."+fileExtension;
            fileDetails.setUploadFileName(uploadFileName);

            fileDetails.setFileSize(file.getSize());

            File saveFile = new File(uploadpath);

            if(!saveFile.exists()){
                saveFile.mkdir();
            }
            String storePath=uploadpath.concat(uploadFileName);
            fileDetails.setPath(storePath);

            //upload file
           long copy = Files.copy(file.getInputStream(), Paths.get(storePath));
           if(copy!=0){
                FileDetails saveFileDetails = fileDetailsRepository.save(fileDetails);
                return saveFileDetails;
            }

        }
        return null;
    }

    private String getfileDisplayName(String originalFileName) {

        String fileExtension = FilenameUtils.getExtension(originalFileName);
        String  fileName = FilenameUtils.removeExtension(originalFileName);
        if(fileName.length()>8){
            fileName=fileName.substring(0,7);
        }
         fileName=fileName+"."+fileExtension;
        return fileName;
    }

/*
    private FileDetails saveFileDetails(MultipartFile file) {



    }
*/

    private void checkCategoryExist(NotesDto.CategoryDto category) throws Exception {

        categoryRepository.findById(category.getId()).orElseThrow(()-> new ResourceNotFoundException("Invalid Category_id"));
    }

    @Override
    public List<NotesDto> findAllNotes() {

        List<Notes> allNotes =notesRepository.findAll();

        List<NotesDto> notesList =
                allNotes.stream().map(note->mapper.map(note,NotesDto.class)).toList();

        return  notesList;
    }
}
