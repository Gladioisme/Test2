package be.thomasmore.party.controllers;

import be.thomasmore.party.model.Venue;
import be.thomasmore.party.repositories.VenueRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Controller
public class VenueController {
    private Logger logger = LoggerFactory.getLogger(VenueController.class);
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
        List<Venue> allVenues = venueRepository.findAllBy();
        model.addAttribute("venues", allVenues);
        model.addAttribute("nrVenues", allVenues.size());
        return "venuelist";
    }

    @GetMapping("/venuelist/filter")
    public String venueListWithFilter(Model model,
                                      @RequestParam(required = false) Integer minimumCapacity,
                                      @RequestParam(required = false) Integer maximumCapacity,
                                      @RequestParam(required = false) Double distance,
                                      @RequestParam(required = false) String foodProvided,
                                      @RequestParam(required = false) String indoor,
                                      @RequestParam(required = false) String outdoor) {
        List<Venue> venues = venueRepository.findByCapacityDistanceFoodIndoorOutdoor(
                minimumCapacity, maximumCapacity, distance,
                ((foodProvided==null || foodProvided.equals("all")) ? null : (foodProvided.equals("yes") ? true : false)),
                ((indoor==null || indoor.equals("all")) ? null : (indoor.equals("yes") ? true : false)),
                ((outdoor==null || outdoor.equals("all")) ? null : (outdoor.equals("yes") ? true : false)));
        model.addAttribute("maxCapacity", maximumCapacity);
        model.addAttribute("minCapacity", minimumCapacity);
        model.addAttribute("distance", distance);
        model.addAttribute("foodProvided", foodProvided);
        model.addAttribute("indoor", indoor);
        model.addAttribute("outdoor", outdoor);
        model.addAttribute("venues", venues);
        model.addAttribute("nrVenues", venues.size());
        model.addAttribute("showFilter", true);
        return "venuelist";
    }
}
