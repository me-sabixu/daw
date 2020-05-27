package eu.badeasabina.daw.controller;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import eu.badeasabina.daw.model.Faculty;
import eu.badeasabina.daw.repository.FacultyRepository;
import eu.badeasabina.daw.repository.StudentRepository;



@Controller
@RequestMapping("/faculties/")
public class FacultyController {

    private final FacultyRepository facultyRepository;
    private final StudentRepository studentRepository; 

    @Autowired
    public FacultyController(FacultyRepository facultyRepository, StudentRepository studentRepository) {
        this.facultyRepository = facultyRepository;
        this.studentRepository = studentRepository;
    }

    @GetMapping("signup")
    public String showSignUpForm(Faculty faculty) {
        return "add-faculty";
    }

    @GetMapping("list")
    public String showUpdateForm(Model model) {
        model.addAttribute("faculties", facultyRepository.findAll());
        model.addAttribute("students", studentRepository.findAll());
        return "index";
    }

    @PostMapping("add")
    public String addFaculty( Faculty faculty, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "add-faculty";
        }

        facultyRepository.save(faculty);
        return "redirect:list";
    }

    @GetMapping("edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        Faculty faculty = facultyRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Invalid faculty Id:" + id));
        model.addAttribute("faculty", faculty);
        return "update-faculty";
    }

    @PostMapping("update/{id}")
    public String updateStudent(@PathVariable("id") long id,  Faculty faculty, BindingResult result,
        Model model) {
        if (result.hasErrors()) {
            faculty.setId(id);
            return "update-faculty";
        }

        facultyRepository.save(faculty);
        model.addAttribute("faculty", facultyRepository.findAll());
        model.addAttribute("students", studentRepository.findAll());
        return "index";
    }

    @GetMapping("delete/{id}")
    public String deleteFaculty(@PathVariable("id") long id, Model model) {
    	Faculty faculty = facultyRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Invalid faculty Id:" + id));
        facultyRepository.delete(faculty);
        model.addAttribute("faculty", facultyRepository.findAll());
        model.addAttribute("students", studentRepository.findAll());
        return "index";
    }
    
    
}