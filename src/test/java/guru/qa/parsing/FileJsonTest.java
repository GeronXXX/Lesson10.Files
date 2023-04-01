package guru.qa.parsing;

import com.google.gson.Gson;
import jdk.jfr.Description;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public class FileJsonTest {

    private ClassLoader cl = FileZipTest.class.getClassLoader();
    @Test
    @Description("Проверка JSON файла")

    void readGameFromJsonTest() throws Exception {
        Gson gson = new Gson();
        try (InputStream is = cl.getResourceAsStream("PC.json");
             InputStreamReader isr = new InputStreamReader(is)) {
             Pc pc = gson.fromJson(isr, Pc.class);

            Assertions.assertEquals("Computer - Galaxy", pc.name);
            Assertions.assertEquals("Ivan", pc.сreator);
            Assertions.assertEquals(32, pc.raw);
            Assertions.assertEquals("AMD Ryzen 7 7700x", pc.cpu);
            Assertions.assertEquals("Nvidia RTX 3080", pc.gpu);
            Assertions.assertTrue(pc.pbo);
            Assertions.assertEquals(List.of("ASUS","GIGABYTE", "MSI"), pc.mbManufacturer);
        }
    }
}
