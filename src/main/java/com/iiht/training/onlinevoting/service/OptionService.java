package com.iiht.training.onlinevoting.service;

import com.iiht.training.onlinevoting.entity.PollOption;
import com.iiht.training.onlinevoting.repository.PollOptionRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OptionService {
    @Autowired
    private PollOptionRepository pollOptionRepository;

    @Transactional
    public boolean save(PollOption pollOption) {
        this.pollOptionRepository.save(pollOption);
        return true;
    }

    public List<PollOption> getAll() {
        return this.pollOptionRepository.findAll();
    }

    public Object get(int id) {
        return this.pollOptionRepository.findById((long) id).get();
    }

    public List<PollOption> getOptionByPollId(int pollId) {
        return pollOptionRepository.findByPollId(pollId);
    }

    public boolean delete(int id) {
        this.pollOptionRepository.deleteById((long) id);
        return true;
    }

    public boolean deleteOptionByPollId(int pollId) {
        pollOptionRepository.deleteByPollId(pollId);
        return true;
    }


}
