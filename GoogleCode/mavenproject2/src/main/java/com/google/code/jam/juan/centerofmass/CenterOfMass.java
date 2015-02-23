package com.google.code.jam.juan.centerofmass;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import static com.google.code.jam.juan.allyourbase.AllYourBase.getMinimumTime;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author juan
 */
public class CenterOfMass {

    private static class FireFly {

        public double x;
        public double y;
        public double z;
        public double vx;
        public double vy;
        public double vz;

        public FireFly(double x, double y, double z, double vx, double vy, double vz) {
            this.x = x;
            this.y = y;
            this.z = z;
            this.vx = vx;
            this.vy = vy;
            this.vz = vz;
        }
    }
    public double getDistance(double t)
    {
      double x = 0.0;
        double y = 0.0;
        double z = 0.0;
        double vx = 0.0;
        double vy = 0.0;
        double vz = 0.0;
        for (FireFly fireFly : fireFlies) {
            x += fireFly.x;
            y += fireFly.y;
            z += fireFly.z;
            vx += fireFly.vx;
            vy += fireFly.vy;
            vz += fireFly.vz;
        }
        x /= N;
        y /= N;
        z /= N;
        vx /= N;
        vy /= N;
        vz /= N;
        return Math.sqrt((x+vx*t)*(x+vx*t)+(y+vy*t)*(y+vy*t)+(z+vz*t)*(z+vz*t));
    }
    public double getMinTime() {
        double x = 0.0;
        double y = 0.0;
        double z = 0.0;
        double vx = 0.0;
        double vy = 0.0;
        double vz = 0.0;
        for (FireFly fireFly : fireFlies) {
            x += fireFly.x;
            y += fireFly.y;
            z += fireFly.z;
            vx += fireFly.vx;
            vy += fireFly.vy;
            vz += fireFly.vz;
        }
        x /= N;
        y /= N;
        z /= N;
        vx /= N;
        vy /= N;
        vz /= N;
        if(vx * vx + vy * vy + vz * vz == 0  )
        {
            return 0.0;
        }
        double t = -(x * vx + y * vy + z * vz) / (vx * vx + vy * vy + vz * vz);
        if(t < 0)
        {
            return 0.0;
        }
        return t;
    }

    private Integer N;
    private List<FireFly> fireFlies;

    public static void process(String inputPath, String outputPath) throws FileNotFoundException, IOException {

        List<CenterOfMass> tests = new ArrayList<CenterOfMass>();
        try (BufferedReader reader = new BufferedReader(new FileReader(new File(inputPath)))) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(new File(outputPath)))) {
                String line = reader.readLine();
                while (line != null) {
                    //Here you read and write everything, the reader and writer are automatically closed by java 7
                    int T = Integer.parseInt(line);
                    line = reader.readLine();
                    for (int i = 0; i < T && line != null; i++) {
                        CenterOfMass cm = new CenterOfMass();
                        int N = Integer.parseInt(line);
                        cm.N = N;
                        tests.add(cm);
                        List<FireFly> fireFlies = new ArrayList<>();
                        line = reader.readLine();
                        for( int j = 0; j < N && line != null; j++) {
                            String[] split = line.split(" ");
                            double x = Double.parseDouble(split[0]);
                            double y = Double.parseDouble(split[1]);
                            double z = Double.parseDouble(split[2]);
                            double vx = Double.parseDouble(split[3]);
                            double vy = Double.parseDouble(split[4]);
                            double vz = Double.parseDouble(split[5]);
                            FireFly fireFly = new FireFly(x, y, z, vx, vy, vz);
                            fireFlies.add(fireFly);
                            line = reader.readLine();
                        }
                        cm.fireFlies = fireFlies;
                    }

                }
                int i = 0;
                DecimalFormat df = new DecimalFormat("0.00000000");
                for (CenterOfMass cm : tests) {
                    StringBuilder sb = new StringBuilder();
                    double t = cm.getMinTime();
                    double d = cm.getDistance(t);
                        sb.append("Case #").append(i + 1).append(": ").append(df.format(d)).append(" ").append(df.format(t));
                        writer.append(sb.toString());
                        writer.newLine();
                        i++;
                }   
            }
        }
    }

    public static void main(String args[]) throws FileNotFoundException, IOException {

        ClassLoader classLoader = CenterOfMass.class.getClassLoader();
        //change it with your specific resources for each input.
        String fileLarge = classLoader.getResource("com/google/code/jam/juan/centerofmass/large.in").getFile();
        String fileSmall = classLoader.getResource("com/google/code/jam/juan/centerofmass/small.in").getFile();
        process(fileLarge, "large.txt");
        process(fileSmall, "small.txt");
    }
}
