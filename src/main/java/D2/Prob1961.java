package D2;

import java.io.*;
import java.util.*;

public class Prob1961 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder ans = new StringBuilder();

        int T = Integer.parseInt(br.readLine());
        for(int t = 1; t <= T; t++){
            int n = Integer.parseInt(br.readLine());
            int[][] grid = new int[n][n];
            for(int y = 0; y < n; y++){
                st = new StringTokenizer(br.readLine());
                for(int x = 0; x < n; x++){
                    grid[y][x] = Integer.parseInt(st.nextToken());
                }
            }
            String[][] tmp = new String[n][3];
            for(int i = 0; i < 3; i++){
                grid = rotate(grid, n);
                for(int y = 0; y < n; y++){
                    String cur = "";
                    for(int x = 0; x < n; x++){
                        cur += String.valueOf(grid[y][x]);
                    }
                    tmp[y][i] = cur;
                }
            }

            ans.append(String.format("#%d\n", t));
            for(int y = 0; y < n; y++){
                for(int i = 0; i < 3; i++){
                    ans.append(tmp[y][i]).append(" ");
                }
                ans.append("\n");
            }

        }

        System.out.println(ans);
    }

    public static int[][] rotate(int[][] grid, int n){
        int[][] tmp = new int[n][n];
        for(int y = 0; y < n; y++){
            for(int x = 0; x < n; x++){
                int ny = x;
                int nx = n - 1- y;
                tmp[ny][nx] = grid[y][x];
            }
        }

        return tmp;
    }
}
