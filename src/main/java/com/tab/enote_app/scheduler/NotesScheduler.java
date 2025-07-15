package com.tab.enote_app.scheduler;

import com.tab.enote_app.entity.Notes;
import com.tab.enote_app.repository.NotesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class NotesScheduler {

    private final NotesRepository notesRepository;

    //000**? every mid-night it will check
    @Scheduled(cron = "0 0 0 * * ?")
    void deleteScheduledNotes(){
        LocalDateTime cuttOffTime = LocalDateTime.now().minusDays(7);
        List<Notes> notes =notesRepository.findAllByIsDeletedAndDeletedOnBefore(true,cuttOffTime);
        notesRepository.deleteAll(notes);

    }

}
