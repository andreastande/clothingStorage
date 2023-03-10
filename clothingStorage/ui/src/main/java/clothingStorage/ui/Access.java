package clothingStorage.ui;

import clothingStorage.core.Clothing;
import clothingStorage.core.Storage;
import java.util.List;

/**
 * Interface used by RemoteAccess and DirectAccess.
 */
public interface Access {

    public Storage getStorage();

    public Clothing getClothing(String name);

    public boolean addClothing(Clothing clothing, int quantity);

    public boolean removeClothing(String name);
    
    public boolean increaseQuantityByOne(String name);

    public boolean decreaseQuantityByOne(String name);

    public boolean increaseQuantity(String name, int quantity);

    public boolean decreaseQuantity(String name, int quantity);

    public List<String> getStorageDisplay();

    public List<String> getPriceDisplay();

    public List<String> getSortedPriceDisplay(int id);

    public List<String> getSortedTypePriceDisplay(String type);

    public List<String> getSortedBrandPriceDisplay(String brand);

    public List<String> getSortedNames();

    public List<String> getPricePageNames();

    public List<String> getStoragePageNames();

    public boolean updatePrice(Clothing clothing, double price);

    public boolean updateDiscount(Clothing clothing, double discount);

    public boolean removeDiscount(Clothing clothing);

    public List<Integer> getQuantitiesForTypeAndSizes(String type);

    public double getTotalValue();

    public int getTotalQuantity();

}
