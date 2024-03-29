package com.elgendy.invitationservice.client;

import com.elgendy.invitationservice.exception.ApplicationException;
import com.elgendy.invitationservice.model.Invitation;
import com.elgendy.invitationservice.model.dto.UserDTO;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;

@Log4j2
@AllArgsConstructor
@Service
public class GetUserInfoClient {

    private final WebClient.Builder webClientBuilder;
    private static final String SERVICE_BASE_URL = "http://user-service/playgrounds/api/user/";

    @HystrixCommand(fallbackMethod = "getFallbackUserDTO", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "30000"),
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "4"),
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50"),
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "60000") })
    public UserDTO getUserDTO(Invitation invitation) {
        UserDTO userDTO = null;
        try{
            userDTO = webClientBuilder.build()
                    .get()
                    .uri(SERVICE_BASE_URL + invitation.getUserId())
                    .retrieve()
                    .bodyToMono(UserDTO.class)
                    .timeout(Duration.ofMillis(30000))
                    .block();
            if (userDTO != null) log.info("User: {}", userDTO.toString());
        } catch (HttpStatusCodeException e){
            log.error(e.getMessage());
            throw new ApplicationException(e.getMessage(), e.getStatusCode(), e);
        } catch (Exception e){
            log.error(e.getMessage());
        }
        return userDTO;
    }

    public UserDTO getFallbackUserDTO(Invitation invitation) {
        return new UserDTO(invitation.getId(), "user-service is timed out or down or Error Occurred", "", "", "", "");
    }
}