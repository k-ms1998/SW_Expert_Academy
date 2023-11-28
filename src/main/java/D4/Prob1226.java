package D4;

import java.io.*;
import java.util.*;

public class Prob1226 {

    static int[] dx = {1, 0, -1, 0};
    static int[] dy = {0, 1, 0, -1};

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        StringBuilder ans = new StringBuilder();
        for(int t = 1; t <= 10; t++){
            int num = Integer.parseInt(br.readLine());

            int[][] grid = new int[16][16];
            boolean[][] v = new boolean[16][16];

            int sx = 0;
            int sy = 0;
            int ex = 0;
            int ey = 0;
            for(int y = 0; y < 16; y++){
                String row = br.readLine();
                for(int x = 0; x < 16; x++){
                    grid[y][x] = Integer.parseInt(String.valueOf(row.charAt(x)));
                    if(grid[y][x] == 2){
                        sx = x;
                        sy = y;
                    }
                    if(grid[y][x] == 3){
                        ex = x;
                        ey = y;
                    }
                }
            }

            Deque<Point> queue = new ArrayDeque<>();
            queue.offer(new Point(sx, sy));
            v[sy][sx] = true;
            while(!queue.isEmpty()){
                Point p = queue.poll();
                int x = p.x;
                int y = p.y;

                if(x == ex && y == ey){
                    break;
                }

                for(int i = 0; i < 4; i++){
                    int nx = x + dx[i];
                    int ny = y + dy[i];

                    if(nx < 0 || ny < 0 || nx >= 16 || ny >= 16){
                        continue;
                    }
                    if(grid[ny][nx] == 1){
                        continue;
                    }
                    if(!v[ny][nx]){
                        v[ny][nx] = true;
                        queue.offer(new Point(nx, ny));
                    }

                }
            }

            ans.append(String.format("#%d %d\n", num, v[ey][ex] ? 1 : 0));
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
}