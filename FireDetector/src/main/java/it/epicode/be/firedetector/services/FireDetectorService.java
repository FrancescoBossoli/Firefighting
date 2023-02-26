package it.epicode.be.firedetector.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import it.epicode.be.firedetector.models.FireDetector;
import it.epicode.be.firedetector.repositories.FireDetectorRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class FireDetectorService {

	@Autowired
	private FireDetectorRepository fR;

	public void save(FireDetector f) {
		if (!fR.existsByName(f.getName())) {
			fR.save(f);
			log.info("The Fire Detector has been saved in the Database.");
		} else
			log.info("This Fire Detector is already present in the Database.");
	}

	public Optional<FireDetector> getFireDetectorById(int id) {
		return fR.findById(id);
	}

	public List<FireDetector> getAllFireDetectors() {
		return fR.findAll(PageRequest.of(0, 2000)).getContent();
	}

	public Page<FireDetector> getAllFireDetectors(Pageable p) {
		return fR.findAll(p);
	}

	public void deleteFireDetectorById(int id) {
		fR.deleteById(id);
	}

	public void printList(List<FireDetector> list) {
		for (FireDetector l : list)
			log.info(l.toString());
	}

}
