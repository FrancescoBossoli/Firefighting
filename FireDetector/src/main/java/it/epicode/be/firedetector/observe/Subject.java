package it.epicode.be.firedetector.observe;

import lombok.Getter;

@Getter
public class Subject {
	
	protected Observer observer;
	
	public void register(Observer o) {
		this.observer = o;		
	}
	
	public void unregister(Observer o) {
		this.observer = null;		
	}
	
	public void notifyObserver() {}
	
}
