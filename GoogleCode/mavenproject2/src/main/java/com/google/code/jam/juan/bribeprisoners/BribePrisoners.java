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
    private Integer Q;
    private class Node implements Comparable<Node>
    {
        public int cost;
        public boolean[] cells;
        public int q;
        public int prisoner;

        public Node(int cost, boolean[] cells, int q, int prisoner) {
            this.cost = cost;
            this.cells = cells;
            this.q = q;
            this.prisoner = prisoner;
        }
        
        public int compareTo(Node o)
        {
            if(o.cost != cost)
            {
                return cost - o.cost;
            }
            if(q != o.q)
            {
                return q - o.q;
            }
            if(prisoner != o.prisoner)
            {
                return prisoner - o.prisoner;
            }
            return 0;
        }
        
    }
    private boolean over(boolean cells[])
    {
        int count = 0;
        for(boolean b : cells)
        {
            if(b)
            {
                count++;
            }
        }
        return count == Q;
    }
    private int getCost(boolean[] cells, int p)
    {
        int cost = 0;
        for(int i = p+1; i < cells.length && cells[i]; i++)
        {
            cost++;
        }
        for(int i = p-1; i >= 0 && cells[i]; i++)
        {
            cost++;
        }
        return cost;
    }
    public int bfs()
    {
        TreeSet<Node> queue = new TreeSet<>();
        for(int p = 0; p < P; p++)
        {
            
            boolean cells[] = new boolean[P];
            Arrays.fill(cells, true);
            cells[p] = false;
            int cost = getCost(cells, p);
            queue.add(new Node(cost, cells, 1, p));
        }
        while(!queue.isEmpty())
        {
            Node current = queue.first();
            queue.remove(current);
            if(current.q == Q)
            {
                return current.cost;
            }
            for(int i = 0; i < current.cells.length; i++)
            {
                if(current.cells[i])
                {
                    int cost = getCost(current.cells, i);
                    boolean[] cells = Arrays.copyOf(current.cells, current.cells.length);
                    cells[i] = false;
                    queue.add(new Node(current.cost+cost, cells, current.q+1, i));
                }
            }
        }
        return 0;
    }
    public static void process(String inputPath, String outputPath) throws FileNotFoundException, IOException {
        List<BribePrisoners> list = new ArrayList<BribePrisoners>();
        
        try (BufferedReader reader = new BufferedReader(new FileReader(new File(inputPath)))) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(new File(outputPath)))) {
                String line = reader.readLine();
                while (line != null) {
                    //Here you read and write everything, the reader and writer are automatically closed by java 7
                    int count = Integer.parseInt(line);
                    line = reader.readLine();
                    for(int i = 0; i < count && line != null; i++)
                    {
                        BribePrisoners bp = new BribePrisoners();
                        String split[] = line.split(" ");
                        bp.P = Integer.parseInt(split[0]);
                        bp.Q = Integer.parseInt(split[1]);
                        list.add(bp);
                        line = reader.readLine();
                    }
                }
                int i = 0;
                for(BribePrisoners bp : list)
                {
                     StringBuilder sb = new StringBuilder();
                        sb.append("Case #").append(i + 1).append(": ").append(bp.bfs());
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
        //process(fileLarge, "large.txt");
        process(fileSmall, "small.txt");
    }
}
