package com.ambroziepaval.stockservice

import org.apache.commons.logging.LogFactory
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import java.time.Duration
import java.time.LocalDateTime
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ThreadLocalRandom

@Service
class PriceService {

    private val pricesForStock = ConcurrentHashMap<String, Flux<StockPrice>>()
    private val log = LogFactory.getLog(javaClass)

    fun getPrices(symbol: String): Flux<StockPrice> {

        return pricesForStock.computeIfAbsent(symbol) {
            Flux.interval(Duration.ofSeconds(1))
                    .map { StockPrice(symbol, generateRandomPrice(), LocalDateTime.now()) }
                    .doOnSubscribe { log.info("New subscription for symbol $symbol.") }
                    .share()
        }
    }

    private fun generateRandomPrice(): Double {
        return ThreadLocalRandom.current().nextDouble(100.0)
    }
}
