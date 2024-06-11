package com.iiht.training.onlinevoting.service;

import com.iiht.training.onlinevoting.entity.Poll;
import com.iiht.training.onlinevoting.repository.PollRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import jakarta.transaction.Transactional;

@Component
public class PollService {
    @Autowired
    private PollRepository pollRepository;

    @Autowired
    private OptionService optionService;


    @Transactional
    public boolean save(Poll poll) {
        this.pollRepository.save(poll);
        return true;
    }

    public Poll get(int id) {
        return (Poll) this.pollRepository.findById((long) id).get();
    }

    public Page<Poll> getAll(Pageable pageable) {
        return this.pollRepository.findAllPoll(pageable);
    }

    @Transactional
    public boolean delete(int id) {
        this.optionService.deleteOptionByPollId(id);
        this.pollRepository.deleteById((long) id);
        return true;
    }

    @Transactional
    public boolean update(Poll poll) {
        this.pollRepository.save(poll);
        return true;
    }

    public Page<Poll> searchPolls(String theSearchName, Pageable pageable) {
        if (theSearchName != null && theSearchName.trim().length() > 0) {
            return pollRepository.findByPollName(theSearchName, pageable);
        } else {
            return pollRepository.findAllPoll(pageable);
        }
    }
}
