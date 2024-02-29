package D4;

import java.io.*;
import java.util.*;

/**
 * 1. N개의 섬들을 연결
 */
public class Prob1251 {

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static StringBuilder sb = new StringBuilder();

    static int size;
    static int[] islandsX;
    static int[] islandsY;
    static int[] parent;
    static double tax;
    static PriorityQueue<Edge> edges;

    public static void main(String[] args) throws IOException{
        int T =  Integer.parseInt(br.readLine());

        for(int testCase = 1; testCase <= T; testCase++) {
            size = Integer.parseInt(br.readLine());

            edges = new PriorityQueue<>(new Comparator<Edge>() {
                @Override
                public int compare(Edge e1, Edge e2) {
                    return e1.cost < e2.cost ? -1 : e1.cost == e2.cost ? 0 : 1;
                }
            });
            islandsX = new int[size];
            islandsY = new int[size];
            parent = new int[size];

            st = new StringTokenizer(br.readLine());
            for(int idx = 0; idx < size; idx++) {
                int x = Integer.parseInt(st.nextToken());

                islandsX[idx] = x;
                parent[idx] = idx;
            }

            st = new StringTokenizer(br.readLine());
            for(int idx = 0; idx < size; idx++) {
                int y = Integer.parseInt(st.nextToken());

                islandsY[idx] = y;
            }

            tax = Double.parseDouble(br.readLine());
            for(int idxA = 0; idxA < size; idxA++) {
                int islandAX = islandsX[idxA];
                int islandAY = islandsY[idxA];
                for(int idxB = idxA + 1; idxB < size; idxB++) {
                    int islandBX = islandsX[idxB];
                    int islandBY = islandsY[idxB];

                    int diffX = Math.abs(islandAX - islandBX);
                    int diffY = Math.abs(islandAY - islandBY);
                    double tunnelLength = tax*diffX*diffX + tax*diffY*diffY;
                    double cost = tunnelLength;
//					System.out.println("cost =" + cost);
                    edges.offer(new Edge(idxA, idxB, cost));
                }
            }

            int targetUsed = size - 1;
            int edgesUsed = 0;
            double totalCost = 0;
            while(!edges.isEmpty() && edgesUsed < targetUsed) {
                Edge edge = edges.poll();
                int nodeA = edge.nodeA;
                int nodeB = edge.nodeB;
                double cost = edge.cost;

                int rootA = findRoot(nodeA);
                int rootB = findRoot(nodeB);

                if(rootA != rootB) {
                    parent[nodeB] = rootA;
                    parent[rootB] = rootA;
                    edgesUsed++;
                    totalCost += cost;
                }
            }


            sb.append(String.format("#%d %d\n", testCase, (long)(totalCost + 0.5)));
        }

        System.out.println(sb);
    }

    public static int findRoot(int node) {
        if(parent[node] == node) {
            return node;
        }

        return parent[node] = findRoot(parent[node]);
    }

    public static class Point {
        int x;
        int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    public static class Edge{
        int nodeA;
        int nodeB;
        double cost;

        public Edge(int nodeA, int nodeB, double cost) {
            this.nodeA = nodeA;
            this.nodeB = nodeB;
            this.cost = cost;
        }
    }

}
