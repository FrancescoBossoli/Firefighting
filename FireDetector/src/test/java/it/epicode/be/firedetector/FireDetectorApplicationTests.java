package it.epicode.be.firedetector;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import it.epicode.be.firedetector.models.*;
import it.epicode.be.firedetector.services.CoordinateService;
import it.epicode.be.firedetector.services.FireDetectorService;
import it.epicode.be.firedetector.services.ProbeService;
import it.epicode.be.firedetector.services.RemoteAccessService;
import it.epicode.be.firedetector.utils.NotificationManager;
import it.epicode.be.firedetector.utils.notification.DangerNotification;
import it.epicode.be.firedetector.utils.notification.InfoNotification;
import it.epicode.be.firedetector.utils.notification.WarningNotification;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@WebAppConfiguration
@AutoConfiguration
@SpringBootTest
class FireDetectorApplicationTests {

	@Resource
	CoordinateService cS;
	
	@Resource
	ProbeService pS;
	
	@Resource
	FireDetectorService fS;
	
	Probe probe;
	FireDetector detector;
	Latitude x;
	Longitude y;

	@BeforeEach
	public void getElements() {
		detector = FireDetector.builder()
							   .name("Sistema Rilevamento Incendi")
							   .build();

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

		probe = Probe.builder()
					 .id(1)
					 .name("Riserva Naturale del Circeo - Area Nord")
					 .latitude(x)
					 .longitude(y)
					 .build();

		probe.register(detector);
	}
	
	@Test
	@DisplayName("Verifica dei dati presenti nel Database dopo la prima run")
	public void dataAvailabilityCheck() {
		
		Probe p = pS.getProbeByCoordinates(x, y).get();		
		assertEquals(p.getName(), probe.getName());
		assertTrue(pS.getAllProbes().size() == 5);
		assertTrue(cS.getAllCoordinates().size() == 10);
		assertTrue(fS.getAllFireDetectors().size() == 1);
		assertEquals(fS.getFireDetectorById(1).get().getName(), "Sistema Rilevamento Incendi");
	}

	@Test
	@DisplayName("Controllo della corretta associazione Observer-Subject")
	public void testFireDetector() {
		
		assertTrue(probe.getObserver() == detector);
		probe.unregister(detector);
		assertNull(probe.getObserver());		
	}
	
	@Test
	@DisplayName("Controllo sull'effettiva mobilitazione dell'Observer")
	public void testEmergencyCall() {
		
		FireDetector f = mock(FireDetector.class);
		Probe p = mock(Probe.class);
		p.register(f);
		p.scan();
		if (p.getDanger() > 5) verify(f, times(1)).update(p);
	}
	
	@Test
	@DisplayName("Si accerta che la string factory lanci il metodo prestabilito al cambiare del parametro danger")
	public void testNotificationManager() {
		
		MockedStatic<InfoNotification> info = Mockito.mockStatic(InfoNotification.class);
		MockedStatic<WarningNotification> warning = Mockito.mockStatic(WarningNotification.class);
		MockedStatic<DangerNotification> danger = Mockito.mockStatic(DangerNotification.class);
		probe.setDanger(2);
		NotificationManager.redact(probe);
		info.verify( () -> InfoNotification.write(probe.toString() + " - Misurazione Rilevata: Livello " + probe.getDanger()), times(1));
		probe.setDanger(7);
		NotificationManager.redact(probe);
		warning.verify( () -> WarningNotification.write(probe.toString() + " - Misurazione Rilevata: Livello " + probe.getDanger()), times(1));
		probe.setDanger(9);
		NotificationManager.redact(probe);
		danger.verify( () -> DangerNotification.write(probe.toString() + " - Misurazione Rilevata: Livello " + probe.getDanger()), times(1));
	}
	
	//Necessita del secondo server in funzione - Test login su secondo server
	@Test
	@DisplayName("Assicura che la stringa ritornata sia il token comunicato dal server esterno")
	public void testTokenFetching() {		
		
		RemoteAccessService raS = new RemoteAccessService();
		assertTrue(raS.getToken().startsWith("ey"));		
	}
	
	//Necessita del secondo server in funzione - Test chiamata post e uso token su secondo server
	@Test
	@DisplayName("Assicura la corretta comunicazione tra i 2 server, ottenendo il messaggio di risposta previsto")			
	public void testServerCommunication() {
		
		RemoteAccessService raS = new RemoteAccessService();
		String alarmUrl = "http://localhost:1515/alarm?idsonda=" + probe.getId() + "&lat=" + raS.parse(x.toString()) 
						+ "&lon=" + raS.parse(y.toString()) + "&smokelevel=" + 9;
		
		raS.emergencyAlert(probe);
		assertTrue(raS.connect(alarmUrl, probe.toString()).equals("Segnalazione ricevuta, i Soccorsi sono in mobilitazione verso " + probe.getName()));
	}

}
