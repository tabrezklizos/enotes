package com.tab.EnoteApp.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.tab.EnoteApp.entity.Notes;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface NotesRepository extends JpaRepository<Notes,Integer> {

    Optional<Notes> findByTitle(String title);

  //  @Query("select n from Notes as n where n.createdBy=?1 and n.isDeleted=false")
    Page <Notes> findByCreatedByAndIsDeletedFalse(Integer userId, Pageable pageable);

    @Query("select n from Notes n where (lower(n.title) like lower(concat('%',:keyword,'%')) "
                                 + "or lower(n.description) like lower(concat('%',:keyword,'%')) "
                                 + "or lower(n.category.name) like lower(concat('%',:keyword,'%'))) "
                                 + "and n.isDeleted=false "
                                 + "and n.createdBy=:userId ")
    Page <Notes> userSearchNotes(@Param("keyword") String Keyword,@Param("userId") Integer userId, Pageable pageable);

    @Query(" select n from Notes  as n where n.id=?1 and n.createdBy=?2  ")
    Notes findByIdAndCreatedBy(Integer id, Integer createdBy);

    @Query("select n from Notes as n where n.createdBy=?1 and n.isDeleted=true")
    List<Notes> findByCreatedByAndIsDeletedTrue(Integer userId);

    List<Notes> findAllByIsDeletedAndDeletedOnBefore(boolean b, LocalDateTime cuttOffTime);

    Notes findByIdAndCreatedByAndIsDeleted(Integer id, Integer userId, boolean b);
}
