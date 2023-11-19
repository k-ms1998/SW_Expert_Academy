package D4;

import java.io.*;
import java.util.*;

public class Prob1249 {

    static final int INF = 100000000;
    static int[] dx = {0, 1, 0, -1};
    static int[] dy = {1, 0, -1, 0};

    public static void main(String args[]) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        StringBuilder ans = new StringBuilder();
        int totalT = Integer.parseInt(br.readLine());
        for(int t = 1; t <= totalT; t++){
            st = new StringTokenizer(br.readLine());
            int n = Integer.parseInt(st.nextToken());

            int[][] grid = new int[n][n];
            int[][] dist = new int[n][n];
            for(int y = 0; y < n; y++){
                String row = br.readLine();
                for(int x = 0; x < n; x++){
                    grid[y][x] = Integer.parseInt(String.valueOf(row.charAt(x)));
                    dist[y][x] = INF;
                }
            }

            Deque<Point> queue = new ArrayDeque<>();
            queue.offer(new Point(0, 0, 0));
            dist[0][0] = 0;

            while(!queue.isEmpty()){
                Point p = queue.poll();
                int x = p.x;
                int y = p.y;
                int d = p.d;

                if(x == n - 1 && y == n - 1){
                    continue;
                }

                for(int i = 0; i < 4; i++){
                    int nx = x + dx[i];
                    int ny = y + dy[i];

                    if(nx < 0 || ny < 0 || nx >= n || ny >= n){
                        continue;
                    }

                    int nextD = d + grid[ny][nx];
                    if(dist[ny][nx] > nextD){
                        dist[ny][nx] = nextD;
                        queue.offer(new Point(nx, ny, nextD));
                    }
                }
            }

            ans.append(String.format("#%d %d\n", t, dist[n-1][n-1]));
        }
        System.out.println(ans.toString());
    }

    public static class Point{
        int x;
        int y;
        int d;

        public Point(int x, int y, int d){
            this.x = x;
            this.y = y;
            this.d = d;
        }
    }

}