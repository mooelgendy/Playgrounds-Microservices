package com.elgendy.reservationservice.service;

import com.elgendy.reservationservice.model.Reservation;
import com.elgendy.reservationservice.model.dto.PlaygroundDTO;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;
import java.util.Date;

@Log4j2
@AllArgsConstructor
@Service
public class PlaygroundInfo {

    private final WebClient.Builder webClientBuilder;

    @HystrixCommand(fallbackMethod = "getFallbackPlaygroundDTO", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "30000"),
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "4"),
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50"),
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "60000") })
    public PlaygroundDTO getPlaygroundDTO(Reservation reservation) {
        PlaygroundDTO playgroundDTO = null;
        try{
            playgroundDTO = webClientBuilder.build()
                    .get()
                    .uri("http://localhost:8083/playgrounds/api/playground/" + reservation.getPlaygroundId())
                    .retrieve()
                    .bodyToMono(PlaygroundDTO.class)
                    .timeout(Duration.ofMillis(30000))
                    .block();
            if(playgroundDTO != null) log.info("PlaygroundDTO: {}", playgroundDTO.toString());
        } catch (Exception e){
            log.error(e.getMessage(), e);
            // In case of exceeding the time out or error, will be redirected to the below fallback method.
        }
        return playgroundDTO;
    }

    public PlaygroundDTO getFallbackPlaygroundDTO(Reservation reservation) {
        return new PlaygroundDTO(reservation.getId(), "playground-service is timed out or down or Error Occurred", "", "", "", "", new Date(), "");
    }
}
