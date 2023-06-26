package org.openmrs.module.clinicalsummary.api.util;

import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;


public class EmailClientUtil {

    public static void sendEmail(String url, String recipient, String body, String subject, String module ) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(StandardCharsets.UTF_8));

        String escapedStackTrace = StringEscapeUtils.escapeJson(body);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String jsonBody = String.format(
                "{%n" +
                        "  \"to\": \"%s\",%n" +
                        "  \"body\": \"%s\",%n" +
                        "  \"subject\": \"%s\",%n" +
                        "  \"module\": \"%s\"%n" +
                        "}", recipient, escapedStackTrace, subject, module
        );

        HttpEntity<String> requestEntity = new HttpEntity<>(jsonBody, headers);

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, requestEntity, String.class);

        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            System.out.println("Email sent successfully.");
        } else {
            System.out.println("Failed to send email. Response code: " + responseEntity.getStatusCode());
        }
    }
}