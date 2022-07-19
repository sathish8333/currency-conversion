package com.sr.microservices.service;

import com.sr.microservices.entity.CurrencyConverter;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

//@FeignClient(name = "currency-exchange",url = "localhost:8000")
@FeignClient(name = "currency-exchange") //for using automaticv load balencer we are removing url declaration
public interface CurrencyExchangeProxy {

    @GetMapping("/currency-exchange/from/{from}/to/{to}")
    public CurrencyConverter retriveExchange(
            @PathVariable String from,
            @PathVariable String to
    );
}
