package be.thomasmore.party.controllers;

import be.thomasmore.party.model.Artist;
import be.thomasmore.party.model.Party;
import be.thomasmore.party.repositories.PartyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@Controller
public class PartyController {
    @Autowired
    private PartyRepository partyRepository;

    @GetMapping("/partylist")
    public String partyList(Model model) {
        List<Party> parties = partyRepository.findAllBy();
        model.addAttribute("parties", parties);
        model.addAttribute("nrParties", parties.size());
        return "partylist";
    }

    @GetMapping({"/partydetails", "/partydetails/{id}"})
    public String partyDetails(Model model, @PathVariable(required = false) Integer id) {
        if (id==null) return "partydetails";
        Optional<Party> optionalParty = partyRepository.findById(id);
        Optional<Party> optionalPrev = partyRepository.findFirstByIdLessThanOrderByIdDesc(id);
        Optional<Party> optionalNext = partyRepository.findFirstByIdGreaterThanOrderById(id);
        if (optionalParty.isPresent()) {
            model.addAttribute("party", optionalParty.get());
        }
        if (optionalPrev.isPresent()) {
            model.addAttribute("prev", optionalPrev.get().getId());
        } else {
            model.addAttribute("prev", partyRepository.findFirstByOrderByIdDesc().get().getId());
        }
        if (optionalNext.isPresent()) {
            model.addAttribute("next", optionalNext.get().getId());
        } else {
            model.addAttribute("next", partyRepository.findFirstByOrderByIdAsc().get().getId());
        }
        return "partydetails";
    }
}
