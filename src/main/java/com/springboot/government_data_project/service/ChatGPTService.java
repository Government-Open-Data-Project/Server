package com.springboot.government_data_project.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.government_data_project.dto.*;
import com.sun.tools.javac.Main;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Properties;

@Slf4j
@Service
public class ChatGPTService {

    @Value("${openai.secret-key}")
    private String apiKey;
    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    public ChatGPTService() {
        this.httpClient = HttpClient.newHttpClient();
        this.objectMapper = new ObjectMapper();
    }
    public ChatGPTService(String apiKey) {
        this.apiKey = apiKey;
        this.httpClient = HttpClient.newHttpClient();
        this.objectMapper = new ObjectMapper();
    }

    public void run() {

        //LOAD YOUR API KEY
        Properties properties = new Properties();

        try (InputStream input = Main.class.getClassLoader().getResourceAsStream("application.properties")) {
            if (input == null) {
                System.out.println("Sorry, unable to find application.properties");
                return;
            }
            properties.load(input);

            ChatGPTService client = new ChatGPTService(apiKey);
            AssistantResponseDTO assistantResponse = client.createAssistant("You are an expert in geography, be helpful and concise.");
            ThreadResponseDTO threadResponse = client.createThread();
            MessageResponseDTO messageResponse = client.sendMessage(threadResponse.id(), "user", "What is the capital of Italy?");
            log.info("messegeResponse: " + messageResponse.content().toString());

            client.runMessage(threadResponse.id(), assistantResponse.id());
            //Retrieve and handle responses
            MessagesListResponseDTO response = client.getMessages(threadResponse.id());
            log.info("response: " + response.toString());

        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String post(String url, Object body) throws Exception {
        String jsonBody = objectMapper.writeValueAsString(body);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Authorization", "Bearer " + apiKey)
                .header("Content-Type", "application/json")
                .header("OpenAI-Beta", "assistants=v1")  // Add this line
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

    public AssistantResponseDTO createAssistant(String initialPrompt) throws Exception {
        String url = "https://api.openai.com/v1/assistants";
        AssistantRequestDTO dto = new AssistantRequestDTO("gpt-4", initialPrompt);
        String response = post(url, dto);
        return objectMapper.readValue(response, AssistantResponseDTO.class);
    }

    public ThreadResponseDTO createThread() throws Exception {
        String url = "https://api.openai.com/v1/threads";

        String response = post(url, "");
        return objectMapper.readValue(response, ThreadResponseDTO.class);
    }

    public MessageResponseDTO sendMessage(String threadId, String role, String content) throws Exception {
        String url = "https://api.openai.com/v1/threads/" + threadId + "/messages";
        MessageDTO dto = new MessageDTO(role, content);

        String response = post(url, dto);
        return objectMapper.readValue(response, MessageResponseDTO.class);
    }

    public RunResponseDTO runMessage(String threadId, String assistantId) throws Exception {
        String url = "https://api.openai.com/v1/threads/" + threadId + "/runs";
        RunRequestDTO dto = new RunRequestDTO(assistantId);

        String response = post(url, dto);
        return objectMapper.readValue(response, RunResponseDTO.class);
    }

    public MessagesListResponseDTO getMessages(String threadId) throws Exception {
        String url = "https://api.openai.com/v1/threads/" + threadId + "/messages";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Authorization", "Bearer " + apiKey)
                .header("OpenAI-Beta", "assistants=v1")
                .header("Content-Type", "application/json")
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        log.info("message list response = " + response.toString());

        return objectMapper.readValue(response.body(), MessagesListResponseDTO.class);
        // Assuming the response is a JSON array of MessageResponseDTO
        //return objectMapper.readValue(response.body(), new TypeReference<List<MessageResponseDTO>>() {});
    }

}
