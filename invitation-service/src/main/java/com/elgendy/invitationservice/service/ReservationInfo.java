package com.elgendy.invitationservice.service;

import com.elgendy.invitationservice.model.Invitation;
import com.elgendy.invitationservice.model.dto.ReservationDTO;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;
import java.util.Date;

@Service
public class ReservationInfo {

    private static Logger LOGGER = LoggerFactory.getLogger(ReservationInfo.class);

    @Autowired
    private WebClient.Builder webClientBuilder;

    @HystrixCommand(fallbackMethod = "getFallbackReservationDTO", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "30000"),
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "4"),
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50"),
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "60000") })
    public ReservationDTO getReservationDTO(Invitation invitation) {
        ReservationDTO reservationDTO = null;
        try{
            reservationDTO = webClientBuilder.build()
                    .get()
                    .uri("http://reservation-service/playgrounds/api/reservation/" + invitation.getReservationId())
                    .retrieve()
                    .bodyToMono(ReservationDTO.class)
                    .timeout(Duration.ofMillis(30000))
                    .block();
            if (reservationDTO != null) LOGGER.info("Reservation: {}", reservationDTO.toString());
        } catch (Exception e){
            LOGGER.error(e.getMessage(), e);
            // In case of exceeding the time out or error, will be redirected to the below fallback method.
        }
        return reservationDTO;
    }

    public ReservationDTO getFallbackReservationDTO(Invitation invitation) {
        return new ReservationDTO(invitation.getId(), "reservation-service is timed out or down or error occurred", new Date(), "", "");
    }
}