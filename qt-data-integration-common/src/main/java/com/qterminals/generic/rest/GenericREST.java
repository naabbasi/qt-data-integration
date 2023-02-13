package com.qterminals.generic.rest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.UUID;

@Slf4j
public class GenericREST<T> {
    private static SseEmitter sseEmitter;

    @GetMapping("/events")
    public SseEmitter streamSseMvc() {
        return getEmitter();
    }

    private SseEmitter getEmitter() {
        if(sseEmitter == null){
            //sseEmitter = new SseEmitter(3600000l);
            sseEmitter = new SseEmitter();
            sseEmitter.onCompletion(()-> {
                sseEmitter = null;
            });

            sseEmitter.onTimeout(()-> {
                if(sseEmitter != null){
                    sseEmitter.complete();
                }
            });
        }

        return sseEmitter;
    }

    public void sendEventToClient(T data) {
        SseEmitter.SseEventBuilder event = SseEmitter.event()
                .data(data)
                .id(UUID.randomUUID().toString())
                .name("data-integration");

        try {
            getEmitter().send(event);
        } catch (IOException | IllegalStateException e) {
            log.error("sendEventToClient(...) ", e);
        }
    }
}
