package it.epicode.be.forestrycorps;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import it.epicode.be.forestrycorps.models.Direction;
import it.epicode.be.forestrycorps.models.Latitude;
import it.epicode.be.forestrycorps.models.Location;
import it.epicode.be.forestrycorps.models.Longitude;
import it.epicode.be.forestrycorps.models.User;
import it.epicode.be.forestrycorps.services.CoordinateService;
import it.epicode.be.forestrycorps.services.LocationService;
import it.epicode.be.forestrycorps.services.UserService;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import java.util.List;
import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@WebAppConfiguration
@AutoConfiguration
@SpringBootTest
class ForestryCorpsApplicationTests {
	
	@Resource
	LocationService lS;
	
	@Resource
	CoordinateService cS;
	
	@Resource
	UserService uS;
	
	Location location;	
	Latitude x;
	Longitude y;	
	
	@BeforeEach
	public void getElements() {
		
		x = Latitude.builder()
					.direction(Direction.N)
					.grades(41)
					.minutes(22)
					.seconds(11)
					.build();
	
		y = Longitude.builder()
				 	 .direction(Direction.E)
				 	 .grades(13)
				 	 .minutes(1)
				 	 .seconds(53)
				 	 .build();
		
		location = Location.builder()
						   .id(1)
						   .name("Riserva Naturale del Circeo - Area Nord")
						   .latitude(x)
						   .longitude(y)
						   .build();
		
		
	}
	
	@Test
	@DisplayName("Verifica dei dati presenti nel Database dopo la prima run")
	public void dataAvailabilityCheck() {
		
		Location loc = lS.getLocationByCoordinates(x, y).get();
		List<User> list = uS.getAllUsers();
		assertEquals(loc.getName(), location.getName());
		assertTrue(lS.getAllLocations().size() == 5);
		assertTrue(cS.getAllCoordinates().size() == 10);
		assertTrue(list.size() == 2);
		assertEquals(list.get(0).getUsername(), "Admin");
		assertEquals(list.get(1).getUsername(), "CirceoAlarm");
	}

}
