import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertTrue;

class MySetTest {

    MySet<Integer> instance = new MySet<>();
    MySet<Integer> msDeserialized = null;

    public void setUp(){
        try {
            Files.deleteIfExists(Path.of("MySet-Serialized.obj"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        for(int i = 0; i< 11; i++){
            instance.add(new Random().nextInt(12));
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
                FileInputStream fis = new FileInputStream("MySet-Serialized.obj");
                ObjectInputStream ois = new ObjectInputStream(fis))
        {
            msDeserialized = (MySet<Integer>) ois.readObject();

        }
        catch (IOException | ClassNotFoundException ex) {
            System.out.println("Exception:  " + ex);
        }

        assertTrue(msDeserialized.isEmpty());
    }

}