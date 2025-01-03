package ru.job4j.dreamjob.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;
import ru.job4j.dreamjob.dto.FileDto;
import ru.job4j.dreamjob.model.Candidate;
import ru.job4j.dreamjob.model.User;
import ru.job4j.dreamjob.service.CandidateService;

import net.jcip.annotations.ThreadSafe;
import ru.job4j.dreamjob.service.CityService;

import java.util.Optional;

@Controller
@RequestMapping("/candidates")
@ThreadSafe
public class CandidateController {

    private final CandidateService candidateService;

    private final CityService cityService;

    private final UserController userController;

    public CandidateController(CandidateService candidateService,
                               CityService cityService,
                               UserController userController) {
        this.candidateService = candidateService;
        this.cityService = cityService;
        this.userController = userController;
    }

    @GetMapping
    public String getAll(Model model, HttpSession session) {
        userController.addUserAsAttributeToModel(model, session);
        model.addAttribute("candidates", candidateService.findAll());
        return "candidates/list";
    }

    @GetMapping("/create")
    public String getCreationPage(Model model, HttpSession session) {
        userController.addUserAsAttributeToModel(model, session);
        model.addAttribute("cities", cityService.findAll());
        return "candidates/create";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute Candidate candidate, @RequestParam MultipartFile file, Model model) {
        try {
            candidateService.save(candidate, new FileDto(file.getOriginalFilename(), file.getBytes()));
            return "redirect:/candidates";
        } catch (Exception exception) {
            model.addAttribute("message", exception.getMessage());
            return "errors/404";
        }
    }

    @GetMapping("/{id}")
    public String getById(Model model, @PathVariable int id, HttpSession session) {
        Optional<Candidate> candidateOptional = candidateService.findById(id);
        if (candidateOptional.isEmpty()) {
            model.addAttribute("message",
                    "Анкета с указанным идентификатором не найдена");
            return "errors/404";
        }
        userController.addUserAsAttributeToModel(model, session);
        model.addAttribute("candidate", candidateOptional.get());
        model.addAttribute("cities", cityService.findAll());
        return "candidates/one";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute Candidate candidate, @RequestParam MultipartFile file, Model model) {
        try {
            boolean isUpdated = candidateService.update(candidate, new FileDto(file.getOriginalFilename(), file.getBytes()));
            if (!isUpdated) {
                model.addAttribute("message", "Вакансия с указанным идентификатором не найдена");
                return "errors/404";
            }
            return "redirect:/candidates";
        } catch (Exception exception) {
            model.addAttribute("message", exception.getMessage());
            return "errors/404";
        }
    }

    @GetMapping("/delete/{id}")
    public String delete(Model model, @PathVariable int id) {
        boolean isDeleted = candidateService.deleteById(id);
        if (!isDeleted) {
            model.addAttribute("message",
                    "Анкета с указанным идентификатором не найдена");
            return "errors/404";
        }
        return "redirect:/candidates";
    }
}
