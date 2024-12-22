package kr.co.store.api.store.kafka.event;

import lombok.Builder;
import lombok.Getter;

@Getter
public class KafkaMessageSendEvent implements KafkaMessageEvent {

    private String topic;
    private String key;
    private String payload;

    @Builder
    public KafkaMessageSendEvent(String topic, String key, String payload) {
        this.topic = topic;
        this.key = key;
        this.payload = payload;
    }

    public static KafkaMessageSendEvent of(String topic, String key, String payload) {
        return KafkaMessageSendEvent.builder()
                .topic(topic)
                .key(key)
                .payload(payload)
                .build();
    }
}
