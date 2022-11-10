package clothingStorage.client;

import clothingStorage.core.Clothing;
import clothingStorage.json.ClothingStoragePersistence;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;



/**
 * The LogClient.
 */
public class LogClient {
    /* Skriv metoder vi trenger
     * get
     * getAsync
     * post
     * postAsync
     * getClothing
     * getStorage
     * removeClothing
     * addClothing
     * updateClothing
     */

    /**
     * URI base path.
     */
    private final URI endpointBaseUri;

    /**
     * Application json for requests.
     */
    private static final String APPLICATION_JSON = "application/json";

    /**
     * Accept header for requests.
     */
    private static final String ACCEPT_HEADER = "Accept";

    /**
     * Content-type header for requests.
     */
    private static final String CONTENT_TYPE_HEADER = "Content-Type";

    /**
     * Objectmapper.
     */
    private ObjectMapper objectMapper;


    /**
     * Constructs a LogClient from a builder.
     *
     * @throws URISyntaxException if string could not be parsed to URI
     */
    public LogClient() throws URISyntaxException {
        this.endpointBaseUri = new URI("http://localhost:8080/clothingStorage");
        objectMapper = ClothingStoragePersistence.createObjectMapper();
    }

    /**
     * gets Clothing from restserver.
     *
     * @param name of clothing to be retrieved
     * @return Clothing item
     */
    public Clothing getClothing(final String name) {
        Clothing clothing;
        HttpRequest request = HttpRequest.newBuilder(endpointBaseUri.resolve("/clothes/" + name))
            .header(ACCEPT_HEADER, APPLICATION_JSON)
            .GET()
            .build();
        try {
            final HttpResponse<String> response = 
                HttpClient.newBuilder().build().send(request, HttpResponse.BodyHandlers.ofString());
            clothing = objectMapper.readValue(response.body(), Clothing.class);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        return clothing;
    }

    /**
     * Removes Clothing from restserver.
     *
     * @param clothing to be removed
     * @return boolean true if success, false if not
     */
    public boolean removeClothing(Clothing clothing) {
        Boolean removed;
        try {
            HttpRequest request = HttpRequest.newBuilder(endpointBaseUri
                .resolve("/clothes/" + clothing.getName()))
                .header(ACCEPT_HEADER, APPLICATION_JSON)
                .DELETE()
                .build();
            final HttpResponse<String> response =
                HttpClient.newBuilder().build().send(request, HttpResponse.BodyHandlers.ofString());
            removed = objectMapper.readValue(response.body(), Boolean.class);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        return removed;
    }

    /**
     * Puts Clothing to restserver.
     *
     * @param clothing to be put
     * @return boolean true if put, false if not
     */
    public boolean putClothing(Clothing clothing) throws JsonProcessingException {
        Boolean updated;
        String json = objectMapper.writeValueAsString(clothing);
        
        HttpRequest request = HttpRequest.newBuilder(endpointBaseUri
            .resolve("/clothes/" + clothing.getName()))
            .header(ACCEPT_HEADER, APPLICATION_JSON)
            .header(CONTENT_TYPE_HEADER, APPLICATION_JSON)
            .PUT(BodyPublishers.ofString(json))
            .build();
        try {
            final HttpResponse<String> response =
                HttpClient.newBuilder().build().send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("hei2");
            System.out.println(response.body());
            updated = objectMapper.readValue(response.body(), Boolean.class);

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        return updated;
    }

    /**
     * Gets quantity from restserver.
     *
     * @param name to be retrieved
     * @return quantity from restserver
     */
    public int getQuantity(final String name) {
        int quantity;
        HttpRequest request = HttpRequest.newBuilder(endpointBaseUri
            .resolve("/quantity/" + name))
            .header(ACCEPT_HEADER, APPLICATION_JSON)
            .GET()
            .build();
        try {
            final HttpResponse<String> response = 
                HttpClient.newBuilder().build().send(request, HttpResponse.BodyHandlers.ofString());
            quantity = objectMapper.readValue(response.body(), Integer.class);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        return quantity;
    }

    /**
     * Puts quantity to restserver.
     *
     * @param clothing corresponding to quantity
     * @param quantity to be updated
     * @return boolean true if success, false if not
     */
    public boolean putQuantityOfClothing(Clothing clothing, int quantity) {
        Boolean updated;
        String json = "{quantity:" + quantity + "}";

        HttpRequest request = HttpRequest.newBuilder(endpointBaseUri
            .resolve("/quantity/" + clothing.getName()))
            .header(ACCEPT_HEADER, APPLICATION_JSON)
            .header(CONTENT_TYPE_HEADER, APPLICATION_JSON)
            .PUT(BodyPublishers.ofString(json))
            .build();
        try {
            final HttpResponse<String> response =
                HttpClient.newBuilder().build().send(request, HttpResponse.BodyHandlers.ofString());
            updated = objectMapper.readValue(response.body(), Boolean.class);

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        return updated;
    }

    /**
     * Removes quantity from restserver.
     *
     * @param clothing corresponding to quantity
     * @return boolean true if success, false if not
     */
    public boolean removeQuantity(Clothing clothing) {
        Boolean removed;
        try {
            HttpRequest request = HttpRequest.newBuilder(endpointBaseUri
                .resolve("/quantity/" + clothing.getName()))
                .header(ACCEPT_HEADER, APPLICATION_JSON)
                .DELETE()
                .build();
            final HttpResponse<String> response =
                HttpClient.newBuilder().build().send(request, HttpResponse.BodyHandlers.ofString());
            removed = objectMapper.readValue(response.body(), Boolean.class);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        return removed;
    }

    public static void main(String[] args) throws URISyntaxException, JsonProcessingException {
        Clothing clothing = new Clothing("Pants", "Nike", 'M', 43);
        LogClient logClient = new LogClient();
        logClient.putQuantityOfClothing(clothing, 5);
    }

}
/*
http:/localhost:8080/clothingStorage
http:/localhost:8080/clothingStorage/clothes/name
http:/localhost:8080/clothingStorage/quantities/name

Storage storage = client.getStorage();
storage.increaseQuantity(Clothing clothing);
client.putStorage();
*/