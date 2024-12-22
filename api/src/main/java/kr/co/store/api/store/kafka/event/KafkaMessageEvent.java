package kr.co.store.api.store.kafka.event;

public interface KafkaMessageEvent {
    String getTopic();
    String getKey();
    String getPayload();
}
