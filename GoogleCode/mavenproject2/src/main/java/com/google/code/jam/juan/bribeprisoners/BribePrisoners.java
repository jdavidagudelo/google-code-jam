/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.google.code.jam.juan.bribeprisoners;

import com.google.code.jam.SimpleTemplate;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.*;

/**
 *
 * @author juan
 */
public class BribePrisoners {

    private Integer P;
    private Set<Integer> Q;
    private int[] q;
    private int memo[][];

    public int getCost(int n, int m) {
        if (m - n < 2) {
            memo[n][m] = 0;
            return 0;
        }
        if (memo[n][m] != -1) {
            return memo[n][m];
        }
        int min = Integer.MAX_VALUE;
        for (int i = n + 1; i < m; i++) {
            int calc = (q[i] - q[n] - 1) + (q[m] - q[i] - 1);
            calc = calc + getCost(n, i) + getCost(i, m);
            min = Math.min(min, calc);
        }
        memo[n][m] = min;
        return min;
    }

    public static void process(String inputPath, String outputPath) throws FileNotFoundException, IOException {
        List<BribePrisoners> list = new ArrayList<BribePrisoners>();
        try (BufferedReader reader = new BufferedReader(new FileReader(new File(inputPath)))) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(new File(outputPath)))) {
                String line = reader.readLine();
                int count = Integer.parseInt(line);
                int i = 0;
                while (line != null && i < count) {
                    //Here you read and write everything, the reader and writer are automatically closed by java 7

                    line = reader.readLine();
                    for (i = 0; i < count && line != null; i++) {
                        BribePrisoners bp = new BribePrisoners();
                        String split[] = line.split(" ");
                        bp.P = Integer.parseInt(split[0]);
                        bp.Q = new HashSet<Integer>();

                        list.add(bp);
                        line = reader.readLine();
                        split = line.split(" ");
                        for (String s : split) {
                            bp.Q.add(Integer.parseInt(s));
                        }
                        bp.Q.add(0);
                        bp.Q.add(bp.P + 1);
                        bp.q = new int[bp.Q.size()];
                        int k = 0;
                        for (int x : bp.Q) {
                            bp.q[k] = x;
                            k++;
                        }
                        Arrays.sort(bp.q);
                        bp.memo = new int[bp.q.length][bp.q.length];
                        for (int a[] : bp.memo) {
                            Arrays.fill(a, -1);
                        }
                        line = reader.readLine();
                    }
                }
                i = 0;
                for (BribePrisoners bp : list) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("Case #").append(i + 1).append(": ").append(bp.getCost(0, bp.q.length - 1));
                    writer.append(sb.toString());
                    writer.newLine();
                    i++;
                }
            }
        }
    }

    public static void main(String args[]) throws FileNotFoundException, IOException {
        ClassLoader classLoader = SimpleTemplate.class.getClassLoader();
        //change it with your specific resources for each input.
        String fileLarge = classLoader.getResource("com/google/code/jam/juan/bribeprisoners/large.in").getFile();
        String fileSmall = classLoader.getResource("com/google/code/jam/juan/bribeprisoners/small.in").getFile();
        process(fileLarge, "large.txt");
        process(fileSmall, "small.txt");
    }
}
