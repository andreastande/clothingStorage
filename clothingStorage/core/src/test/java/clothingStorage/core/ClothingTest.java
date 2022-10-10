package clothingStorage.core;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

public class ClothingTest{

    Clothing clothing;

    @BeforeEach
    public void setUp() {
        clothing = new Clothing("Jeans", "Nike", 'M', 150);
    }
    
    @Test
    public void checkConstructor() {
        Assertions.assertEquals("Jeans", clothing.getName());
        Assertions.assertEquals("Nike", clothing.getBrand());
        Assertions.assertEquals('M', clothing.getSize());
        Assertions.assertEquals(150, clothing.getPrice());
    }

    @Test
    public void testName() {
        assertThrows(IllegalArgumentException.class, () -> {
            clothing.setName("J3sper");
        }, "Threw IllegalArgumentException due to number in name");
        clothing.setName("Jesper");
        assertThrows(IllegalArgumentException.class, () -> {
            clothing.setName("jesper");
        }, "Threw IllegalArgumentException since input doesn't start with uppercase letter");

        clothing.setName("Jesper");
        assertEquals("Jesper", clothing.getName());
    }

    @Test
    public void testBrand() {
        assertThrows(IllegalArgumentException.class, () -> {
            clothing.setBrand("Gucci");
        }, "Threw IllegalArgumentException due to input not being a valid brand");
        assertThrows(IllegalArgumentException.class, () -> {
            clothing.setBrand("nike");
        }, "Threw IllegalArgumentException due to incorrect spelling");

        clothing.setBrand("Nike");
        assertEquals("Nike", clothing.getBrand());
    }

    @Test
    public void testSize() {
        assertThrows(IllegalArgumentException.class, () -> {
            clothing.setSize('X');
        }, "Threw IllegalArgumentException since it is not a size in the store");
        assertThrows(IllegalArgumentException.class, () -> {
            clothing.setSize('s');
        }, "Threw IllegalArgumentException due to incorrect spelling");

        clothing.setSize('S');
        assertEquals('S', clothing.getSize());
    }

    @Test
    public void testPrice() {
        assertThrows(IllegalArgumentException.class, () -> {
            clothing.setPrice(-10, false);
        }, "Threw IllegalArgumentException since price can't be negative");
        assertThrows(IllegalArgumentException.class, () -> {
            clothing.setPrice(0, false);
        }, "Threw IllegalArgumentException because price can't be zero");

        clothing.setPrice(49, false);
        assertEquals(49, clothing.getPrice());
        clothing.setPrice(30.55, false);
        assertEquals(30.55, clothing.getPrice());
    }

    @Test
    public void testDiscount() {
        assertThrows(IllegalArgumentException.class, () -> {
            clothing.setDiscount(105);
        }, "Threw IllegalArgumentException since discount can't be over 100");
        assertThrows(IllegalArgumentException.class, () -> {
            clothing.setDiscount(100);
        }, "Threw IllegalArgumentException since discount can't be 100");
        assertThrows(IllegalArgumentException.class, () -> {
            clothing.setDiscount(0);
        }, "Threw IllegalArgumentException since discount can't be 0");

        clothing.setDiscount(0.50);
        assertEquals(75, clothing.getPrice());
        clothing.setPrice(150, true);
        clothing.setDiscount(0.90);
        assertEquals(15, clothing.getPrice());
        clothing.setPrice(150, true);
        clothing.setDiscount(0.375);
        assertEquals(93.75, clothing.getPrice());
    }

    @Test
    public void testOnSale() {
        assertEquals(0, clothing.getDiscount());
        assertFalse(clothing.isOnSale());

        clothing.setDiscount(0.5);
        assertEquals(0.5, clothing.getDiscount());
        assertTrue(clothing.isOnSale());

        clothing.setSale(0);
        assertEquals(0, clothing.getDiscount());
        
        assertThrows(IllegalArgumentException.class, () -> {
            clothing.setSale(2);
        }, "Threw IllegalArgumentException since input was not 0 or 1");
    }

    @Test
    public void testToString() {
        String output = "Jeans" + "\n" + "   - Brand: Nike" + "\n" + "   - Size: M" + "\n" + "   - Price: 150.0,-";
        assertEquals(output, clothing.toString());
        clothing.setSize('S');
        output = "Jeans" + "\n" + "   - Brand: Nike" + "\n" + "   - Size: S" + "\n" + "   - Price: 150.0,-";
        assertEquals(output, clothing.toString());
    }
}

