package com.akkademy;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.TimeUnit;

import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.testkit.TestActorRef;
import com.akkademy.messages.JavaPongActor;
import org.junit.Test;
import scala.concurrent.Future;

import static akka.pattern.Patterns.ask;
import static scala.compat.java8.FutureConverters.toJava;

public class PongActorTest {

    ActorSystem system = ActorSystem.create();
    TestActorRef<JavaPongActor> actorRef = TestActorRef.create(system, Props.create(JavaPongActor.class));

    @Test
    public void shouldReplyToPingWithPong() throws Exception {
        Future<Object> sFuture = ask(actorRef, "Ping", 1000);
        CompletionStage<Object> cs = toJava(sFuture);
        CompletableFuture jFuture = (CompletableFuture)cs;
        assert (jFuture.get(1000, TimeUnit.MILLISECONDS).equals("Pong"));
    }

    @Test
    public void shouldReplyToUnknownMessageWithFailure() throws Exception {
        Future<Object> sFuture = ask(actorRef, "unknown", 1000);
        CompletionStage<Object> cs = toJava(sFuture);
        CompletableFuture jFuture = (CompletableFuture)cs;
        assert (jFuture.get(1000, TimeUnit.MILLISECONDS).equals("pong"));
    }

    public CompletionStage<String> askPong(String message) {
        Future sFuture = ask(actorRef, message, 1000);
        final CompletionStage<String> cs = toJava(sFuture);
        return cs;
    }

    @Test
    public void printToConsole() throws Exception {
        askPong("Ping")
            .thenAccept(x -> System.out.println("replied with: " + x));
        Thread.sleep(100);
    }
}
