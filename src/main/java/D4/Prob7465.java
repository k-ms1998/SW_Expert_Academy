package D4;

import java.io.*;
import java.util.*;

public class Prob7465 {

    static final int INF = 100_000_000;
    static int[] dx = {1, 0, -1, 0};
    static int[] dy = {0, 1, 0, -1};

    public static void main(String args[]) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder ans = new StringBuilder();

        int T = Integer.parseInt(br.readLine());
        for(int t = 1; t <= T; t++){
            st = new StringTokenizer(br.readLine());
            int n = Integer.parseInt(st.nextToken());
            int m = Integer.parseInt(st.nextToken());

            int[] parent = new int[n + 1];
            for(int i = 1; i < n + 1; i++){
                parent[i] = i;
            }

            for(int i = 0; i < m; i++){
                st = new StringTokenizer(br.readLine());
                int a = Integer.parseInt(st.nextToken());
                int b = Integer.parseInt(st.nextToken());

                int rootA = findRoot(a, parent);
                int rootB = findRoot(b, parent);

                if(rootA != rootB){
                    parent[b] = rootA;
                    parent[rootB] = rootA;
                }
            }

            Set<Integer> set = new HashSet<>();
            for(int i = 1; i < n + 1; i++){
                int root = findRoot(i, parent);
                set.add(root);
            }

            ans.append(String.format("#%d %d\n", t, set.size()));
        }

        System.out.println(ans);
    }

    public static int findRoot(int node, int[] parent){
        if(node == parent[node]){
            return node;
        }

        int next = findRoot(parent[node], parent);
        parent[node] = next;
        return parent[node];
    }

}