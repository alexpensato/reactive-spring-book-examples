package br.com.pensato.springbook.controller;

import br.com.pensato.springbook.component.TemperatureSensor;
import br.com.pensato.springbook.handler.RxSseEmitter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.servlet.http.HttpServletRequest;

@RestController
public class TemperatureController {

    private final TemperatureSensor temperatureSensor;

    public TemperatureController(TemperatureSensor temperatureSensor) {
        this.temperatureSensor = temperatureSensor;
    }

    @GetMapping(value = "/temperature-stream")
    public SseEmitter events(HttpServletRequest request) {
        RxSseEmitter emitter = new RxSseEmitter();
        temperatureSensor.temperatureStream()
                .subscribe(emitter.getSubscriber());
        return emitter; // (5)
    }
}
