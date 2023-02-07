package be.thomasmore.party.repositories;

import be.thomasmore.party.model.Venue;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface VenueRepository extends CrudRepository<Venue, Integer> {
    List<Venue> findAllBy();
    List<Venue> findByOutdoor(boolean isOutdoor);
    List<Venue> findByIndoor(boolean isIndoor);
    List<Venue> findByCapacityLessThanEqual(int capacity);
    List<Venue> findByCapacityIsBetween(int min, int max);
    List<Venue> findByCapacityIsGreaterThan(int capacity);
    Optional<Venue> findFirstByIdLessThanOrderByIdDesc(int id);
    Optional<Venue> findFirstByIdGreaterThanOrderById(int id);
    Optional<Venue> findFirstByOrderByIdDesc();
    Optional<Venue> findFirstByOrderByIdAsc();
}