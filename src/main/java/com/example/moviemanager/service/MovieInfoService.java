package com.example.moviemanager.service;

import com.example.moviemanager.model.MovieInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.genai.Client;
import com.google.genai.types.GenerateContentResponse;
import com.google.genai.errors.ClientException;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class MovieInfoService {

    private final Client client;
    private final ObjectMapper objectMapper;

    public MovieInfoService() {
        this.client = new Client();
        this.objectMapper = new ObjectMapper();
    }

    public MovieInfo getMovieInfo(String movieTitle) throws IOException {
        String prompt =
                "You are a JSON API.\n" +
                        "Return ONLY a valid JSON object.\n" +
                        "Do NOT include markdown, backticks, comments, or explanations.\n" +
                        "The response MUST start with '{' and end with '}'.\n\n" +
                        "Movie title: \"" + movieTitle + "\"\n\n" +
                        "JSON schema:\n" +
                        "{\n" +
                        "  \"director\": \"string\",\n" +
                        "  \"description\": \"string\"\n" +
                        "}";

        try {
            GenerateContentResponse response = client.models.generateContent(
                    "gemini-2.0-flash-001",
                    prompt,
                    null
            );

            String raw = response.text();
            String json = extractJson(raw);
            return objectMapper.readValue(json, MovieInfo.class);

        } catch (ClientException e) {
            // Handles 429 or other API errors: return defaults
            System.out.println("AI request failed: " + e.getMessage());
            MovieInfo fallback = new MovieInfo();
            fallback.setDirector("Director unknown");
            fallback.setDescription("Description not available");
            return fallback;
        }
    }

    // response sanitizer
    private String extractJson(String text) {
        int start = text.indexOf('{');
        int end = text.lastIndexOf('}');

        if (start == -1 || end == -1 || end < start) {
            throw new RuntimeException("No valid JSON found in AI response");
        }

        return text.substring(start, end + 1);
    }
}
