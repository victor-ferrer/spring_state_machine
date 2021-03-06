/**
 * 
 */
package org.victorferrer.statemachine.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.stereotype.Service;
import org.victorferrer.statemachine.model.Events;
import org.victorferrer.statemachine.model.States;
import org.victorferrer.statemachine.persistence.EventRecord;
import org.victorferrer.statemachine.persistence.EventRecordRepository;

/**
 * @author victor
 *
 */
@Service
public class StateMachineService implements TaskService {

	@Autowired
	private StateMachineFactory<States, Events> smFactory;
	
	@Autowired
	private EventRecordRepository repository;
	
	
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
		
		EventRecord record = new EventRecord();
		record.setTaskName(taskId);
		record.setState(sm.getInitialState().getId());
		record.setTimestamp(new Date());
		
		repository.save(record);
		
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
