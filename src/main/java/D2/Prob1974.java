package D2;

import java.io.*;
import java.util.*;

public class Prob1974 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder answer = new StringBuilder();

        int T = Integer.parseInt(br.readLine());
        for(int t = 1; t <= T; t++){
            int[][] grid = new int[9][9];
            for(int y = 0; y < 9; y++){
                st = new StringTokenizer(br.readLine());
                for(int x = 0; x < 9; x++){
                    grid[y][x] = Integer.parseInt(st.nextToken());
                }
            }

            boolean flag = checkSquare(grid) && checkV(grid) && checkH(grid);

            answer.append(String.format("#%d %d\n", t, flag ? 1 : 0));
        }

        System.out.println(answer);
    }

    public static boolean checkSquare(int[][] grid) {
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                int sy = i * 3;
                int sx = j * 3;
                boolean[] v = new boolean[10];
                for(int y = sy; y < sy + 3; y++){
                    for(int x = sx; x < sx + 3; x++){
                        int num = grid[y][x];
                        if(v[num]){
                            return false;
                        }else {
                            v[num] = true;
                        }
                    }
                }
            }
        }

        return true;
    }

    public static boolean checkV(int[][] grid) {
        for(int x = 0; x < 9; x++){
            boolean[] v = new boolean[10];
            for(int y = 0; y < 9; y++){
                int num = grid[y][x];
                if(v[num]){
                    return false;
                }else{
                    v[num] = true;
                }
            }
        }

        return true;
    }

    public static boolean checkH(int[][] grid) {
        for(int y = 0; y < 9; y++){
            boolean[] v = new boolean[10];
            for(int x = 0; x < 9; x++){
                int num = grid[y][x];
                if(v[num]){
                    return false;
                }else{
                    v[num] = true;
                }
            }
        }

        return true;
    }
}
