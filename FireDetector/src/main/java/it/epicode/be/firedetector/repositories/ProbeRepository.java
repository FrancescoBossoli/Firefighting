package it.epicode.be.firedetector.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import it.epicode.be.firedetector.models.Probe;

@Repository
public interface ProbeRepository extends PagingAndSortingRepository <Probe, Integer> {

	@Query(nativeQuery = true, value = "SELECT * FROM probes WHERE latitude_id = :x AND longitude_id = :y")
	Optional<Probe> findProbeByCoordinates(@Param("x") Integer x, @Param("y") Integer y);
	
	Boolean existsByName(String n);

}

