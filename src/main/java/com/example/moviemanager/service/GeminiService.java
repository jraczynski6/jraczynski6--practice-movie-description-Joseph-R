package com.example.moviemanager.service;

import com.google.genai.Client;
import com.google.genai.types.GenerateContentResponse;
import org.springframework.stereotype.Service;

@Service
public class GeminiService {

    private final String apiKey = System.getenv("GOOGLE_API_KEY");
    private final Client client = Client.builder().apiKey(apiKey).build();

    public String generateDescription(String movieTitle) {
        try {
            if (apiKey == null || apiKey.isEmpty()) {
                return "Error: GOOGLE_API_KEY is not set.";
            }

            GenerateContentResponse response = client.models.generateContent(
                    "models/gemini-2.0-flash",
                    "Write a 2‑sentence captivating description for the movie: " + movieTitle,
                    null
            );

            return response.text();
        } catch (Exception e) {
            return "AI Error Detail: " + e.getMessage();
        }
    }
}
