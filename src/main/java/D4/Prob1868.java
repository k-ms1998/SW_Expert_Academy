package D4;

import java.io.*;
import java.util.*;

public class Prob1868 {

    static int[] dx = {0, 1, 1, 1, 0, -1, -1, -1};
    static int[] dy = {-1, -1, 0, 1, 1, 1, 0, -1};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder ans = new StringBuilder();

        int T = Integer.parseInt(br.readLine());
        for(int t = 1; t <= T; t++){
            int n = Integer.parseInt(br.readLine());
            char[][] grid = new char[n][n];
            int[][] cnt = new int[n][n];
            boolean[][] v = new boolean[n][n];

            for(int y = 0; y < n; y++){
                String row = br.readLine();
                for(int x = 0; x < n; x++){
                    grid[y][x] = row.charAt(x);
                    cnt[y][x] = -1;
                }
            }

            for(int y = 0; y < n; y++){
                for(int x = 0; x < n; x++){
                    if(grid[y][x] == '.'){
                        int tmp = 0;
                        for(int i = 0; i < 8; i++){
                            int nx = x + dx[i];
                            int ny = y + dy[i];

                            if(nx < 0 || ny < 0 || nx >= n || ny >= n){
                                continue;
                            }

                            if(grid[ny][nx] == '*') {
                                tmp++;
                            }
                        }
                        cnt[y][x] = tmp;
                    }
                }
            }

            int pop = 0;
            for(int y = 0; y < n; y++){
                for(int x = 0; x < n; x++){
                    if(!v[y][x] && cnt[y][x] == 0){
                        pop++;
                        Deque<Point> queue = new ArrayDeque<>();
                        queue.offer(new Point(x, y));
                        v[y][x] = true;
                        while (!queue.isEmpty()) {
                            Point p = queue.poll();
                            int px = p.x;
                            int py = p.y;

                            if(cnt[py][px] > 0){
                                continue;
                            }

                            for(int i = 0; i < 8; i++){
                                int nx = px + dx[i];
                                int ny = py + dy[i];

                                if(nx < 0 || ny < 0 || nx >= n || ny >= n){
                                    continue;
                                }

                                if(grid[ny][nx] == '.' && !v[ny][nx]) {
                                    v[ny][nx] = true;
                                    queue.offer(new Point(nx, ny));
                                }
                            }
                        }
                    }
                }
            }
            for(int y = 0; y < n; y++){
                for(int x = 0; x < n; x++){
                    if(!v[y][x] && cnt[y][x] > 0 && grid[y][x] == '.'){
                        pop++;
                    }
                }
            }

            ans.append(String.format("#%d %d\n", t, pop));
        }

        System.out.println(ans);
    }

    public static class Point{
        int x;
        int y;
        int c;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public Point(int x, int y, int c) {
            this.x = x;
            this.y = y;
            this.c = c;
        }
    }

    public static void printArr(char[][] grid, int[][] arr, int n) {
        for(int y = 0; y < n; y++){
            for(int x = 0; x < n; x++){
                System.out.print(grid[y][x] + " ");
            }
            System.out.println();
        }
        for(int y = 0; y < n; y++){
            for(int x = 0; x < n; x++){
                System.out.print(arr[y][x] + " ");
            }
            System.out.println();
        }
        System.out.println("----------");
    }
}
