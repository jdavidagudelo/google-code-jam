/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.google.code.jam.juan.minimumscalar;

import com.google.code.jam.SimpleTemplate;
import com.google.code.jam.juan.milkshake.MinimumMilkshakes;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.util.*;

/**
 *
 * @author juan
 */
public class MinimumScalar {

    public static void process(String inputPath, String outputPath) throws FileNotFoundException, IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(new File(inputPath)))) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(new File(outputPath)))) {
                String line = reader.readLine();
                BigInteger results[];
                while (line != null) {
                    //Here you read and write everything, the reader and writer are automatically closed by java 7
                    int tests = Integer.parseInt(line);
                    results = new BigInteger[tests];
                    line = reader.readLine();
                    int index = 0;
                    while (line != null) {
                        int n = Integer.parseInt(line);
                        line = reader.readLine();
                        String[] splitX = line.split(" ");
                        line = reader.readLine();
                        String[] splitY = line.split(" ");
                        BigInteger[] x = new BigInteger[n];
                        BigInteger[] y = new BigInteger[n];
                        for (int i = 0; i < n; i++) {
                            x[i] = new BigInteger(splitX[i]);
                            y[i] = new BigInteger(splitY[i]);
                        }
                        Arrays.sort(x);
                        Arrays.sort(y);
                        BigInteger sum = BigInteger.ZERO;
                        for (int i = 0; i < x.length; i++) {
                            sum = sum.add(x[i].multiply(y[x.length-i-1]));
                        }
                        results[index] = sum;
                        index++;
                        line = reader.readLine();
                    }
                    int c = 1;
                    for (BigInteger r : results) {
                        writer.write("Case #" + c + ": " + r);
                        writer.newLine();
                        c++;
                    }
                }
            }
        }
    }

    public static void main(String args[]) throws FileNotFoundException, IOException {
        ClassLoader classLoader = MinimumMilkshakes.class.getClassLoader();
        //change it with your specific resources for each input.
       String fileLarge = classLoader.getResource("com/google/code/jam/juan/minimumscalar/large.in").getFile();
        String fileSmall = classLoader.getResource("com/google/code/jam/juan/minimumscalar/small.in").getFile();
        process(fileLarge, "large.txt");
        process(fileSmall, "small.txt");
    }
}
