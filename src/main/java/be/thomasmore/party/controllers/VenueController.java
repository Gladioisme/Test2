package be.thomasmore.party.controllers;

import be.thomasmore.party.model.Venue;
import be.thomasmore.party.repositories.VenueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@Controller
public class VenueController {
    @Autowired
    private VenueRepository venueRepository;

    @GetMapping({"/venuedetails/{id}", "/venuedetails"})
    public String venueDetails(Model model, @PathVariable(required = false) Integer id) {
        if (id==null) return "venuedetails";
        Optional<Venue> optionalVenue = venueRepository.findById(id);
        Optional<Venue> optionalPrev = venueRepository.findFirstByIdLessThanOrderByIdDesc(id);
        Optional<Venue> optionalNext = venueRepository.findFirstByIdGreaterThanOrderById(id);
        if (optionalVenue.isPresent()) {
            model.addAttribute("venue", optionalVenue.get());
        }
        if (optionalPrev.isPresent()) {
            model.addAttribute("prev", optionalPrev.get().getId());
        } else {
            model.addAttribute("prev", venueRepository.findFirstByOrderByIdDesc().get().getId());
        }
        if (optionalNext.isPresent()) {
            model.addAttribute("next", optionalNext.get().getId());
        } else {
            model.addAttribute("next", venueRepository.findFirstByOrderByIdAsc().get().getId());
        }
        return "venuedetails";
    }

    @GetMapping({"/venuelist", "/venuelist/{something}"})
    public String venueList(Model model) {
        Iterable<Venue> allVenues = venueRepository.findAll();
        model.addAttribute("venues", allVenues);
        return "venuelist";
    }
}
