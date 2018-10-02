//By: Connor Toale
//CS4306
//March 24, 2018

import java.util.InputMismatchException;
import java.util.Scanner;

// main class
public class JavaPrim {
        // variables
        private boolean incomplete[];
        private boolean complete[];
        private int vertexnumber;
        private int AMatrix[][];
        private int set[];
        public static final int INFINITE = 999;
        private int parent[];

        // main method for prims calculation
        public JavaPrim(int vertexnumber) {
                this.vertexnumber = vertexnumber;
                incomplete = new boolean[vertexnumber + 1];
                complete = new boolean[vertexnumber + 1];
                AMatrix = new int[vertexnumber + 1][vertexnumber + 1];
                set = new int[vertexnumber + 1];
                parent = new int[vertexnumber + 1];
        }

        // get count
        public int CountIncomplete(boolean incomplete[]) {
                int count = 0;
                for (int index = 0; index < incomplete.length; index++) {
                        if (incomplete[index]) {
                                count++;
                        }
                }
                return count;
        }

        // implementation of algorithm
        public void primsAlgorithm(int AMatrix[][]) {
                int determiningVertex;
                for (int source = 1; source <= vertexnumber; source++) {
                        for (int destination = 1; destination <= vertexnumber; destination++) {
                                this.AMatrix[source][destination] = AMatrix[source][destination];
                        }
                }

                for (int index = 1; index <= vertexnumber; index++) {
                        set[index] = INFINITE;
                }
                set[1] = 0;
                incomplete[1] = true;
                parent[1] = 1;

                while (CountIncomplete(incomplete) != 0) {
                        determiningVertex = getKeyVertex(incomplete);
                        incomplete[determiningVertex] = false;
                        complete[determiningVertex] = true;
                        checkAdjacent(determiningVertex);
                }
        }

        private int getKeyVertex(boolean[] incomplete2) {
                int min = Integer.MAX_VALUE;
                int node = 0;
                for (int vertex = 1; vertex <= vertexnumber; vertex++) {
                        if (incomplete[vertex] == true && set[vertex] < min) {
                                node = vertex;
                                min = set[vertex];
                        }
                }
                return node;
        }

        public void checkAdjacent(int determiningVertex) {

                for (int vertexgoal = 1; vertexgoal <= vertexnumber; vertexgoal++) {
                        if (complete[vertexgoal] == false) {
                                if (AMatrix[determiningVertex][vertexgoal] != INFINITE) {
                                        if (AMatrix[determiningVertex][vertexgoal] < set[vertexgoal]) {
                                                set[vertexgoal] = AMatrix[determiningVertex][vertexgoal];
                                                parent[vertexgoal] = determiningVertex;
                                        }
                                        incomplete[vertexgoal] = true;
                                }
                        }
                }
        }

        public int printMST() {
                int res = 0;
                System.out.println("Minimum Spanning Tree:");
                System.out.println("Origin  ->  Goal   |   Weight");
                for (int vertex = 2; vertex <= vertexnumber; vertex++) {
                        System.out.println(parent[vertex] + "\t->\t" + vertex + "\t|\t" + AMatrix[parent[vertex]][vertex]);
                        res += AMatrix[parent[vertex]][vertex];
                }
                return res;
        }

        public static void main(String... arg) {
                int adjacency_matrix[][];
                int vertexCount;
                Scanner scan = new Scanner(System.in);
                        System.out.println("Please enter the number of vertices in the graph.");
                        vertexCount = scan.nextInt();
                        adjacency_matrix = new int[vertexCount + 1][vertexCount + 1];

                        System.out.println("Please enter the weighted matrix for the graph.");
                        for (int i = 1; i <= vertexCount; i++) {
                                for (int j = 1; j <= vertexCount; j++) {
                                        adjacency_matrix[i][j] = scan.nextInt();
                                        if (i == j) {
                                                adjacency_matrix[i][j] = 0;
                                                continue;
                                        }
                                        if (adjacency_matrix[i][j] == 0) {
                                                adjacency_matrix[i][j] = INFINITE;
                                        }
                                }
                        }

                        JavaPrim prims = new JavaPrim(vertexCount);
                        prims.primsAlgorithm(adjacency_matrix);
                        System.out.println("Minimum cost possible: " + prims.printMST());
                }
        }
