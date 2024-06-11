package com.iiht.training.onlinevoting.controller;

import com.iiht.training.onlinevoting.entity.Poll;
import com.iiht.training.onlinevoting.entity.PollOption;
import com.iiht.training.onlinevoting.service.OptionService;
import com.iiht.training.onlinevoting.service.PollService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/pollOption")
public class OptionController {

    @Autowired
    OptionService optionService;
    @Autowired
    PollService pollService;

    @RequestMapping("/add/{id}")
    public String addOptionPage(@PathVariable("id") int id, Model model) {
        Poll poll = pollService.get(id);
        List<PollOption> pollOptions = optionService.getOptionByPollId(id);
        model.addAttribute("poll", poll);
        model.addAttribute("options", pollOptions);
        model.addAttribute("option", new PollOption());
        return "addOption";
    }

    @RequestMapping("/handleForm")
    public String addOptionHandleForm(@RequestParam("pollId") int id, @Valid @ModelAttribute PollOption pollOption, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "addOption";
        }
        pollOption.setPollId(id);
        optionService.save(pollOption);
        return "redirect:add/" + id;
    }

    @RequestMapping("/deletePollOption/{pollId}/{optionId}")
    public String deletePoll(@PathVariable("pollId") int pollId,
                             @PathVariable("optionId") int optionId) {
        optionService.delete(optionId);
        return "redirect:/pollOption/add/" + pollId;
    }
}
