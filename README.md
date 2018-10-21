# spring_state_machine
Sample project with Spring State Machine handling task status.

|
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