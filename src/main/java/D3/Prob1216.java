package D3;

import java.io.*;
import java.util.*;

public class Prob1216 {

    public static void main(String args[]) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        StringBuilder ans = new StringBuilder();
        for(int i = 0; i < 10; i++){
            int t = Integer.parseInt(br.readLine());
            char[][] grid = new char[100][100];
            for(int y = 0; y < 100; y++){
                String row = br.readLine();
                for(int x = 0; x < 100; x++){
                    grid[y][x] = row.charAt(x);
                }
            }

            int cnt = 1;
            int left = 1;
            int right = 100;
            while(left <= right){
                int mid = (left + right) / 2;
                if(found(grid, mid - 1)){
                    left = mid + 1;
                }else{
                    right = mid - 1;
                }
            }

            cnt = left;
            ans.append(String.format("#%d %d\n", t, cnt));
        }

        System.out.println(ans);
    }

    public static boolean found(char[][] grid, int target){
        for(int y = 0; y < 100; y++){
            if(y + target >= 100){
                break;
            }
            for(int x = 0; x < 100; x++){
                int nx = x + target;
                if(nx >= 100){
                    break;
                }
                if(checkX(grid, y, x, nx)){
                    return true;
                }

                int ny = y + target;
                if(checkY(grid, x, y, ny)){
                    return true;
                }
            }
        }

        return false;
    }

    public static boolean checkX(char[][] grid, int y, int x, int nx){
        while(x <= nx){
            char a = grid[y][x];
            char b = grid[y][nx];
            if(a == b){
                x++;
                nx--;
            }else{
                return false;
            }
        }

        return true;
    }

    public static boolean checkY(char[][] grid, int x, int y, int ny){
        while(y <= ny){
            char a = grid[y][x];
            char b = grid[ny][x];
            if(a == b){
                y++;
                ny--;
            }else{
                return false;
            }
        }

        return true;
    }
}