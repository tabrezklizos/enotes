package com.tab.enote_app.repository;

import com.tab.enote_app.entity.FileDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileDetailsRepository extends JpaRepository<FileDetails,Integer> {
}
