package clothingStorage.ui;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import clothingStorage.core.Clothing;
import clothingStorage.core.Storage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class NewClothingPageControllerTest extends ApplicationTest {
    
    private NewClothingPageController controller;
    private Parent root;
    private Stage stage;
    private Storage storage;

    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        final FXMLLoader loader = new FXMLLoader(getClass().getResource("NewClothingPage.fxml"));
        root = loader.load();
        this.controller = loader.getController();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @BeforeEach
    public void setupClothingItems() {
        storage = new Storage();
        Clothing clothing1 = new Clothing("Jeans", "Nike", 'S', 10);
        Clothing clothing2 = new Clothing("Shorts", "Louis Vuitton", 'M', 20);
        Clothing clothing3 = new Clothing("Socks", "Adidas", 'L', 30);
        storage.addNewClothing(clothing1, 5);
        storage.addNewClothing(clothing2, 8);
        storage.addNewClothing(clothing3, 4);
        controller.setStorage(storage);
    }

    @Test
    public void testCancel() {
        clickOn("#cancel");
        assertEquals("Clothing Storage", this.stage.getTitle());
        clickOn("#newClothingItem");
        assertEquals("New Clothing", this.stage.getTitle());
    }

    @Test
    public void testNewClothingItem() {
        clickOn("#typeOfClothing").write("Jeans");
        clickOn("#brand").clickOn("Adidas");
        clickOn("#size").clickOn("S");
        clickOn("#price").write("150");
        clickOn("#quantity").write("5");
        clickOn("#ok");

        Clothing clothing = new Clothing("Jeans", "Adidas", 'S', 150);
        assertEquals("You successfully added the following: " + clothing.toString(), controller.getConfirmMessage());
    }

    @Test
    public void testErrorClothingItemAlreadyInStorage() {
        clickOn("#typeOfClothing").write("Jeans");
        clickOn("#brand").clickOn("Nike");
        clickOn("#size").clickOn("S");
        clickOn("#price").write("150");
        clickOn("#quantity").write("5");
        clickOn("#ok");
        assertEquals("This item is already in storage" , controller.getErrorMessage());
    }

    @Test
    public void testErrorNewClothingNoFields() {
        clickOn("#ok");
        assertEquals("Fill in all fields", controller.getErrorMessage());
    }

    @Test
    public void testErrorNewClothingWrongName() {
        clickOn("#typeOfClothing").write("jacket");
        clickOn("#brand").clickOn("Adidas");
        clickOn("#size").clickOn("L");
        clickOn("#price").write("159");
        clickOn("#quantity").write("8");
        clickOn("#ok");
        assertEquals("Name of clothing must start with uppercase letter", controller.getErrorMessage());
    }

    @Test
    public void testErrorNewClothingWrongNumber() {
        clickOn("#typeOfClothing").write("Jacket");
        clickOn("#brand").clickOn("Adidas");
        clickOn("#size").clickOn("L");
        clickOn("#price").write("hei");
        clickOn("#quantity").write("8");
        clickOn("#ok");
        assertEquals("Price must be a positive decimal number" +  "\n" 
            + "Quantity must a positive integer", controller.getErrorMessage());
    }
}