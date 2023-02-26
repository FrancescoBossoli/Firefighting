package it.epicode.be.forestrycorps.services;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import it.epicode.be.forestrycorps.models.Emergency;
import it.epicode.be.forestrycorps.repositories.EmergencyRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class EmergencyService {

	@Autowired
	private EmergencyRepository eR;
	
	public void save(Emergency e) {
		eR.save(e);
	}
	
	public Optional<Emergency> getEmergencyById(int id) {
		return eR.findById(id);
	}
	
	public List<Emergency> getAllEmergencies(){
		return eR.findAll(PageRequest.of(0 , 2000)).getContent();
	}
	
	public Page<Emergency> getAllEmergencies(Pageable p) {
		return eR.findAll(p);
	}
	
	public void deleteEmergencyById(int id) {
		eR.deleteById(id);
	}
	
	public void printList(List<Emergency> list) {
		for (Emergency l : list)
			log.info(l.toString());
	}
}
