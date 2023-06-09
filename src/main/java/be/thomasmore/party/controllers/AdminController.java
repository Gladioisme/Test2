package be.thomasmore.party.controllers;

import be.thomasmore.party.model.Party;
import be.thomasmore.party.repositories.PartyRepository;
import be.thomasmore.party.repositories.VenueRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private Logger logger = LoggerFactory.getLogger(AdminController.class);
    @Autowired
    private PartyRepository partyRepository;
    @Autowired
    private VenueRepository venueRepository;

    @ModelAttribute("party")
    public Party findParty(@PathVariable(required = false) Integer id) {
        logger.info("findParty "+id);
        if (id!=null) {
            Optional<Party> optionalParty = partyRepository.findById(id);
            if (optionalParty.isPresent()) return optionalParty.get();
        }
        return new Party();
    }

    @GetMapping("/partyedit/{id}")
    public String partyEdit(Model model, @PathVariable int id) {
        logger.info("partyedit : "+id);
        model.addAttribute("venues", venueRepository.findAllBy());
        return "admin/partyedit";
    }

    @PostMapping("/partyedit/{id}")
    public String partyEditPost(Model model, @PathVariable int id, Party party) {
        logger.info("partyeditpost : "+id);
        partyRepository.save(party);
        return "redirect:/partydetails/"+id;
    }

    @GetMapping("/partynew")
    public String partyNew(Model model) {
        model.addAttribute("venues", venueRepository.findAllBy());
        return "admin/partynew";
    }

    @PostMapping("/partynew")
    public String partyNewPost(Model model, Party party) {
        logger.info("partynewpost");
        partyRepository.save(party);
        return "redirect:/partydetails/"+party.getId();
    }

    @PostMapping("/partydelete/{id}")
    public String partyDeletePost(Model model, @PathVariable int id, Party party) {
        logger.info("partydeletepost : "+id);
        partyRepository.delete(party);
        return "redirect:/partylist";
    }
}
