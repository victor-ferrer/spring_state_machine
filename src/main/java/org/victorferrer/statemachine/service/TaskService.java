package org.victorferrer.statemachine.service;

import org.victorferrer.statemachine.exception.StateMachineException;

/**
 * Contract for interacting with Tasks
 * @author victor
 *
 */
public interface TaskService 
{

	/**
	 * Creates a new Task with the given ID
	 * @param taskId
	 */
	public void create(String taskId);
	
	/**
	 * Starts the Task with the given ID
	 * @param taskId
	 * @throws StateMachineException 
	 */
	public void start(String taskId) throws StateMachineException;
	
	/**
	 * Sends an Event to the given task 
	 * @param taskId
	 * @param event
	 * @return Boolean on whether the event was accepted by the StateMachine
	 * @throws StateMachineException
	 */
	public boolean event(String taskId, String event) throws StateMachineException;
	
	/**
	 * Gets the current state of the given task
	 * @param taskId
	 * @return
	 * @throws StateMachineException 
	 */
	public String getState(String taskId) throws StateMachineException;
}
