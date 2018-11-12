package org.victorferrer.statemachine.config;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Optional;

import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.victorferrer.statemachine.persistence.EventRecord;
import org.victorferrer.statemachine.persistence.EventRecordRepository;

/**
 * Sample State MAchine Configuration that might be replaced
 * by a more sophisticated one.
 * @author victor
 *
 */
@Configuration
@EnableStateMachineFactory
public class StateMachineConfig  extends StateMachineConfigurerAdapter<String, String> {

	// States
	public static final String ACCEPTED = "Accepted";
	public static final String RUNNING = "Running";
	public static final String FINISHED_OK = "Finished OK";
	public static final String FINISHED_ERROR = "Finished Error";
	
	// Events
	public static final String STARTED_EVENT = "Started";
	public static final String FINISHED_OK_EVENT = "Finished OK";
	public static final String FINISHED_ERROR_EVENT = "Finished Error";
	public static final String TIMEOUT_EVENT = "Timeout";

	@Autowired
	private EventRecordRepository eventRepo;
	
	private Logger logger = LoggerFactory.getLogger(StateMachineConfig.class); 
	
    @Override
    public void configure(StateMachineStateConfigurer<String, String> configurer)
            throws Exception {
        configurer
            .withStates()
                .initial(ACCEPTED)
                .end(FINISHED_OK)
                .end(FINISHED_ERROR)
                .states(new HashSet<String>(Arrays.asList(ACCEPTED, RUNNING,
                										  FINISHED_OK, FINISHED_ERROR)));
    }
    
    @Override
    public void configure(StateMachineTransitionConfigurer<String, String> transitions)
            throws Exception {
        transitions
            .withExternal()
                .source(ACCEPTED).target(RUNNING)
                .event(STARTED_EVENT)
                .action(persistAction(RUNNING))
                .and()
            .withExternal()
                .source(RUNNING).target(FINISHED_OK)
                .event(FINISHED_OK_EVENT)
                .action(persistAction(FINISHED_OK),persistAction(FINISHED_ERROR))
                .and()
            // FINISH ERROR due to timeout
            .withExternal()
                .source(RUNNING).target(FINISHED_ERROR)
                .event(TIMEOUT_EVENT)
                .action(persistAction(FINISHED_ERROR))
                .and()                
            // Handle timeout
            .withInternal()
                .source(RUNNING)
                .action(timeoutAction())
                .timerOnce(10000);
    }

	private Action<String, String> timeoutAction() {
    	
    	return new Action<String, String>(){

			@Override
			public void execute(StateContext<String, String> context) {
				context.getStateMachine().sendEvent(TIMEOUT_EVENT);
			}
    		
    	};
	}

	private Action<String, String> persistAction(String state) {
    	
    	return context -> {
    		
    		Optional<Exception> ex = Optional.ofNullable(context.getException());
    		String id = context.getStateMachine().getId();
    		logger.info(String.format("Persisting [%s state for StateMachine [%s] to DB",state,id));
    		
    		EventRecord record = new EventRecord();
    		record.setTaskName(id);
    		record.setTimestamp(new Date());
    		record.setState(state);
    		record.setErrorMsg(ex.isPresent() ? ex.get().getMessage() : Strings.EMPTY);
			eventRepo.save(record);
		};
	}
}
