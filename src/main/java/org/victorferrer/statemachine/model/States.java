package org.victorferrer.statemachine.model;

public enum States 
{
	ACCEPTED("Accepted"),RUNNING("Running"),FINISHEDOK("Finished OK"), FINISHEDERROR("Finished Error");
	
	private String label;
	
	States(String label){
		this.label = label;
	}
	
	
	@Override
	public String toString() {
		return label;
	}
}
