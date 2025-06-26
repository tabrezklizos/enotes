package com.tab.EnoteApp.scheduler;

import com.tab.EnoteApp.entity.Notes;
import com.tab.EnoteApp.repository.NotesRepository;
import com.tab.EnoteApp.service.NotesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class NotesScheduler {

    @Autowired
    NotesRepository notesRepository;
    @Autowired
    NotesService notesService;

    //000**? every mid-night it will check
    @Scheduled(cron = "0 0 0 * * ?")
    void deleteScheduledNotes(){
        LocalDateTime cuttOffTime = LocalDateTime.now().minusDays(7);
        List<Notes> notes =notesRepository.findAllByIsDeletedAndDeletedOnBefore(true,cuttOffTime);
        notesRepository.deleteAll(notes);

    }

}
