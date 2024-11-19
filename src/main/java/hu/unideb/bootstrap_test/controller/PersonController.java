package hu.unideb.bootstrap_test.controller;

import hu.unideb.bootstrap_test.model.Person;
import hu.unideb.bootstrap_test.model.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
public class PersonController {

    @Autowired
    PersonRepository personRepository;
    private String redirectText;
    
    @GetMapping("/persons")
    public String listAllPersons(Model model){
        List<Person> persons = personRepository.findAll();
        model.addAttribute("personsList", persons);
        return "persons";
    }

    @GetMapping("/persons/new")
    public String newPerson(Model model){
        model.addAttribute("person", new Person());
        model.addAttribute("pageTitle", "Add new person");
        redirectText="Add successfully";
        return "newPersonFrom";
    }

    @PostMapping("/persons/save")
    public String savePerson(Person person, RedirectAttributes rd){
        personRepository.save(person);
        rd.addFlashAttribute("message", redirectText);
        return "redirect:/persons";
    }

    @GetMapping("/persons/delete/{id}")
    public String deletePerson(@PathVariable Integer id, RedirectAttributes rd) {
        Optional<Person> personToDelete = personRepository.findById(id);
        if (personToDelete.isPresent()) {
            personRepository.delete(personToDelete.get());
            rd.addFlashAttribute("message", "Delete operation successful");
        }
        return "redirect:/persons";
    }

    @GetMapping("/persons/edit/{id}")
    public String editPerson(@PathVariable Integer id, Model model){
        Optional<Person> personToEdit = personRepository.findById(id);
        if (personToEdit.isPresent()) {
            model.addAttribute("person", personToEdit.get());
            model.addAttribute("pageTitle", "Edit person with id: " + id);
            redirectText="Changed successfully";
            return "newPersonFrom";
        }
        return "redirect:/persons";
    }


}
