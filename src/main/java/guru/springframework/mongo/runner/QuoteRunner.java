package guru.springframework.mongo.runner;

import guru.springframework.mongo.service.QuoteGeneratorService;
import guru.springframework.mongo.service.QuoteHistoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
@RequiredArgsConstructor
@Slf4j
public class QuoteRunner implements CommandLineRunner {

    private final QuoteGeneratorService quoteGeneratorService;
    private final QuoteHistoryService quoteHistoryService;

    @Override
    public void run(String... args) throws Exception {

        quoteGeneratorService
                .fetchQuoteStream(Duration.ofMillis(100L))
                .take(10)
                .log("Got quote : ")
                .flatMap(quoteHistoryService::save)
                .subscribe(savedQuote -> {
                            log.info("Saved quote : " + savedQuote);
                        },
                        throwable -> {
                            log.error("error", throwable.getMessage());
                        },
                        () -> {
                            log.info("Done!");
                        });
    }
}
