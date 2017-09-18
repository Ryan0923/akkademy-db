package com.akkademy.messages;

import java.util.HashMap;
import java.util.Map;

import akka.actor.AbstractActor;
import akka.actor.Status;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.pf.ReceiveBuilder;

public class AkkademyDb extends AbstractActor {

    protected final LoggingAdapter log = Logging.getLogger(context().system(), this);
    public final Map<String, Object> map = new HashMap<>();

    private AkkademyDb() {
        receive(
            ReceiveBuilder
                .match(SetRequest.class, message -> {
                    log.info("Received Set request: {}", message);
                    map.put(message.getKey(), message.getValue());
                    sender().tell(new Status.Success(message.key), self());
                })
                .match(GetRequest.class, message -> {
                    log.info("Received Get request: {}", message);
                    Object value = map.get(message.key);
                    Object response = (value != null) ? value : new Status.Failure(
                        new KeyNotFoundException(message.key));
                    sender().tell(response, self());
                })
                .matchAny(o ->
                    sender().tell(new Status.Failure(new ClassNotFoundException()), self())
                )
                .build()
        );
    }

}