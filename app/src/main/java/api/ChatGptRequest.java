package api;

import java.util.List;

public class ChatGptRequest {
    private String model;
    private List<Message> messages;

    public ChatGptRequest(String model, List<Message> messages) {
        this.model = model;
        this.messages = messages;
    }

    // Getters and Setters (Optional)
}
