package it.epicode.be.forestrycorps.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import it.epicode.be.forestrycorps.models.Location;

@Repository
public interface LocationRepository extends PagingAndSortingRepository <Location, Integer> {

	@Query(nativeQuery = true, value = "SELECT * FROM locations WHERE latitude_id = :x AND longitude_id = :y")
	Optional<Location> findLocationByCoordinates(@Param("x") Integer x, @Param("y") Integer y);
	
	Boolean existsByName(String n);

}

