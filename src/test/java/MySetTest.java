import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MySetTest {

    MySet<Integer> instance = new MySet<>();
    MySet<String> msDeserialized = null;

    public void setUp(){
        try {
            Files.deleteIfExists(Path.of("MySet-Serialized.obj"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        for(int i = 1; i<= 10; i++){
            instance.add(i);
        }
    }

    @Test
    public void serializeTest(){
        setUp();
        try (
            FileOutputStream fos = new FileOutputStream("MySet-Serialized.obj");
            BufferedOutputStream bos = new BufferedOutputStream(fos);
            ObjectOutputStream oos = new ObjectOutputStream(bos))
        {
            oos.writeObject(instance);
        }
        catch (IOException ex) {
            System.out.println("Exception:  " + ex);
        }
        assertTrue(Files.exists(Path.of("MySet-Serialized.obj")));

    }

    @Test
    public void deSerializeTest(){

        try (
                FileInputStream fis = new FileInputStream("DeserializingTest.obj");
                ObjectInputStream ois = new ObjectInputStream(fis))
        {
            msDeserialized = (MySet<String>) ois.readObject();

        }
        catch (IOException | ClassNotFoundException ex) {
            System.out.println("Exception:  " + ex);
        }
        assertEquals(msDeserialized.size(), 10);
    }

}