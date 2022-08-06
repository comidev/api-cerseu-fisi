package comidev.apicerseufisi.services;

import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.junit.jupiter.api.Assertions.fail;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.io.UnsupportedEncodingException;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
public class Response {
    private final ResultActions resultActions;

    public void isStatus(HttpStatus status) throws Exception {
        resultActions.andExpect(status().is(status.value()));
    }

    public <T> T body(Class<T> clazz) {
        try {
            return new ObjectMapper().readValue(bodyString(), clazz);
        } catch (JsonProcessingException e) {
            log.error("Error al deserealizar el JSON -> {}", e.getMessage());
            fail("Failed to convert json to object");
            return null;
        }
    }

    public String bodyString() {
        try {
            return resultActions.andReturn().getResponse().getContentAsString();
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }

    public boolean bodyContains(Object... objects) {
        String body = bodyString();
        return List.of(objects).stream()
                .allMatch(obj -> body.contains(obj.toString()));
    }
}
