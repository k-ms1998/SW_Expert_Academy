package D3;

import java.io.*;
import java.util.*;

public class Prob1220 {

    public static void main(String args[]) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        StringBuilder ans = new StringBuilder();
        int totalT = 10;
        for(int t = 1; t <= totalT; t++){
            int n = Integer.parseInt(br.readLine());
            int[][] grid = new int[n][n];
            for(int y = 0; y < n; y++){
                st = new StringTokenizer(br.readLine());
                for(int x = 0; x < n; x++){
                    grid[y][x] = Integer.parseInt(st.nextToken());
                }
            }
            moveS(grid, n);
            moveN(grid, n);
            // printGrid(grid, n);
            int cnt = findAnswer(grid, n);

            ans.append(String.format("#%d %d\n", t, cnt));
        }

        System.out.println(ans.toString());
    }

    public static void moveS(int[][] grid, int n){ // s극에 의해 움직이기 -> 빨간색(1)이 아래로 이동
        for(int x = 0; x < n; x++){
            int stop = n;
            for(int y = n - 1; y >= 0; y--){
                if(grid[y][x] == 2){ // 파란색
                    stop = y;
                }
                if(grid[y][x] == 1){
                    grid[y][x] = 0;
                    if(stop != n){
                        grid[stop - 1][x] = 1;
                        stop = stop - 1;
                    }
                }
            }
        }
    }

    public static void moveN(int[][] grid, int n){
        for(int x = 0; x < n; x++){
            int stop = n - 1;
            for(int y = 0; y < n; y++){
                if(grid[y][x] == 1){ // 빨간색
                    stop = y;
                }
                if(grid[y][x] == 2){
                    grid[y][x] = 0;
                    if(stop != n - 1){
                        grid[stop + 1][x] = 2;
                        stop = stop + 1;
                    }
                }
            }
        }
    }

    public static void printGrid(int[][] grid, int n){
        for(int y = 0; y < n; y++){
            for(int x = 0; x < n; x++){
                System.out.print(grid[y][x] + " ");
            }
            System.out.println();
        }
        System.out.println("----------");
    }

    public static int findAnswer(int[][] grid, int n){
        int cnt = 0;
        for(int x = 0; x < n; x++){
            int cur = 0;
            for(int y = 0; y < n; y++){
                if(grid[y][x] == 1){
                    cur = 1;
                }else if(grid[y][x] == 2){
                    if(cur == 1){
                        cnt++;
                        cur = 0;
                    }
                }else{
                    cur = 0;
                }
            }
        }

        return cnt;
    }
}