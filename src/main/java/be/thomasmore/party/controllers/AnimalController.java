package be.thomasmore.party.controllers;

import be.thomasmore.party.model.Animal;
import be.thomasmore.party.model.Artist;
import be.thomasmore.party.repositories.AnimalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@Controller
public class AnimalController {
    @Autowired
    private AnimalRepository animalRepository;

    @GetMapping({"/animaldetails", "/animaldetails/{id}"})
    public String animalDetails(Model model, @PathVariable(required = false) Integer id) {
        if (id==null) return "animaldetails";
        Optional<Animal> optionalAnimal = animalRepository.findById(id);
        Optional<Animal> optionalPrev = animalRepository.findFirstByIdLessThanOrderByIdDesc(id);
        Optional<Animal> optionalNext = animalRepository.findFirstByIdGreaterThanOrderById(id);
        if (optionalAnimal.isPresent()) {
            model.addAttribute("animal", optionalAnimal.get());
        }
        if (optionalPrev.isPresent()) {
            model.addAttribute("prev", optionalPrev.get().getId());
        } else {
            model.addAttribute("prev", animalRepository.findFirstByOrderByIdDesc().get().getId());
        }
        if (optionalNext.isPresent()) {
            model.addAttribute("next", optionalNext.get().getId());
        } else {
            model.addAttribute("next", animalRepository.findFirstByOrderByIdAsc().get().getId());
        }
        return "animaldetails";
    }
}
