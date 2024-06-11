package com.iiht.training.onlinevoting.repository;

import com.iiht.training.onlinevoting.entity.Poll;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PollRepository extends JpaRepository<Poll, Long> {

    @Query("SELECT p FROM Poll p")
    Page<Poll> findAllPoll(Pageable pageable);

    @Query(value = "Select p from Poll p where lower(pollName) like %:keyword% ")
    Page<Poll> findByPollName(@Param("keyword") String keyword,
                                        Pageable pageable);


}
