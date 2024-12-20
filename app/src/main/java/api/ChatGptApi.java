package api;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ChatGptApi {

    @Headers({
            "Content-Type: application/json",
            "Authorization: Bearer YOUR_API_KEY"
    })
    @POST("v1/chat/completions")
    Call<ChatGptResponse> getChatCompletion(@Body ChatGptRequest request);
}
