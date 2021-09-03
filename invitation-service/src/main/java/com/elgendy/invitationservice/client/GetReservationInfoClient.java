package com.elgendy.invitationservice.client;

import com.elgendy.invitationservice.exception.ApplicationException;
import com.elgendy.invitationservice.model.Invitation;
import com.elgendy.invitationservice.model.dto.ReservationDTO;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;
import java.util.Date;

@Log4j2
@AllArgsConstructor
@Service
public class GetReservationInfoClient {

    private final WebClient.Builder webClientBuilder;
    private static final String SERVICE_BASE_URL = "http://reservation-service/playgrounds/api/reservation/";

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
                    .uri(SERVICE_BASE_URL + invitation.getReservationId())
                    .retrieve()
                    .bodyToMono(ReservationDTO.class)
                    .timeout(Duration.ofMillis(30000))
                    .block();
        } catch (HttpStatusCodeException e){
            log.error(e.getMessage());
            throw new ApplicationException(e.getMessage(), e.getStatusCode(), e);
        } catch (Exception e){
            log.error(e.getMessage());
        }
        return reservationDTO;
    }

    public ReservationDTO getFallbackReservationDTO(Invitation invitation) {
        return new ReservationDTO(invitation.getId(), "reservation-service is timed out or down or error occurred", new Date(), "", "");
    }
}