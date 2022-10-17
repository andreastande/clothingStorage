package clothingStorage.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.matcher.control.LabeledMatchers;

import clothingStorage.core.Clothing;
import clothingStorage.core.Storage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

public class PricePageControllerTest extends ApplicationTest {
    
    private PricePageController controller;
    private Parent root;
    private Stage stage;
    private Storage storage;

    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        final FXMLLoader loader = new FXMLLoader(getClass().getResource("PricePage.fxml"));
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
    public void testStoragePageButton() {
        clickOn("#storagePageButton");
        assertEquals("Clothing Storage", this.stage.getTitle());
        clickOn("#pricePageButton");
        assertEquals("Clothing Prices", this.stage.getTitle());
    }

    @Test
    public void testNewPrice() {
        clickOn("#priceList");
        clickOn(LabeledMatchers.hasText("Shorts; Louis Vuitton; 20.0,-"));
        clickOn("#newPrice").write("30");
        clickOn("#confirmNewPrice");
        ListView<String> priceView = lookup("#priceList").query();
        List<String> priceList = priceView.getItems();
        String[] nikeJeans = priceList.get(1).split(";");
        double price = Double.parseDouble(nikeJeans[2].split(",")[0].strip());
        assertEquals(30, price);
    }

    @Test
    public void testDiscount() {
        clickOn("#priceList");
        clickOn(LabeledMatchers.hasText("Jeans; Nike; 10.0,-"));
        clickOn("#discount").write("50");
        clickOn("#confirmDiscount");
        ListView<String> priceView = lookup("#priceList").query();
        List<String> priceList = priceView.getItems();
        String[] nikeJeans = priceList.get(0).split(";");
        double price = Double.parseDouble(nikeJeans[2].split(",")[0].strip());
        assertEquals(5, price);
    }

    @Test
    public void testRemoveDiscount() {
        clickOn("#priceList");
        clickOn(LabeledMatchers.hasText("Jeans; Nike; 10.0,-"));
        clickOn("#discount").write("50");
        clickOn("#confirmDiscount");
        clickOn("#priceList");
        clickOn(LabeledMatchers.hasText("Jeans; Nike; 5.0,-"));
        clickOn("#removeDiscount");
        ListView<String> priceView = lookup("#priceList").query();
        List<String> priceList = priceView.getItems();
        String[] nikeJeans = priceList.get(0).split(";");
        double price = Double.parseDouble(nikeJeans[2].split(",")[0].strip());
        assertEquals(10, price);
    }

    @Test
    public void testErrorClothingNotOnDiscount() {
        clickOn("#priceList");
        clickOn(LabeledMatchers.hasText("Jeans; Nike; 10.0,-"));
        clickOn("#removeDiscount");
        assertEquals("Clothing is not on discount", controller.getErrorMessage());
    }

    @Test
    public void testErrorDiscountNotValid() {
        clickOn("#priceList");
        clickOn(LabeledMatchers.hasText("Jeans; Nike; 10.0,-"));
        clickOn("#discount").write("120");
        clickOn("#confirmDiscount");
        assertEquals("Given discount is not valid", controller.getErrorMessage());
        clickOn(LabeledMatchers.hasText("OK"));
    }

    @Test
    public void testErrorClothingAlreadyOnDiscount() {
        clickOn("#priceList");
        clickOn(LabeledMatchers.hasText("Jeans; Nike; 10.0,-"));
        clickOn("#discount").write("50");
        clickOn("#confirmDiscount");
        clickOn("#priceList");
        clickOn(LabeledMatchers.hasText("Jeans; Nike; 5.0,-"));
        clickOn("#discount").write("50");
        clickOn("#confirmDiscount");
        assertEquals("Clothing is already on discount", controller.getErrorMessage());
        ListView<String> priceView = lookup("#priceList").query();
        List<String> priceList = priceView.getItems();
        String[] nikeJeans = priceList.get(0).split(";");
        double price = Double.parseDouble(nikeJeans[2].split(",")[0].strip());
        assertEquals(5, price);
    }

    @Test
    public void testErrorNoItemSelectedForDiscount() {
        clickOn("#discount").write("50");
        clickOn("#confirmDiscount");
        assertEquals("You need to select an item from the list", controller.getErrorMessage());
        clickOn(LabeledMatchers.hasText("OK"));
        clickOn("#removeDiscount");
        assertEquals("You need to select an item from the list", controller.getErrorMessage());
    }

    @Test
    public void testErrorNoItemSelectedForPriceChange() {
        clickOn("#newPrice").write("30");
        clickOn("#confirmNewPrice");
        assertEquals("You need to select an item from the list", controller.getErrorMessage());        
    }

    @Test
    public void testErrorNotSpecifyPrice() {
        clickOn("#priceList").clickOn(LabeledMatchers.hasText("Jeans; Nike; 10.0,-"));
        clickOn("#confirmNewPrice");
        assertEquals("Specify price first in textfield", controller.getErrorMessage());
        clickOn(LabeledMatchers.hasText("OK"));
        clickOn("#confirmDiscount");
        assertEquals("Specify price first in textfield", controller.getErrorMessage());
        clickOn(LabeledMatchers.hasText("OK"));
    }

    @Test
    public void testErrorInputNotNumberForPrice() {
        clickOn("#newPrice").write("hei");
        clickOn("#priceList").clickOn(LabeledMatchers.hasText("Jeans; Nike; 10.0,-"));
        clickOn("#confirmNewPrice");
        assertEquals("Input must be a number", controller.getErrorMessage());
        clickOn(LabeledMatchers.hasText("OK"));
        clickOn("#confirmDiscount");
        assertEquals("Input must be a number", controller.getErrorMessage());
        clickOn(LabeledMatchers.hasText("OK"));
    }
    
}