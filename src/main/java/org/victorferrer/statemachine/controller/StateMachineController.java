package org.victorferrer.statemachine.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.victorferrer.statemachine.model.Events;
import org.victorferrer.statemachine.service.TaskService;

@Controller
@RequestMapping("/")
public class StateMachineController {
	
	@Autowired
	private TaskService taskService;
	

	@GetMapping("/create/{id}")
	@ResponseBody
	public ResponseEntity<Object> create(@PathVariable String id) {
		taskService.create(id);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(id);
	}
	
	
	@GetMapping("/start/{id}")
	@ResponseBody
	public ResponseEntity<String> start(@PathVariable String id) {
		taskService.start(id);
		return ResponseEntity.status(HttpStatus.OK).body(id);
	}
	
	@GetMapping(value = "/event/{task}/{event}")
	@ResponseBody
	public ResponseEntity<Object> event(@PathVariable String task, @PathVariable String event) {
		taskService.event(task,Events.valueOf(event));
		return ResponseEntity.status(HttpStatus.OK).build();
	}
	
	@GetMapping(value = "/state/{task}")
	@ResponseBody
	public ResponseEntity<Object> getState(@PathVariable String task) {
		return ResponseEntity.status(HttpStatus.OK)
							 .body(taskService.getState(task));
	}
	
	

}
