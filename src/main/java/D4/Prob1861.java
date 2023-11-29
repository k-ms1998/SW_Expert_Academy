package D4;

import java.io.*;
import java.util.*;

public class Prob1861 {

    static int[] dx = {1, 0, -1, 0};
    static int[] dy = {0, 1, 0, -1};
    static int[][] dist;

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        StringBuilder ans = new StringBuilder();
        int totalT = Integer.parseInt(br.readLine());
        for(int t = 1; t <= totalT; t++){
            int n = Integer.parseInt(br.readLine());

            int[][] grid = new int[n][n];
            dist = new int[n][n];
            for(int y = 0; y < n; y++){
                st = new StringTokenizer(br.readLine());
                for(int x = 0; x < n; x++){
                    grid[y][x] = Integer.parseInt(st.nextToken());
                    dist[y][x] = 1;
                }
            }
            for(int y = 0; y < n; y++){
                for(int x = 0; x < n; x++){
                    dfs(x, y, grid, 1, n);
                }
            }

            int ansNum = Integer.MAX_VALUE;
            int maxCnt = 0;
            for(int y = 0; y < n; y++){
                for(int x = 0; x < n; x++){
                    if(maxCnt == dist[y][x]){
                        if(ansNum > grid[y][x]){
                            ansNum = grid[y][x];
                        }
                    }else if(maxCnt < dist[y][x]){
                        maxCnt = dist[y][x];
                        ansNum = grid[y][x];
                    }
                }
            }
            ans.append(String.format("#%d %d %d\n", t, ansNum, maxCnt));
        }

        System.out.println(ans);
    }

    public static int dfs(int x, int y, int[][] grid, int cnt, int n) {
        if(dist[y][x] > 1){
            return dist[y][x];
        }

        for(int i = 0; i < 4; i++){
            int nx = x + dx[i];
            int ny = y + dy[i];

            if(nx < 0 || ny < 0 || nx >= n || ny >= n){
                continue;
            }

            if(grid[ny][nx] - grid[y][x] == 1){
                dist[y][x] += dfs(nx, ny, grid, cnt + 1, n);
            }
        }

        return dist[y][x];
    }

}