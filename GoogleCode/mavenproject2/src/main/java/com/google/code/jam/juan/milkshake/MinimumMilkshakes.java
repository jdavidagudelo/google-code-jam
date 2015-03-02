package com.google.code.jam.juan.milkshake;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
/**
 * @author juan
 */
public class MinimumMilkshakes {
    private int flavors;
    private List<Client> clients;
    /**
     * This algorithm uses the breadth first search algorithm in order to 
     * find the number of malted milkshakes that must be done.
     * @return hashMap which values are the the milkshakes associated to every 
     * possible flavor.
     */
    private HashMap<Integer, Milkshake> bfs() {
        //queue for the bfs
        TreeSet<Node> queue = new TreeSet<>();
        //Starts from the first client
        Client client = clients.get(0);
        //create a new node with every milkshake the client likes
        for (Milkshake milkshake : client.milkshakes) {
            HashMap<Integer, Milkshake> set = new HashMap<>();
            set.put(milkshake.flavor, milkshake);
            Node node = new Node(client.index, milkshake.flavor, set, milkshake.malted);
            queue.add(node);
        }
        //bfs
        while (!queue.isEmpty()) {
            Node current = queue.first();
            queue.remove(current);
            //the algorith finds the last client, so it successfully added every other client
            //without finding any conflict that made the path impossible.
            if (current.customer >= clients.size() - 1) {
                return current.flavors;
            }
            //the next customer of the list
            client = clients.get(current.customer + 1);
            //visits every valid milkshake of the next client
            for (Milkshake milkshake : client.milkshakes) {
                //the milkshake for the current value
                Milkshake m = current.flavors.get(milkshake.flavor);
                //tests if the current milkshake and the next milkshake are
                //both either malted or unmalted, 
                //otherwise this path is invalid and should not
                //be used. if the current flavor is null, the next flavor can be 
                //added without problems.
                if ((m != null && m.malted == milkshake.malted) || m == null) {
                    HashMap<Integer, Milkshake> set = new HashMap<>();
                    set.put(milkshake.flavor, milkshake);
                    set.putAll(current.flavors);
                    Node node = new Node(client.index, milkshake.flavor, set, current.maltedCount + milkshake.malted);
                    queue.add(node);
                }
            }
        }
        return null;
    }
    /**
     * Client contains the index of the client and the set of milkshakes he likes.
     */
    private static class Client {
        /**
         * Index of the client.
         */
        public int index;
        /**
         * Set of milkshakes the client likes.
         */
        public Set<Milkshake> milkshakes;
        public Client(int index, Set<Milkshake> milkshakes) {
            this.index = index;
            this.milkshakes = milkshakes;
        }
    }
    /**
     * Class that represents an specific milkshake liked by a client.
     */
    private static class Milkshake {
        /**
         * Flavor of the milkshake.
         */
        public int flavor;
        /**
         * The client likes it malted or unmalted.
         */
        public int malted;
        public Milkshake(int flavor, int malted) {
            this.flavor = flavor;
            this.malted = malted;
        }
        @Override
        public int hashCode() {
            int hash = 5;
            hash = 19 * hash + this.flavor;
            return hash;
        }
        @Override
        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final Milkshake other = (Milkshake) obj;
            return this.flavor == other.flavor;
        }
        @Override
        public String toString()
        {
            return flavor+" "+malted;
        }
    }
    /**
     * Node used during the BFS path of the graph used to represent the requests of the client.
     */
    private static class Node implements Comparable<Node> {
        /**
         * Index of the client.
         */
        public int customer;
        public int flavor;
        /**
         * List of the milkshakes already used in the path towards this node.
         */
        public HashMap<Integer, Milkshake> flavors;
        /**
         * Number of malted milkshakes that must be done.
         */
        public int maltedCount;
        
        public Node(int customer, int flavor, HashMap<Integer, Milkshake> flavors, int maltedCount) {
            this.customer = customer;
            this.flavor = flavor;
            this.flavors = flavors;
            this.maltedCount = maltedCount;
        }

        @Override
        public int compareTo(Node o) {
            if (o.maltedCount != maltedCount) {
                return maltedCount - o.maltedCount;
            }
            if (o.customer != customer) {
                return customer - o.customer;
            }
            if (o.flavor != flavor) {
                return o.flavor - flavor;
            }
            return 0;
        }

    }

    public static void process(String inputPath, String outputPath) throws FileNotFoundException, IOException {
        List<MinimumMilkshakes> milkShakes = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(new File(inputPath)))) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(new File(outputPath)))) {
                String line = reader.readLine();
                while (line != null) {
                    //Here you read and write everything, the reader and writer are automatically closed by java 7
                    int tests = Integer.parseInt(line);
                    line = reader.readLine();
                    for (int i = 0; i < tests; i++) {
                        MinimumMilkshakes milkShake = new MinimumMilkshakes();
                        int N = Integer.parseInt(line);
                        milkShake.flavors = N;
                        line = reader.readLine();
                        int M = Integer.parseInt(line);
                        milkShake.clients = new ArrayList<>();
                        for (int j = 0; j < M; j++) {
                            
                        line = reader.readLine();
                            String split[] = line.split(" ");
                            HashSet<Milkshake> milkshakes = new HashSet<>();
                            for (int k = 1; k < split.length; k++) {
                                Milkshake current = new Milkshake(Integer.parseInt(split[k]),
                                        Integer.parseInt(split[k + 1]));
                                milkshakes.add(current);
                                k++;
                            }
                            Client client = new Client(j, milkshakes);
                            milkShake.clients.add(client);
                        }
                        milkShakes.add(milkShake);
                        line = reader.readLine();
                    }

                }
                int i = 1;
                for (MinimumMilkshakes milkShake : milkShakes) {
                    HashMap<Integer, Milkshake> set = milkShake.bfs();
                    
                    StringBuilder sb = new StringBuilder();
                    sb.append("Case #").append(i).append(": ");i++;
                    if(set != null)
                    {
                        int values[] = new int[milkShake.flavors];
                        for(Milkshake m : set.values())
                        {
                            values[m.flavor-1] = m.malted;
                        }
                        for(int v : values)
                        {
                            sb.append(v).append(" ");
                        }
                    }
                    else {
                        sb.append("IMPOSSIBLE");
                    }
                    writer.write(sb.toString());
                    writer.newLine();
                }
            }
        }
    }

    public static void main(String args[]) throws FileNotFoundException, IOException {
        ClassLoader classLoader = MinimumMilkshakes.class.getClassLoader();
         String fileLarge = classLoader.getResource("com/google/code/jam/juan/milkshake/large.in").getFile();
         String fileSmall = classLoader.getResource("com/google/code/jam/juan/milkshake/small.in").getFile();
         process(fileLarge, "large.txt");
         process(fileSmall, "small.txt");
    }
}
