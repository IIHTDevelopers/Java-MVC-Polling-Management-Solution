package com.iiht.training.onlinevoting.controller;

import com.iiht.training.onlinevoting.entity.Poll;
import com.iiht.training.onlinevoting.service.PollService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = {"/poll", "/"})
public class PollController {

    @Autowired
    private PollService pollService;

    @RequestMapping("/add")
    public String addPollsPage(Model model) {
        Poll poll = new Poll();
        model.addAttribute("poll", poll);
        return "addPolls";
    }

    @GetMapping("/update")
    public String showFormForUpdate(@RequestParam("pollId") int pollId, Model model) {
        Poll poll = pollService.get(pollId);
        model.addAttribute("poll", poll);
        return "updatePolls";
    }

    @RequestMapping(value = {"/", "/list"})
    public String home(@PageableDefault(size = 5) Pageable pageable, Model model) {
        Page<Poll> polls = pollService.getAll(pageable);
        model.addAttribute("polls", polls.getContent());
        model.addAttribute("theSearchName", "");
        model.addAttribute("totalPage", polls.getTotalPages());
        model.addAttribute("page", pageable.getPageNumber());
        model.addAttribute("sortBy", pageable.getSort().get().count() != 0 ?
                pageable.getSort().get().findFirst().get().getProperty() + "," + pageable.getSort().get().findFirst().get().getDirection() : "");
        return "list-polls";
    }

    @RequestMapping("/handleForm")
    public String addPollHandleForm(@Valid @ModelAttribute Poll poll, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            if (poll.getPollId() != null) {
                return "updatePolls";
            }
            return "addPolls";
        }
        pollService.save(poll);
        return "redirect:/poll/list";
    }

    @RequestMapping("/deletePoll/{id}")
    public String deletePoll(@PathVariable("id") int id, HttpServletRequest request) {
        pollService.delete(id);
        return "redirect:/poll/list";
    }

    @RequestMapping("/search")
    public String searchPolls(@RequestParam(value = "theSearchName", required = false) String theSearchName,
                              @PageableDefault(size = 5) Pageable pageable,
                              Model theModel) {

        Page<Poll> thePolls = pollService.searchPolls(theSearchName, pageable);
        theModel.addAttribute("polls", thePolls.getContent());
        theModel.addAttribute("theSearchName", theSearchName != null ? theSearchName : "");
        theModel.addAttribute("totalPage", thePolls.getTotalPages());
        theModel.addAttribute("page", pageable.getPageNumber());
        theModel.addAttribute("sortBy", pageable.getSort().get().count() != 0 ?
                pageable.getSort().get().findFirst().get().getProperty() + "," + pageable.getSort().get().findFirst().get().getDirection() : "");

        return "list-polls";
    }

}
