package br.com.sanara.codechella;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import java.time.Duration;
import java.util.stream.Stream;

@RestController
public class SseController {

    @GetMapping(path = "/sse", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> streamEvents() {
        return Flux.fromStream(Stream.generate(() -> "Evento: " + System.currentTimeMillis()))
                .delayElements(Duration.ofSeconds(1));
    }
}