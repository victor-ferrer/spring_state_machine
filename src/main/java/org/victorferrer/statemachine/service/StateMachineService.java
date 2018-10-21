/**
 * 
 */
package org.victorferrer.statemachine.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.listener.StateMachineListener;
import org.springframework.statemachine.state.State;
import org.springframework.statemachine.transition.Transition;
import org.springframework.stereotype.Service;
import org.victorferrer.statemachine.model.Events;
import org.victorferrer.statemachine.model.States;

/**
 * @author victor
 *
 */
@Service
public class StateMachineService implements TaskService {

	@Autowired
	private StateMachineFactory<States, Events> smFactory;
	
	
	private Map<String,StateMachine<States,Events>> smMap = new HashMap<>();
	
	/* (non-Javadoc)
	 * @see org.victorferrer.statemachine.service.TaskService#create(java.lang.String)
	 */
	@Override
	public void create(String taskId) {
		if (smMap.containsKey(taskId)) {
			throw new IllegalArgumentException("State Machine already exists: " + taskId);
		}
		
		StateMachine<States, Events> sm = smFactory.getStateMachine(taskId);
		sm.addStateListener(new TaskStateMachineListener());
		smMap.put(taskId, sm);
	}

	/* (non-Javadoc)
	 * @see org.victorferrer.statemachine.service.TaskService#start(java.lang.String)
	 */
	@Override
	public void start(String taskId) {
		if (!smMap.containsKey(taskId)) {
			throw new IllegalArgumentException("State Machine not found: " + taskId);
		}
		
		smMap.get(taskId).start();

	}

	/* (non-Javadoc)
	 * @see org.victorferrer.statemachine.service.TaskService#event(java.lang.String, org.victorferrer.statemachine.model.Events)
	 */
	@Override
	public void event(String taskId, Events event) {
		if (!smMap.containsKey(taskId)) {
			throw new IllegalArgumentException("State Machine not found: " + taskId);
		}
		smMap.get(taskId).sendEvent(event);
	}

	/* (non-Javadoc)
	 * @see org.victorferrer.statemachine.service.TaskService#getState(java.lang.String)
	 */
	@Override
	public States getState(String taskId) {
		if (!smMap.containsKey(taskId)) {
			throw new IllegalArgumentException("State Machine not found: " + taskId);
		}
		return smMap.get(taskId).getState().getId();
	}

}
