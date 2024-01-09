package D4;

import java.io.*;
import java.util.*;

public class Prob1238 {
    public static void main(String args[]) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder ans = new StringBuilder();

        for(int t = 1; t <= 10; t++){
            st = new StringTokenizer(br.readLine());
            int n = Integer.parseInt(st.nextToken());
            int s = Integer.parseInt(st.nextToken());
            boolean[] v = new boolean[101];
            Deque<Integer>[] edges = new Deque[101];
            for(int i = 0; i <= 100; i++){
                edges[i] = new ArrayDeque<>();
            }

            st = new StringTokenizer(br.readLine());
            for(int i = 0; i < n/2; i++){
                int from = Integer.parseInt(st.nextToken());
                int to = Integer.parseInt(st.nextToken());
                edges[from].offer(to);
            }

            int ansNode = s;
            int ansD = 0;
            Deque<Point> queue = new ArrayDeque<>();
            queue.offer(new Point(s, 0));
            v[s] = true;
            while(!queue.isEmpty()){
                Point p = queue.poll();
                int node = p.node;
                int d = p.d;

                if(d >= ansD){
                    if(d > ansD){
                        ansNode = node;
                        ansD = d;
                    }else{
                        ansNode = Math.max(ansNode, node);
                    }
                }

                Deque<Integer> nextList = edges[node];
                while(!nextList.isEmpty()){
                    int next = nextList.poll();
                    if(!v[next]){
                        v[next] = true;
                        queue.offer(new Point(next, d + 1));
                    }
                }
            }

            ans.append(String.format("#%d %d\n", t, ansNode));
        }

        System.out.println(ans);
    }

    public static class Point{
        int node;
        int d;

        public Point(int node, int d){
            this.node = node;
            this.d = d;
        }
    }
}
