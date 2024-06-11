package com.iiht.training.onlinevoting.repository;

import com.iiht.training.onlinevoting.entity.PollOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface PollOptionRepository extends JpaRepository<PollOption, Long> {


    List<PollOption> findByPollId(int pollId);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM poll_option where poll_id = :pollId", nativeQuery = true)
    void deleteByPollId(@Param("pollId") int pollId);
}
