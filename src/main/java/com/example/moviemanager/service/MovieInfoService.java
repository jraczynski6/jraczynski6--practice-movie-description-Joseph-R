package com.example.moviemanager.service;

import com.example.moviemanager.model.MovieInfo;
import com.google.genai.Client;
import com.google.genai.types.GenerateContentResponse;
import org.apache.http.HttpException;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class MovieInfoService {
    private Client client;

    public MovieInfoService() {
        this.client = new Client();
    }

    public MovieInfo getMovieInfo(String movieTitle) throws HttpException, IOException {
        String prompt = "For the movie '" + movieTitle + "', provide EXACTLY this format:\n" +
                "DIRECTOR: [just the director's name]\n" +
                "DESCRIPTION: [one sentence description]\n\n" +
                "Do not include anything else.";

        GenerateContentResponse response = client.models.generateContent(
                "gemini-2.0-flash-001",
                prompt,
                null
        );

        String rawResponse = response.text().trim();
        return parseResponse(rawResponse);
    }

    private MovieInfo parseResponse(String response) {
        String director = "";
        String description = "";

        String[] lines = response.split("\n");

        for (String line : lines) {
            line = line.trim();

            if (line.startsWith("DIRECTOR:")) {
                director = line.replace("DIRECTOR:", "").trim();
            }
            else if (line.startsWith("DESCRIPTION:")) {
                description = line.replace("DESCRIPTION:", "").trim();
            }
        }

        return new MovieInfo(director, description);
    }
}
