package D5;

import java.io.*;
import java.util.*;

public class Prob1247 {
    public static void main(String args[]) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder ans = new StringBuilder();

        int T = Integer.parseInt(br.readLine());
        for(int t = 1; t <= T; t++){
            int n = Integer.parseInt(br.readLine());

            int[][] dist = new int[n][1 << n];
            Point[] cus = new Point[n];

            st = new StringTokenizer(br.readLine());
            int sx = Integer.parseInt(st.nextToken());
            int sy = Integer.parseInt(st.nextToken());
            int dx = Integer.parseInt(st.nextToken());
            int dy = Integer.parseInt(st.nextToken());
            for(int i = 0; i < n; i++){
                int x = Integer.parseInt(st.nextToken());
                int y = Integer.parseInt(st.nextToken());
                cus[i] = new Point(x, y);

                for(int j = 0; j < (1 << n); j++){
                    dist[i][j] = Integer.MAX_VALUE;
                }
            }


            int answer = Integer.MAX_VALUE;
            Deque<Node> queue = new ArrayDeque<>();
            for(int i = 0; i < n; i++){
                int x = cus[i].x;
                int y = cus[i].y;
                int d = Math.abs(sx - x) + Math.abs(sy - y);
                queue.offer(new Node(i, d, 1 << i));
                dist[i][1 << i] = d;
            }

            while(!queue.isEmpty()){
                // System.out.println("queue=" + queue);
                Node node = queue.poll();
                int num = node.num;
                int d = node.d;
                int v = node.v;

                int x = cus[num].x;
                int y = cus[num].y;

                if(v >= (1 << n) - 1){
                    int nextD = Math.abs(x - dx) + Math.abs(y - dy);
                    answer = Math.min(answer, d + nextD);
                    continue;
                }

                for(int i = 0; i < n; i++){
                    if(i == num){
                        continue;
                    }
                    Point next = cus[i];
                    int nx = next.x;
                    int ny = next.y;

                    int nextD = Math.abs(nx - x) + Math.abs(ny - y);
                    if(dist[i][v] > d + nextD && (v & (1 << i)) != (1 << i)){
                        dist[i][v] = d + nextD;
                        queue.offer(new Node(i, d + nextD, v | (1 << i)));
                    }
                }
            }

            ans.append(String.format("#%d %d\n", t, answer));
        }

        System.out.println(ans);
    }

    public static class Point{
        int x;
        int y;

        public Point(int x, int y){
            this.x = x;
            this.y = y;
        }
    }

    public static class Node{
        int num;
        int d;
        int v;

        public Node(int num, int d, int v){
            this.num = num;
            this.d = d;
            this.v = v;
        }

        @Override
        public String toString(){
            return "{num=" + num + ", d=" + d + ", v=" + v + "}";
        }
    }
}