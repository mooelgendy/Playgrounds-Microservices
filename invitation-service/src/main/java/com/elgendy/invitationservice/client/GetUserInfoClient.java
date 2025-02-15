package com.elgendy.invitationservice.client;

import com.elgendy.invitationservice.exception.ApplicationException;
import com.elgendy.invitationservice.model.Invitation;
import com.elgendy.invitationservice.model.dto.UserDTO;
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
public class GetUserInfoClient {

    private final WebClient.Builder webClientBuilder;
    private static final String SERVICE_BASE_URL = "http://user-service/playgrounds/api/user/";
    private static final int TIMEOUT_MILLIS = 30000;
    private static final String FALLBACK_ERROR_MESSAGE = "user-service is timed out or down or error occurred";

    @CircuitBreaker(name = "userService", fallbackMethod = "getFallbackUserDTO")
    public UserDTO getUserDTO(Invitation invitation) {
        UserDTO userDTO = null;
        try{
            userDTO = webClientBuilder.build()
                    .get()
                    .uri(SERVICE_BASE_URL + invitation.getUserId())
                    .retrieve()
                    .bodyToMono(UserDTO.class)
                    .timeout(Duration.ofMillis(TIMEOUT_MILLIS))
                    .block();
            if (userDTO != null) log.info("User: {}", userDTO.toString());
        } catch (HttpStatusCodeException e){
            log.error(e.getMessage());
            throw new ApplicationException(e.getMessage(), e.getStatusCode(), e, new Date());
        } catch (Exception e){
            log.error(e.getMessage());
        }
        return userDTO;
    }

    public UserDTO getFallbackUserDTO(Invitation invitation, Exception ex) {
        log.warn("Fallback method called for user {} due to: {}", 
                invitation.getUserId(), ex.getMessage());
        return new UserDTO(invitation.getId(), FALLBACK_ERROR_MESSAGE, "", "", "", "");
    }
}