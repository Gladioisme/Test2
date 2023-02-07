package be.thomasmore.party.repositories;

import be.thomasmore.party.model.Venue;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

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

    @Query("SELECT v FROM Venue v WHERE (:min IS NULL OR v.capacity >= :min) AND (:max IS NULL OR v.capacity <= :max)")
    List<Venue> findByCapacity(@Param("min") Integer min, @Param("max") Integer max);

    @Query("SELECT v FROM Venue v " +
            "WHERE (:min IS NULL OR v.capacity >= :min) " +
            "AND (:max IS NULL OR v.capacity <= :max) " +
            "AND (:distance IS NULL OR v.distanceFromPublicTransportInKm <= :distance) " +
            "AND (:food IS NULL OR v.foodProvided = :food) " +
            "AND (:indoor IS NULL OR v.indoor = :indoor) " +
            "AND (:outdoor IS NULL OR v.outdoor = :outdoor) ")
    List<Venue> findByCapacityDistanceFoodIndoorOutdoor(
            @Param("min") Integer min, @Param("max") Integer max,
            @Param("distance") Double distance,
            @Param("food") Boolean food,
            @Param("indoor") Boolean indoor,
            @Param("outdoor") Boolean outdoor);
}