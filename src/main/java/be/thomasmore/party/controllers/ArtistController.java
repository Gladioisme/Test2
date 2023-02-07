package be.thomasmore.party.controllers;

import be.thomasmore.party.model.Artist;
import be.thomasmore.party.repositories.ArtistRepository;
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
public class ArtistController {
    private Logger logger = LoggerFactory.getLogger(ArtistController.class);
    @Autowired
    private ArtistRepository artistRepository;

    @GetMapping("/artistlist")
    public String artistList(Model model) {
        List<Artist> artists = artistRepository.findAllBy();
        model.addAttribute("artists", artists);
        model.addAttribute("nrArtists", artists.size());
        return "artistlist";
    }


    @GetMapping("/artistlist/filter")
    public String artistListWithFilter(Model model, @RequestParam(required = false) String keyword) {
        logger.info("artistListWithFilter -- keyword=" + keyword);
        model.addAttribute("keyword", keyword);
        List<Artist> artists = artistRepository.findByKeyword(keyword);
        model.addAttribute("artists", artists);
        model.addAttribute("nrArtists", artists.size());
        model.addAttribute("showFilter", true);
        return "artistlist";
    }

    @GetMapping({"/artistdetails", "/artistdetails/{id}"})
    public String artistDetails(Model model, @PathVariable(required = false) Integer id) {
        if (id==null) return "artistdetails";
        Optional<Artist> optionalArtist = artistRepository.findById(id);
        Optional<Artist> optionalPrev = artistRepository.findFirstByIdLessThanOrderByIdDesc(id);
        Optional<Artist> optionalNext = artistRepository.findFirstByIdGreaterThanOrderById(id);
        if (optionalArtist.isPresent()) {
            model.addAttribute("artist", optionalArtist.get());
        }
        if (optionalPrev.isPresent()) {
            model.addAttribute("prev", optionalPrev.get().getId());
        } else {
            model.addAttribute("prev", artistRepository.findFirstByOrderByIdDesc().get().getId());
        }
        if (optionalNext.isPresent()) {
            model.addAttribute("next", optionalNext.get().getId());
        } else {
            model.addAttribute("next", artistRepository.findFirstByOrderByIdAsc().get().getId());
        }
        return "artistdetails";
    }
}