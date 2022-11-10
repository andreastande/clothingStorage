package clothingStorage.restserver;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * The clothingStorageApplication.
 */
@SpringBootApplication
public class ClothingStorageApplication {
    

    /**
     * Starts spring server.
     *
     * @param args launched by string
     */
    public static void main(String[] args) {
        SpringApplication.run(ClothingStorageApplication.class, args);
    }

    /**
     * Required for checkstyle to pass. Prevents springboot from breaking
     */
    public void dummy() {}
}
