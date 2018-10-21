package org.victorferrer.statemachine.service;

import org.springframework.messaging.Message;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.listener.StateMachineListener;
import org.springframework.statemachine.state.State;
import org.springframework.statemachine.transition.Transition;
import org.victorferrer.statemachine.model.Events;
import org.victorferrer.statemachine.model.States;

public class TaskStateMachineListener implements StateMachineListener<States, Events> {

	@Override
	public void stateChanged(State<States, Events> from, State<States, Events> to) {
		// TODO Auto-generated method stub

	}

	@Override
	public void stateEntered(State<States, Events> state) {
		// TODO Auto-generated method stub

	}

	@Override
	public void stateExited(State<States, Events> state) {
		// TODO Auto-generated method stub

	}

	@Override
	public void eventNotAccepted(Message<Events> event) {
		System.out.println("This event was not accepted:" + event.getPayload());
	}

	@Override
	public void transition(Transition<States, Events> transition) {
		// TODO Auto-generated method stub

	}

	@Override
	public void transitionStarted(Transition<States, Events> transition) {
		// TODO Auto-generated method stub

	}

	@Override
	public void transitionEnded(Transition<States, Events> transition) {
		// TODO Auto-generated method stub

	}

	@Override
	public void stateMachineStarted(StateMachine<States, Events> stateMachine) {
		// TODO Auto-generated method stub

	}

	@Override
	public void stateMachineStopped(StateMachine<States, Events> stateMachine) {
		// TODO Auto-generated method stub

	}

	@Override
	public void stateMachineError(StateMachine<States, Events> stateMachine, Exception exception) {
		// TODO Auto-generated method stub

	}

	@Override
	public void extendedStateChanged(Object key, Object value) {
		// TODO Auto-generated method stub

	}

	@Override
	public void stateContext(StateContext<States, Events> stateContext) {
		// TODO Auto-generated method stub

	}

}
