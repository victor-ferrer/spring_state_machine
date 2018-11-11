package org.victorferrer.statemachine.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.listener.StateMachineListener;
import org.springframework.statemachine.state.State;
import org.springframework.statemachine.transition.Transition;
import org.victorferrer.statemachine.model.Events;

public class TaskStateMachineListener implements StateMachineListener<String, Events> {

	private Logger logger = LoggerFactory.getLogger(TaskStateMachineListener.class);

	@Override
	public void stateChanged(State<String, Events> from, State<String, Events> to) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void stateEntered(State<String, Events> state) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void stateExited(State<String, Events> state) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void eventNotAccepted(Message<Events> event) {
		logger.warn("Event not accepted: " + event.toString());
		
	}

	@Override
	public void transition(Transition<String, Events> transition) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void transitionStarted(Transition<String, Events> transition) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void transitionEnded(Transition<String, Events> transition) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void stateMachineStarted(StateMachine<String, Events> stateMachine) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void stateMachineStopped(StateMachine<String, Events> stateMachine) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void stateMachineError(StateMachine<String, Events> stateMachine, Exception exception) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void extendedStateChanged(Object key, Object value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void stateContext(StateContext<String, Events> stateContext) {
		// TODO Auto-generated method stub
		
	}
	

}
