package org.victorferrer.statemachine.controller;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.victorferrer.statemachine.exception.StateMachineException;
import org.victorferrer.statemachine.service.TaskService;

/**
 * REST controller over the StateMachine Service.
 * If you want to expose it, so other applications might send requests
 * use {{enableStateMachineRestController}}
 * @author victor
 *
 */
@Controller
@ConditionalOnProperty(name="enableStateMachineRestController", havingValue="true")
@RequestMapping("/")
public class StateMachineController {
	
	@Autowired
	private TaskService taskService;
	
	// TODO Build a POJO with info about the SM
	
	@GetMapping("/sm")
	@ResponseBody
	public Set<String> getAll(){
		return new HashSet<String>(Arrays.asList("todo"));
	}
	
	@GetMapping("/sm/{id}")
	public String get(){
		return "TODO";
	}
	

	@PostMapping("/sm/{id}")
	@ResponseBody
	public ResponseEntity<Object> create(@PathVariable String id) {
		taskService.create(id);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(id);
	}
	
	
	@GetMapping("/sm(/{id}/start")
	public ResponseEntity<String> start(@PathVariable String id) throws StateMachineException {
		taskService.start(id);
		return ResponseEntity.status(HttpStatus.OK).body(id);
	}
	
	@PostMapping("/event/{stateMachine}/{event}")
	public ResponseEntity<Object> event(@PathVariable String stateMachine, @PathVariable String event) throws StateMachineException {
		boolean eventAccepted = taskService.event(stateMachine,event);
		
		if (eventAccepted) {
			return ResponseEntity.status(HttpStatus.OK).build();
		}
		else {
			String msg = String.format("Event [%S] not accepted by StateMachine [%s]", event, stateMachine);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg);
		}
	}
}
