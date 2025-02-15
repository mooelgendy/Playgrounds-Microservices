package com.elgendy.invitationservice.client;

import com.elgendy.invitationservice.exception.ApplicationException;
import com.elgendy.invitationservice.model.Invitation;
import com.elgendy.invitationservice.model.dto.ReservationDTO;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
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
    private static final int TIMEOUT_MILLIS = 30000;
    private static final String FALLBACK_ERROR_MESSAGE = "reservation-service is timed out or down or error occurred";

    @CircuitBreaker(name = "reservationService", fallbackMethod = "getFallbackReservationDTO")
    public ReservationDTO getReservationDTO(Invitation invitation) {
        ReservationDTO reservationDTO = null;
        try{
            reservationDTO = webClientBuilder.build()
                    .get()
                    .uri(SERVICE_BASE_URL + invitation.getReservationId())
                    .retrieve()
                    .bodyToMono(ReservationDTO.class)
                    .timeout(Duration.ofMillis(TIMEOUT_MILLIS))
                    .block();
        } catch (HttpStatusCodeException e){
            log.error(e.getMessage());
            throw new ApplicationException(e.getMessage(), e.getStatusCode(), e, new Date());
        } catch (Exception e){
            log.error(e.getMessage());
        }
        return reservationDTO;
    }

    public ReservationDTO getFallbackReservationDTO(Invitation invitation, Exception ex) {
        log.warn("Fallback method called for reservation {} due to: {}",
                invitation.getReservationId(), ex.getMessage());
        return new ReservationDTO(invitation.getId(), FALLBACK_ERROR_MESSAGE, new Date(), "", "");
    }
}