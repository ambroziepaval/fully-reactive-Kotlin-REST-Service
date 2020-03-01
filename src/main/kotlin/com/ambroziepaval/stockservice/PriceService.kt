package com.ambroziepaval.stockservice

import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import java.time.Duration
import java.time.LocalDateTime
import java.util.concurrent.ThreadLocalRandom

@Service
class PriceService {

    fun getPrices(symbol: String): Flux<StockPrice> {
        return Flux.interval(Duration.ofSeconds(1))
                .map { StockPrice(symbol, generateRandomPrice(), LocalDateTime.now()) }
                .share()
    }

    private fun generateRandomPrice(): Double {
        return ThreadLocalRandom.current().nextDouble(100.0)
    }
}
