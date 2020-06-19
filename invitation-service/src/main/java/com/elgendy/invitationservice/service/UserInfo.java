package com.elgendy.invitationservice.service;

import com.elgendy.invitationservice.model.Invitation;
import com.elgendy.invitationservice.model.dto.UserDTO;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;

@Service
public class UserInfo {

    private static Logger LOGGER = LoggerFactory.getLogger(UserInfo.class);

    @Autowired
    private WebClient.Builder webClientBuilder;

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
                    .uri("http://user-service/playgrounds/api/user/" + invitation.getUserId())
                    .retrieve()
                    .bodyToMono(UserDTO.class)
                    .timeout(Duration.ofMillis(30000))
                    .block();
            if (userDTO != null) LOGGER.info("User: {}", userDTO.toString());
        } catch (Exception e){
            LOGGER.error(e.getMessage(), e);
            // In case of exceeding the time out or error, will be redirected to the below fallback method.
        }
        return userDTO;
    }

    public UserDTO getFallbackUserDTO(Invitation invitation) {
        return new UserDTO(invitation.getId(), "user-service is timed out or down or Error Occurred", "", "", "", "");
    }
}