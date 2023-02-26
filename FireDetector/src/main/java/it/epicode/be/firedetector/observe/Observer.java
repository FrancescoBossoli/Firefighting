package it.epicode.be.firedetector.observe;

import it.epicode.be.firedetector.models.Probe;
import it.epicode.be.firedetector.services.RemoteAccessService;

public interface Observer {
	
	static final RemoteAccessService raS = new RemoteAccessService();

	public void update(Probe p);

	
}
