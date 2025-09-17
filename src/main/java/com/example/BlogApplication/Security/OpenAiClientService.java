package com.example.BlogApplication.Security;


import com.example.BlogApplication.Dto.OpenAiRequestDto;
import com.example.BlogApplication.Dto.OpenAiResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.time.Duration;
import java.util.List;



@Component
@Slf4j
public class OpenAiClientService {

    private final WebClient webClient;


    public OpenAiClientService(@Value("${openai.api.key}") String apikey) {
        this.webClient = WebClient.builder()
                .baseUrl("https://api.openai.com/v1")
                .defaultHeader("Authorization", "Bearer " + apikey)
                .build();

    }


    public String ClassifyCategory(String blogText) {

        String prompt="""
            You are a blog post categorization system.
            Assign exactly ONE category to the blog.
            Available categories: Technology, Travel, Food, Lifestyle, Education, Health & Fitness, Business, Uncategorized.

            Rules:
            - If the blog covers technology applied to another domain (like AI in healthcare), prioritize the domain.
            - If no category fits, return "Uncategorized".
            - Reply ONLY with the category name.

            Blog: %s
        """.formatted(blogText);

         try {
             OpenAiRequestDto request = new OpenAiRequestDto("gpt-4o-mini", List.of(new OpenAiRequestDto.Message("user", prompt)),
                     10);

             OpenAiResponseDto response = webClient.post()
                     .uri("/chat/completions")
                     .bodyValue(request)
                     .retrieve()
                     .bodyToMono(OpenAiResponseDto.class)
                     .timeout(Duration.ofSeconds(5))
                     .block();

             if (response != null && response.getChoices() != null && !response.getChoices().isEmpty()
                     && response.getChoices().get(0).getMessage() != null
                     && response.getChoices().get(0).getMessage().getContent() != null
                     && !response.getChoices().get(0).getMessage().getContent().isBlank()) {

                 String category = response.getChoices().get(0).getMessage().getContent().trim();
                 log.info("OpenAi classified blog category : {}", category);
                 return category;  // return the actual category
             } else {
                 log.warn("OpenAi returned null or empty response for blog {}", blogText);
                 return "Uncategorized";
             }
         }
         catch (WebClientResponseException ex)
         {
             log.error("OpenAi Api HTTP error : {} - {}",ex.getStatusCode(),ex.getResponseBodyAsString());
         }
         catch (Exception e)
         {
             log.error("Error calling OpenAi API for blog ; {}",blogText,e);
         }
         return "Uncategorized";

    }


}
