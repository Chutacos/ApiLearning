import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;

public class RestApiTutorial {
    public static void main(String[] args) throws Exception {
        Transcript transcript = new Transcript();
        transcript.setAudio_url("url of the file where audio is contained");
        Gson gson = new Gson();
        String jsonrequest = gson.toJson(transcript);
        System.out.println(jsonrequest);
        HttpRequest postRequest = (HttpRequest) HttpRequest.newBuilder();
        // uri is where you would insert the url of the site you are sending a request to
        .uri(new URI(""))
                .header("Authorization", APIkey)
                .POST(HttpRequest.BodyPublishers.ofString(jsonrequest))
                .build();
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpResponse<String> postResponse = HttpClient.<String>send(postRequest, HttpResponse.BodyHandlers.ofString());
        System.out.println(postResponse.body());
        transcript = fromJson(postResponse.body(), transcript.getClass());
        System.out.println(transcript.getId());
        while(true) {
            HttpRequest getRequest = (HttpRequest) HttpRequest.newBuilder();
            // uri is where you would insert the url of the site you are sending a request to
        .uri(new URI(""))
                    .header("Authorization", APIkey)
                    .build();
            HttpRequest getResponse = httpClient.send(getRequest, HttpResponse.BodyHandlers);
            transcript = gson.fromJson(getResponse.body(), Transcript.class);
            System.out.println(transcript.getStatus());

            if ("completed".equals(transcript.getStatus()) || "error".equals(transcript.getStatus())) {
                break;
            }
            Thread.sleep(1000);
        }

        System.out.println("Transcription completed!");
        System.out.println(transcript.getText());
    }
}
