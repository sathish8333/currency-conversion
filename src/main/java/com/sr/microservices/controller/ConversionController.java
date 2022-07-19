package com.sr.microservices.controller;

import com.sr.microservices.entity.CurrencyConverter;
import com.sr.microservices.service.CurrencyExchangeProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.HashMap;

@RestController
public class ConversionController {

    @Autowired
    private CurrencyExchangeProxy proxy;

    @GetMapping("/currency-conversion/from/{from}/to/{to}/quantity/{quantity}")
    public CurrencyConverter getConvertedCorrency(
            @PathVariable String from,
            @PathVariable String to,
            @PathVariable BigDecimal quantity
    ){
        HashMap<String,String> uriVariables = new HashMap<>();
        uriVariables.put("from",from);
        uriVariables.put("to",to);

        ResponseEntity<CurrencyConverter> responseEntity = new RestTemplate().getForEntity("http://localhost:8000/currency-exchange/from/USD/to/INR",CurrencyConverter.class,uriVariables);
        CurrencyConverter entityBody = responseEntity.getBody();
        return new CurrencyConverter(entityBody.getId(),from,to,entityBody.getConversionMultiple(),quantity,quantity.multiply(entityBody.getConversionMultiple()),entityBody.getEnvironment());
    }

    @GetMapping("/currency-conversion-feign/from/{from}/to/{to}/quantity/{quantity}")
    public CurrencyConverter getConvertedCorrencyUsingFeign(
            @PathVariable String from,
            @PathVariable String to,
            @PathVariable BigDecimal quantity
    ){
        CurrencyConverter entityBody = proxy.retriveExchange(from, to);
        return new CurrencyConverter(entityBody.getId(),from,to,entityBody.getConversionMultiple(),quantity,quantity.multiply(entityBody.getConversionMultiple()),entityBody.getEnvironment()+" using feign");
    }
}
