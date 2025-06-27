package com.tab.EnoteApp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static com.tab.EnoteApp.util.Constants.*;

@Tag(name="notes")
@RequestMapping("/api/v1/notes")
public interface NotesController {

    @Operation(summary = "save notes",tags={"notes", "user"},description = "User can save notes")
    @PostMapping("/save")
    @PreAuthorize( ROLE_USER)
    public ResponseEntity<?> saveNotes(@RequestParam String notes,
                                       @RequestParam(required = false) MultipartFile file) throws Exception;

    @Operation(summary = "get all notes",tags={"notes"},description = "Admin can get all notes")
    @GetMapping("/")
    @PreAuthorize(ROLE_ADMIN)
    public ResponseEntity<?> getAllNotes();

    @Operation(summary = "get all notes for user",tags={"notes", "user"},description = "get all notes for user")
    @GetMapping("/user-notes")
    @PreAuthorize( ROLE_USER)
    public ResponseEntity<?> getAllNotesByUserId(
            @RequestParam(name="pageNo",defaultValue =DEFAULT_PAGE_NO) Integer pageNo,
            @RequestParam(name="pageSize",defaultValue = DEFAULT_PAGE_SIZE) Integer pageSize
    );

    @Operation(summary = "search notes",tags={"notes", "user"},description = "user can search notes")
    @GetMapping("/search-notes")
    @PreAuthorize( ROLE_USER)
    public ResponseEntity<?> getSearchNotesByUser(
            @RequestParam(name="keyword",defaultValue ="") String keyword,
            @RequestParam(name="pageNo",defaultValue =DEFAULT_PAGE_NO) Integer pageNo,
            @RequestParam(name="pageSize",defaultValue =DEFAULT_PAGE_SIZE) Integer pageSize
    );

    @Operation(summary = "download notes",tags={"notes", "user"},description = "Admin,User can download notes")
    @GetMapping("/downloadFile/{id}")
    @PreAuthorize(ROLE_ADMIN_USER)
    public ResponseEntity<?> downloadFile(@PathVariable Integer id) throws Exception;

    @Operation(summary = "delete notes",tags={"notes", "user"},description = "user can delete notes")
    @GetMapping("/delete-note/{id}")
    @PreAuthorize( ROLE_USER)
    public ResponseEntity<?> deleteNoteByUser(@PathVariable Integer id) throws Exception;

    @Operation(summary = "restore notes",tags={"notes", "user"},description = "user can restore notes from bin")
    @GetMapping("/restore-note/{id}")
    @PreAuthorize( ROLE_USER)
    public ResponseEntity<?> restoreNoteByUser(@PathVariable Integer id) throws Exception;

    @Operation(summary = "recycle bin notes",tags={"notes", "user"},description = "user can get recycle bin notes")
    @GetMapping("/recycle-bin")
    @PreAuthorize( ROLE_USER)
    public ResponseEntity<?> recycleBinNotesByUser() throws Exception;

    @Operation(summary = "empty recycle bin notes",tags={"notes", "user"},description = "user can empty recycle bin notes")
    @DeleteMapping("/empty-recycle-bin")
    @PreAuthorize( ROLE_USER)
    public ResponseEntity<?> emptyRecycleBin( ) throws Exception;

    @Operation(summary = "hard delete notes",tags={"notes", "user"},description = "user can delete notes in single short")
    @DeleteMapping("/hard-delete-note/{id}")
    @PreAuthorize( ROLE_USER)
    public ResponseEntity<?> hardDeleteNote(@PathVariable Integer id) throws Exception;

    @Operation(summary = "save notes as favourite",tags={"notes", "user"},description = "user can save its own facourite notes ")
    @PostMapping("/fav-note/{noteId}")
    @PreAuthorize( ROLE_USER)
    public ResponseEntity<?> saveFavNote(@PathVariable Integer noteId) throws Exception;

    @Operation(summary = "remove notes from favourite list",tags={"notes", "user"},description = "user can remove notes from favourite list")
    @DeleteMapping("/unFav-note/{favNoteId}")
    @PreAuthorize( ROLE_USER)
    public ResponseEntity<?> unFavNote(@PathVariable Integer favNoteId) throws Exception;

    @Operation(summary = "get favourite notes",tags={"notes", "user"},description = "user can get favourite notes")
    @GetMapping("/fav-notes")
    @PreAuthorize( ROLE_USER)
    public ResponseEntity<?> getFavNotes() throws Exception;

    @Operation(summary = "copy notes",tags={"notes", "user"},description = "user can copy notes")
    @GetMapping("/copy-notes/{noteId}")
    @PreAuthorize( ROLE_USER)
    public ResponseEntity<?> copyNotes(@PathVariable Integer noteId) throws Exception;


}
