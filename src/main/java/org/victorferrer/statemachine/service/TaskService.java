package org.victorferrer.statemachine.service;

import org.victorferrer.statemachine.model.Events;
import org.victorferrer.statemachine.model.States;

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
	 */
	public void start(String taskId);
	
	/**
	 * Sends an Event to the given task
	 * @param taskId
	 * @param event
	 */
	public void event(String taskId, Events event);
	
	/**
	 * Gets the current state of the given task
	 * @param taskId
	 * @return
	 */
	public States getState(String taskId);
}
