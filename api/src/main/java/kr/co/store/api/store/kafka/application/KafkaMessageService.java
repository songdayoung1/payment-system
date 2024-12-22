package kr.co.store.api.store.kafka.application;

import kr.co.store.api.store.kafka.event.KafkaMessageEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.KafkaException;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaMessageService {
    private final KafkaTemplate<String, String> kafkaTemplate;

    /**
     * 동기 방식
     *
     * @param event
     */
    public void sendKafkaMessage(KafkaMessageEvent event) {
        ProducerRecord<String, String> producerRecord = createProducerRecord(event.getTopic(), event.getKey(), event.getPayload());

        try {
            kafkaTemplate.send(producerRecord)
                    .whenComplete((result, ex) -> {
                        if (ex == null) {
                            printLog(event.getTopic(), event.getPayload(), result);
                        } else {
                            saveErrorRecord(event, ex);
                        }
                    });
        } catch (KafkaException ex) {
            saveErrorRecord(event, ex);
        } catch (Exception e) {
            saveErrorRecord(event, e);
        }
    }

    private ProducerRecord<String, String> createProducerRecord(String topic, String key, String value) {
        return new ProducerRecord<>(topic, null, key, value, null);
    }


    private void saveErrorRecord(KafkaMessageEvent event, Throwable ex) {
        log.error("Error Sending the Message and the exception is {}", ex.getMessage());
    }

    private void printLog(String key, String value, SendResult<String, String> result) {
        log.info("Message Sent SuccessFully for the key : {} and the value is {} , partition is {}", key, value, result.getRecordMetadata().toString());
    }

}
