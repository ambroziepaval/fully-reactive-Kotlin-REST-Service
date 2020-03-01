package com.ambroziepaval.stockservice

import org.springframework.http.MediaType
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class RestController(val priceService: PriceService) {

    @GetMapping(value = ["/stocks/{symbol}"],
            produces = [MediaType.TEXT_EVENT_STREAM_VALUE])
    fun pricesFor(@PathVariable symbol: String) = priceService.getPrices(symbol)
}

@Controller
class RSocketController(val priceService: PriceService) {

    @MessageMapping("stockPrices")
    fun pricesFor(symbol: String) = priceService.getPrices(symbol)
}