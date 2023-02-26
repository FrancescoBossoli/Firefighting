package it.epicode.be.firedetector.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import it.epicode.be.firedetector.models.FireDetector;

@Repository
public interface FireDetectorRepository extends PagingAndSortingRepository<FireDetector, Integer> {

	Boolean existsByName(String n);

}
