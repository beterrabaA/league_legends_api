package com.beterraba.lol.adapters.out;

import com.beterraba.lol.domain.ports.GenerativeAiService;
import feign.FeignException;
import feign.RequestInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Optional;

@ConditionalOnProperty(name = "generative-ai.provider", havingValue = "GEMINI")
@FeignClient(name = "geminiChatApi",url = "${gemini.url}",configuration = OpenAiChatService.Config.class)
public interface GeminiChatService extends GenerativeAiService {

    @PostMapping("/v1beta/models/gemini-pro:generateContent")
    GoogleGeminiResp textOnlyInput(GoogleGeminiReq req);

    @Override
    default String generateContent(String objective, String context) {
        String prompt = """
                %s
                %s
                """.formatted(objective, context);

        GoogleGeminiReq req = new GoogleGeminiReq(
                List.of(new Content(List.of(new Part(prompt))))
        );
        try {
            GoogleGeminiResp resp = textOnlyInput(req);
            Optional<Candidate> firstResponse = resp.candidates().stream().findFirst();
            if (firstResponse.isEmpty()) return "Data not founded!";
            Optional<Part> firstPartList = firstResponse.get().content().parts().stream().findFirst();
            if(firstPartList.isEmpty()) return "Part not founded!";
            return firstPartList.get().text();
        } catch (FeignException httpError) {
            return "Something went wrong! Please, try again.";
        } catch (Exception exception) {
            return "Something went wrong! Contact support.";
        }
    }

    record GoogleGeminiReq(List<Content> contents) { }
    record Content(List<Part> parts) { }
    record Part(String text) { }
    record GoogleGeminiResp(List<Candidate> candidates) { }
    record Candidate(Content content) { }

    class Config {
        @Bean
        public RequestInterceptor apiKeyRequestInterceptor(@Value("${gemini.api-key}") String apiKey) {
            return requestTemplate -> requestTemplate.query("key", apiKey);
        }
    }
}
