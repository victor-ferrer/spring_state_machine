package org.victorferrer.statemachine.config;

import org.springframework.context.ApplicationListener;
import org.springframework.statemachine.annotation.OnStateMachineStart;
import org.springframework.statemachine.event.OnEventNotAcceptedEvent;
import org.springframework.statemachine.event.OnStateChangedEvent;
import org.springframework.statemachine.event.StateMachineEvent;

public class StateMachineApplicationEventListener implements ApplicationListener<StateMachineEvent> {

	@Override
	public void onApplicationEvent(StateMachineEvent event) {
		
		if (event instanceof OnStateMachineStart) {
			System.out.println("State MAchine started!");
		}
		if (event instanceof OnStateChangedEvent) {
			Object id = ((OnStateChangedEvent)event).getTargetState().getId();
			System.out.println("State changed to: " + id);
		}
		else if (event instanceof OnEventNotAcceptedEvent) {
			System.out.println("Event not accepted: " + ((OnEventNotAcceptedEvent)event).getEvent());
		}
		
	}

}