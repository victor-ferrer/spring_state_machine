package org.victorferrer.statemachine.exception;

/**
 * Base exception for the exceptions to be thrown when interacting with
 * StateMachines
 * @author victor
 *
 */
public class StateMachineException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3420772215512896744L;
	
	public StateMachineException(String message) {
		super(message);
	}

}
