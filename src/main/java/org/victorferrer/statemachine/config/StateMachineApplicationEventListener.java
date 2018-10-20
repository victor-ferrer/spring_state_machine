package org.victorferrer.statemachine.config;

import org.springframework.context.ApplicationListener;
import org.springframework.statemachine.event.StateMachineEvent;

public class StateMachineApplicationEventListener implements ApplicationListener<StateMachineEvent> {

	@Override
	public void onApplicationEvent(StateMachineEvent event) {
		System.out.println("Event detected:" + event.toString());
	}

}