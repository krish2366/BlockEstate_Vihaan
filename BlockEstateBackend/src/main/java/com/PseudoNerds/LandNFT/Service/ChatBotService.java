package com.PseudoNerds.LandNFT.Service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class ChatBotService {

    @Value("${gemini.api.key}")
    private String apiKey;

    @Value("${gemini.api.url}")
    private String apiUrl;

    public String chat(String message) {
        try {
            // Create the URL
            URL url = new URL(apiUrl + "?key=" + apiKey);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            // Prepare the JSON request
            String prompt = "You are a helpful assistant for land registry and NFT transfers in India. Speak in Hindi or English based on user's input. User: " + message;
            String jsonRequestBody = String.format("{\"contents\":[{\"parts\":[{\"text\":\"%s\"}]}]}", prompt);

            // Send the request
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonRequestBody.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            // Get the response
            try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"))) {
                String inputLine;
                StringBuilder response = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                return response.toString(); // Return the full response as a string
            }

        } catch (IOException e) {
            e.printStackTrace();
            return "Error communicating with Gemini API.";
        }
    }
}