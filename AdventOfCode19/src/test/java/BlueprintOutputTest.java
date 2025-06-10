import org.epita.service.io.BlueprintOutput;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BlueprintOutputTest {

    private static final String TEST_FILE = "analysis.txt";

    @BeforeEach
    @AfterEach
    void cleanFile() throws IOException {
        Files.deleteIfExists(Path.of(TEST_FILE));
    }

    @Test
    void writeOutput_createsCorrectFileContent() throws IOException {
        int[] input = {2, 4, 1};

        BlueprintOutput.writeOutput(input);

        Path path = Path.of(TEST_FILE);
        assertTrue(Files.exists(path), "The file must be created");

        String expected = """
                Blueprint 1: 2
                Blueprint 2: 4
                Blueprint 3: 1

                Best blueprint is the blueprint 2""";

        String actual = Files.readString(path).trim();

        assertEquals(expected, actual);
    }
}
