/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.google.code.jam.juan.magictrick;

import com.google.code.jam.SimpleTemplate;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author juan
 */
public class MagicTricks {
     public static void process(String inputPath, String outputPath) throws FileNotFoundException, IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(new File(inputPath)))) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(new File(outputPath)))) {
                String line = reader.readLine();
                while (line != null) {
                    //Here you read and write everything, 
                    //the reader and writer are automatically closed by java 7
                    int tests = Integer.parseInt(line);
                    String[] results = new String[tests];
                    int t = 0;
                    line = reader.readLine();
                    while(line != null)
                    {
                        int f1[][] = new int[4][4];
                        int f2[][] = new int[4][4];
                        int r1 = Integer.parseInt(line)-1;
                        for(int i = 0; i < 4; i++)
                        {
                            line = reader.readLine();
                            String split[] = line.split(" ");
                            for(int j = 0; j < 4; j++)
                            {
                                f1[i][j] = Integer.parseInt(split[j]);
                            }
                        }
                        line = reader.readLine();
                        int r2 = Integer.parseInt(line)-1;
                        for(int i = 0; i < 4; i++)
                        {
                            line = reader.readLine();
                            String split[] = line.split(" ");
                            for(int j = 0; j < 4; j++)
                            {
                                f2[i][j] = Integer.parseInt(split[j]);
                            }
                        }
                        Set<Integer> possible = new HashSet<>();
                        for(int i = 0; i < 4; i++)
                        {
                            possible.add(f1[r1][i]);
                        }
                        Set<Integer> real = new HashSet<>();
                        for(int i = 0; i < 4; i++)
                        {
                            if(possible.contains(f2[r2][i]))
                            {
                                real.add(f2[r2][i]);
                            }
                        }
                        if(real.size() == 1)
                        {
                            results[t] = String.valueOf(real.iterator().next());
                        }
                        else if(real.size() < 1)
                        {
                            results[t] = "Volunteer cheated!";
                        }
                        else
                        {
                            results[t] = "Bad magician!";
                        }
                        line = reader.readLine();
                        t++;
                    }
                    for(int i = 0; i < results.length; i++)
                    {
                        String sb = "Case #" + (i+1) + ": " + results[i];
                        writer.write(sb.toString());
                        writer.newLine();
                    }
                }
            }
        }
    }
    public static void main(String args[]) throws FileNotFoundException, IOException {
        ClassLoader classLoader = SimpleTemplate.class.getClassLoader();
        //change it with your specific resources for each input.
        //String fileLarge = classLoader.getResource("com/google/code/jam/juan/allyourbase/large.in").getFile();
        String fileSmall = classLoader.getResource("com/google/code/jam/juan/magictrik/small.in").getFile();
        //process(fileLarge, "large.txt");
        process(fileSmall, "small.txt");
    }
}
