package D4;

import java.io.*;
import java.util.*;

public class Prob4193 {

    static final int INF = 100_000_000;
    static int[] dx = {1, 0, -1, 0};
    static int[] dy = {0, 1, 0, -1};

    public static void main(String args[]) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder ans = new StringBuilder();

        int T = Integer.parseInt(br.readLine());
        for(int t = 1; t <= T; t++){
            int n = Integer.parseInt(br.readLine());
            int[][] grid = new int[n][n];
            int[][] cost = new int[n][n];

            for(int y = 0; y < n; y++){
                st = new StringTokenizer(br.readLine());
                for(int x = 0; x < n; x++){
                    grid[y][x] = Integer.parseInt(st.nextToken());
                    cost[y][x] = INF;
                }
            }

            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());

            st = new StringTokenizer(br.readLine());
            int c = Integer.parseInt(st.nextToken());
            int d = Integer.parseInt(st.nextToken());
            Deque<Node> queue = new ArrayDeque<>();
            queue.offer(new Node(b, a, 0));
            cost[a][b] = 0;

            while(!queue.isEmpty()){
                // System.out.println("queue=" + queue);
                Node node = queue.poll();
                int x = node.x;
                int y = node.y;
                int dist = node.dist;

                if(x == d && y == c){
                    continue;
                }

                for(int i = 0; i < 4; i++){
                    int nx = x + dx[i];
                    int ny = y + dy[i];

                    if(nx < 0 || ny < 0 || nx >= n || ny >= n){
                        continue;
                    }

                    if(grid[ny][nx] == 1){
                        continue;
                    }else if(grid[ny][nx] == 2){
                        int diff = Math.abs(2 - dist % 3);
                        if(cost[ny][nx] > dist + diff + 1){
                            cost[ny][nx] = dist + diff + 1;
                            queue.offer(new Node(nx, ny, dist + diff + 1));
                        }
                    }else{
                        if(cost[ny][nx] > dist + 1){
                            cost[ny][nx] = dist + 1;
                            queue.offer(new Node(nx, ny, dist + 1));
                        }
                    }
                }

            }
            // System.out.println("-----------");

            ans.append(String.format("#%d %d\n", t, (cost[c][d] == INF ? -1 : cost[c][d])));
        }

        System.out.println(ans);
    }

    public static class Node{
        int x;
        int y;
        int dist;

        public Node(int x, int y, int dist){
            this.x = x;
            this.y = y;
            this.dist = dist;
        }

        @Override
        public String toString(){
            return "{x=" + x + ", y=" + y + ", dist=" + dist + "}";
        }
    }
}