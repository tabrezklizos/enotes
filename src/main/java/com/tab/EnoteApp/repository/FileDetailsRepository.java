package com.tab.EnoteApp.repository;

import com.tab.EnoteApp.entity.FileDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileDetailsRepository extends JpaRepository<FileDetails,Integer> {
}
