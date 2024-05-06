package com.beterraba.lol.adapters.out;

import com.beterraba.lol.domain.ports.GenerativeAiService ;
import feign.FeignException;
import feign.RequestInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Optional;


@FeignClient(name = "openAiChatApi",url = "${openai.url}",configuration = OpenAiChatService.Config.class)
public interface OpenAiChatService extends GenerativeAiService  {

    @PostMapping("/v1/chat/completions")
    OpenAiResponse chatCompletion(OpenAiRequest request);


    @Override
    default String generateContent(String objective, String context) {
        String model = "gpt-3.5-turbo";
        List<Message> messages = List.of(
                new Message("system", objective),
                new Message("user", context)
        );
        OpenAiRequest request = new OpenAiRequest(model,messages);

        OpenAiResponse response = chatCompletion(request);
        try {
            Optional<Choice> firstResponse = response.choices().stream().findFirst();
            if (firstResponse.isEmpty()) return "No response.Try again!";
            return firstResponse.get().message().content();

        } catch (FeignException httpError) {
            return "Something went wrong! Please, try again.";
        } catch (Exception exception) {
            return "Something went wrong! Contact support.";
        }
    }

    record Message(String role,String content){}

    record OpenAiRequest(String model, List<Message> messages){}

    record Choice(Message message){}
    record OpenAiResponse(List<Choice> choices) {}

    class Config {
        @Bean
        public RequestInterceptor apiKeyRequestInterceptor(@Value("${openai.api-key}") String apiKey){
            return requestTemplate -> requestTemplate.header(HttpHeaders.AUTHORIZATION, "Bearer %s".formatted(apiKey));
        }
    }
}
