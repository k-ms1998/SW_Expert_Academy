package D3;

import java.io.*;
import java.util.*;

public class Prob2814 {

    static boolean[] visited;
    static int maxCnt = 0;

    public static void main(String args[]) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int totalT = Integer.parseInt(br.readLine());
        for(int t = 1; t <= totalT; t++){
            st = new StringTokenizer(br.readLine());
            int n = Integer.parseInt(st.nextToken());
            int m = Integer.parseInt(st.nextToken());

            maxCnt = 1;
            List<Integer>[] edges = new List[n + 1];
            visited = new boolean[n + 1];
            for(int i = 1; i < n + 1; i++){
                edges[i] = new ArrayList<>();
            }
            for(int i = 0; i < m; i++){
                st = new StringTokenizer(br.readLine());
                int a = Integer.parseInt(st.nextToken());
                int b = Integer.parseInt(st.nextToken());

                edges[a].add(b);
                edges[b].add(a);
            }

            for(int i = 1; i < n + 1; i++){
                visited[i] = true;
                dfs(i, 1, edges);
                visited[i] = false;
            }

            System.out.println(String.format("#%d %d", t, maxCnt));
        }
    }

    public static void dfs(int node, int cnt, List<Integer>[] edges){
        maxCnt = Math.max(maxCnt, cnt);
        for(int next : edges[node]){
            if(!visited[next]){
                visited[next] = true;
                dfs(next, cnt + 1, edges);
                visited[next] = false;
            }
        }
    }
}