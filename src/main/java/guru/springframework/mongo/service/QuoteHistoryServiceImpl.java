package guru.springframework.mongo.service;

import guru.springframework.mongo.domain.QuoteHistory;
import guru.springframework.mongo.model.Quote;
import guru.springframework.mongo.repositories.QuoteHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class QuoteHistoryServiceImpl implements QuoteHistoryService {

    private final QuoteHistoryRepository quoteHistoryRepository;
    @Override
    public Mono<QuoteHistory> save(Quote quote) {
        return quoteHistoryRepository
                .save(QuoteHistory
                .builder()
                        .price(quote.getPrice())
                        .ticker(quote.getTicker())
                        .instant(quote.getInstant())
                        .build());
    }
}
