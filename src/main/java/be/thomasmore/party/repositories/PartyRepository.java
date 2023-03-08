package be.thomasmore.party.repositories;

import be.thomasmore.party.model.Artist;
import be.thomasmore.party.model.Party;
import be.thomasmore.party.model.Venue;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface PartyRepository extends CrudRepository<Party, Integer> {
    Optional<Party> findFirstByIdLessThanOrderByIdDesc(int id);
    Optional<Party> findFirstByIdGreaterThanOrderById(int id);
    Optional<Party> findFirstByOrderByIdDesc();
    Optional<Party> findFirstByOrderByIdAsc();
    List<Party> findAllBy();
    List<Party> findByVenue(Venue venue);
}
