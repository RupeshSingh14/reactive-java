package com.singh.rupesh.part9Sinks;

import com.singh.rupesh.utils.Util;
import lombok.Data;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.util.function.Consumer;

public class Slack {

    public static void main(String[] args) {
        SlackRoom slackRoom = new SlackRoom("reactor");
        SlackMember ram = new SlackMember("Ram");
        SlackMember raunak = new SlackMember("Raunak");
        SlackMember riddhi = new SlackMember("Ridhhi");

        slackRoom.joinRoom(ram);
        slackRoom.joinRoom(raunak);

        ram.says("Hi All");
        Util.sleepSeconds(4);

        raunak.says("Hey");
        ram.says("Hi Raunak");
        Util.sleepSeconds(4);

        slackRoom.joinRoom(riddhi);
        riddhi.says("Hi Everyone");
    }
}

class SlackRoom {
    private String name;
    private Sinks.Many<SlackMessage> sink;
    private Flux<SlackMessage> flux;

    public SlackRoom(String name) {
        this.name = name;
        this.sink = Sinks.many().replay().all();
        this.flux = this.sink.asFlux();
    }

    public void joinRoom(SlackMember slackMember) {
        System.out.println(slackMember.getName() + " ------joined------ " + this.name);
        this.subscribe(slackMember);
        slackMember.setMessageConsumer(
                msg -> this.postMessage(msg, slackMember)
        );
    }

    private void subscribe(SlackMember slackMember) {
        this.flux
                .filter(sm -> !sm.getSender().equals(slackMember.getName()))
                .doOnNext(sm -> sm.setReceiver(slackMember.getName()))
                .map(SlackMessage::toString)
                .subscribe(slackMember::receives);
    }

    private void postMessage(String msg, SlackMember slackMember) {
        SlackMessage slackMessage = new SlackMessage();
        slackMessage.setSender(slackMember.getName());
        slackMessage.setMessage(msg);
        this.sink.tryEmitNext(slackMessage);
    }



}

@Data
class SlackMember {

    private String name;
    private Consumer<String> messageConsumer;

    public SlackMember(String name) {
        this.name = name;
    }

    public void receives(String message) {
        System.out.println(message);
    }

    public void says(String message) {
        this.messageConsumer.accept(message);
    }

    void setMessageConsumer(Consumer<String> messageConsumer) {
        this.messageConsumer = messageConsumer;
    }
}


@Data
class SlackMessage {

    private static final String FORMAT = "[%s -> %s] : %s";

    private String sender;
    private String receiver;
    private String message;

    public String toString() {
        return String.format(FORMAT, this.sender, this.receiver, this.message);
    }

}