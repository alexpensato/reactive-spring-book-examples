package br.com.pensato.springbook.component;

import br.com.pensato.springbook.model.Temperature;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component
public class TemperatureSensor {

    private final ApplicationEventPublisher publisher;
    private final Random random;
    private final ScheduledExecutorService executor;

    public TemperatureSensor(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
        this.random = new Random();
        this.executor = Executors.newSingleThreadScheduledExecutor();
    }

    @PostConstruct
    public void startProcessing() {
        this.executor.schedule(this::probe, 1, TimeUnit.SECONDS);
    }

    private void probe() {
        double temperature = 16 + random.nextGaussian() * 10;
        publisher.publishEvent(new Temperature(temperature));
        // schedule the next read after some random delay (0-5 seconds)
        executor.schedule(this::probe, random.nextInt(5000), TimeUnit.MILLISECONDS);
    }
}
