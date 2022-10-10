package clothingStorage.core;

import java.io.File;
import java.io.FileNotFoundException;

public interface IFiles {
    public void writeToFile(String filename, Storage storage) throws FileNotFoundException;
    public Storage readFromFile(String filename) throws FileNotFoundException;
    public File getFile(String filename);
    public String readAsString(String filename) throws FileNotFoundException;
}
