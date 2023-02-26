package it.epicode.be.forestrycorps.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import it.epicode.be.forestrycorps.models.Emergency;

@Repository
public interface EmergencyRepository extends PagingAndSortingRepository<Emergency, Integer> {
	

}
