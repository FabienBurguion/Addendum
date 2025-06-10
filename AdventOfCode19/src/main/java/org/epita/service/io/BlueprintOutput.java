package org.epita.service.io;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import static org.epita.utils.Utils.findIndexOfMax;

public class BlueprintOutput {
    private static final String FILENAME = "analysis.txt";

    public static void writeOutput(int[] values) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(FILENAME, false));
        for (int i = 0; i < values.length; i++) {
            writer.append("Blueprint ").append(String.valueOf(i + 1)).append(": ");
            writer.append(String.valueOf(values[i])).append("\n");
        }
        writer.append("\nBest blueprint is the blueprint ").append(String.valueOf(findIndexOfMax(values) + 1));
        writer.close();
    }
}
