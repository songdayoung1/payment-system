package kr.co.store.api.global.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MessageQueueUtil {
    private static final String FAIL_PARSING_MESSAGE = "message parsing error";

    /**
     * object -> string payload
     * @param payload
     * @param objectMapper
     * @return
     */
    public static String convertPayload(Object payload, ObjectMapper objectMapper) {
        try {
            return objectMapper.writeValueAsString(payload);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(FAIL_PARSING_MESSAGE);
        }
    }
}
