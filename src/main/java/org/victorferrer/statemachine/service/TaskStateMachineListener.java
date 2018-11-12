package org.victorferrer.statemachine.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.listener.StateMachineListener;
import org.springframework.statemachine.state.State;
import org.springframework.statemachine.transition.Transition;

public class TaskStateMachineListener implements StateMachineListener<String, String> {

	private Logger logger = LoggerFactory.getLogger(TaskStateMachineListener.class);

	@Override
	public void stateChanged(State<String, String> from, State<String, String> to) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void stateEntered(State<String, String> state) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void stateExited(State<String, String> state) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void eventNotAccepted(Message<String> event) {
		// TODO Auto-generated method stub
		logger.warn("Event not accepted: " + event.toString());
		
	}

	@Override
	public void transition(Transition<String, String> transition) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void transitionStarted(Transition<String, String> transition) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void transitionEnded(Transition<String, String> transition) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void stateMachineStarted(StateMachine<String, String> stateMachine) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void stateMachineStopped(StateMachine<String, String> stateMachine) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void stateMachineError(StateMachine<String, String> stateMachine, Exception exception) {
		// TODO Auto-generated method stub
		String msg = String.format("State Machine [%s] in state [%s] threw and Exception:", stateMachine.getId(), stateMachine.getState(), exception.getMessage());
		logger.error(msg,exception);
	}

	@Override
	public void extendedStateChanged(Object key, Object value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void stateContext(StateContext<String, String> stateContext) {
		// TODO Auto-generated method stub
		
	}


	

}
