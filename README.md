# spring_state_machine
Sample project with Spring State Machine handling the status of several tasks:

![State Machine Diagram](https://raw.githubusercontent.com/victor-ferrer/spring_state_machine/master/State%20Machine.png)
      
It has a REST controller to interact with the system, let's say we want to create and use
a State Machine called "hello_world"

```
# To create a new instance:
curl localhost:8080/create/hello_world

# To start a new instance (State will be ACCEPTED)
curl localhost:8080/start/hello_world

# To send events to the State Machine
curl localhost:8080/event/hello_world/STARTED
curl localhost:8080/event/hello_world/FINISHED_OK
curl localhost:8080/event/hello_world/FINISHED_ERROR

# To get the current state
curl localhost:8080/state/hello_world

# Access to Spring Boot Actuator State Machine Traces
curl localhost:8080/actuator/statemachinetrace
```

This is the component structure so far:

![Component Diagram](https://raw.githubusercontent.com/victor-ferrer/spring_state_machine/master/Layers.png)

## Deployment in Openshift

Activate this Spring Profile to use a Postgres database instead of H2:

```
SPRING_PROFILES_ACTIVE=openshift
```

This is not Openshift specific, it is just that I am deploying there the service as part of a personal learning exercise.

## TODO list
- [ ] Testing
- [ ] State Machine in Service not deleted after finished
- [ ] State Machine Storage in Service is not thread-safe
- [ ] Timeout to configurable
- [ ] Example that injects the State Machine configuration from the outside
- [ ] Example of a Process using the State Machine Service
