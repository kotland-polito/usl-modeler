package io.github.kotlandpolito;

import com.codahale.usl4j.Measurement;
import com.codahale.usl4j.Model;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UslModeler {
    public static void main(String[] args) {
        if (args.length < 2){
            System.out.println("USAGE:\njava -jar usl-modeler-<version>.jar <input-file> <output-file>");
            return;
        }

        List<String[]> lines;
        try {
            lines = Utils.parseCsv(args[0]);
        } catch (FileNotFoundException e) {
            System.out.println("Couldn't find the specified input file");
            return;
        }

        double[][] points = lines.stream()
                .map(Utils::stringArrayToDoubleArray)
                .toArray(double[][]::new);

        // Map the points to measurements of concurrency and throughput, then build a model from them.
        Model model = Arrays.stream(points)
                .map(Measurement.ofConcurrency()::andThroughput)
                .collect(Model.toModel());

        // Predict the throughput for various levels of possible concurrency.
        List<String[]> outLines = new ArrayList<>();
        for (int i = 1; i < 300; ++i) {
            outLines.add(new String[]{String.valueOf(i), String.valueOf(model.throughputAtConcurrency(i))});
        }

        try {
            Utils.writeCsv(args[1], outLines);
        } catch (FileNotFoundException e) {
            System.out.println("Couldn't write to the specified output file");
        }
    }
}
