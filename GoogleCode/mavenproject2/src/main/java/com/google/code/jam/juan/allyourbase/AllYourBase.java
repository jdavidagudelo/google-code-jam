/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.google.code.jam.juan.allyourbase;

import java.io.BufferedReader;
import java.math.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.*;
import java.io.IOException;
import java.util.*;
/**
 *
 * @author juan
 */
public class AllYourBase {

    /**
     * This method receives an String and converts it to the minimum possible decimal
     * value considering the minimum possible base in which the digits could be.
     * That is for example the string cats corresponds to an String 1023, so the minimum possible
     * base for this number is 4 and converting it to decimal base the result is 75.
     * @param s the String from which we want to get the decimal value.
     * @return the decimal value of the minimal possible value of the number in any 
     * possible base different of one.
     */
    public static String getMinimumTime(String s) {
        int base = 0;
        Set<Character> set = new HashSet<Character>();
        for (int i = 0; i < s.length(); i++) {
            if (!set.contains(s.charAt(i))) {
                base++;
            }
            set.add(s.charAt(i));
        }
        //THEY ARE NOT USING UNARY BASE, REMEMBER THIS YU SON OF THE BITCH.
        if (base == 1) {
            base = 2;
        }
        Map<Character, Integer> map = new HashMap<>();
        //Every digit in an Array, DO NOT TRY TO STORE THEM IN A STRING, IT DOESNOT CONSIDER 
        //BASES BIGGER THAT 10, so do not use an string
        int[] digits = new int[s.length()];
        int digit = 0;
        for (int i = 0; i < s.length(); i++) {
            if (i == 0) {
                map.put(s.charAt(0), 1);
                digits[i] = 1;
            } else {
                Integer current = map.get(s.charAt(i));
                if (current == null) {
                    if (digit == 1) {
                        digit = 2;
                    }
                    map.put(s.charAt(i), digit);
                    digits[i] = digit;
                    digit++;
                } else {
                    digits[i] = current;
                }
            }
        }
        //be careful with the limits
        BigInteger result = BigInteger.ZERO;
        int pow = 0;
        for (int i = digits.length - 1; i >= 0; i--) {
            result = result.add(BigInteger.valueOf(Integer.parseInt(String.valueOf(digits[i]))).
                    multiply(BigInteger.valueOf(base).pow(pow)));
            pow++;
        }
        return String.valueOf(result);
    }
    public static void process(String inputPath, String outputPath) throws FileNotFoundException, IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(new File(inputPath)))) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(new File(outputPath)))) {
                String line = reader.readLine();
                while (line != null) {
                    int count = Integer.parseInt(line);
                    line = reader.readLine();
                    for (int i = 0; i < count && line != null; i++) {
                        StringBuilder sb = new StringBuilder();
                        sb.append("Case #").append(i + 1).append(": ").append(getMinimumTime(line));
                        writer.append(sb.toString());
                        writer.newLine();
                        line = reader.readLine();
                    }
                }
            }
        }
    }

    public static void main(String args[]) throws FileNotFoundException, IOException {
        ClassLoader classLoader = AllYourBase.class.getClassLoader();
        String fileLarge = classLoader.getResource("com/google/code/jam/juan/allyourbase/large.in").getFile();
        String fileSmall = classLoader.getResource("com/google/code/jam/juan/allyourbase/small.in").getFile();
        process(fileLarge, "large.txt");
        process(fileSmall, "small.txt");
    }
}
