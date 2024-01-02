package D2;

import java.util.*;
import java.io.*;

public class Prob12712
{
    static int[] dx1 = {0, 1, 0, -1};
    static int[] dy1 = {1, 0, -1, 0};
    static int[] dx2 = {1, 1, -1, -1};
    static int[] dy2 = {-1, 1, 1, -1};

    public static void main(String args[]) throws Exception
    {
        StringBuilder ans = new StringBuilder();
        Scanner sc = new Scanner(System.in);
        StringTokenizer st;
        int T;
        T=sc.nextInt();

        for(int test_case = 1; test_case <= T; test_case++)
        {
            int n = sc.nextInt();
            int m = sc.nextInt();
            int[][] grid = new int[n][n];
            for(int y = 0; y < n; y++){
                for(int x = 0; x < n; x++){
                    grid[y][x] = sc.nextInt();
                }
            }

            int answer = 0;
            for(int y = 0; y < n; y++){
                for(int x = 0; x < n; x++){
                    int tmp1 = grid[y][x];
                    int tmp2 = grid[y][x];
                    for(int d = 1; d < m; d++){
                        for(int i = 0; i < 4; i++){
                            int nx = x + d*dx1[i];
                            int ny = y + d*dy1[i];
                            if(nx < 0 || ny < 0 || nx >= n || ny >= n){
                                continue;
                            }

                            tmp1 += grid[ny][nx];
                        }
                        for(int i = 0; i < 4; i++){
                            int nx = x + d*dx2[i];
                            int ny = y + d*dy2[i];
                            if(nx < 0 || ny < 0 || nx >= n || ny >= n){
                                continue;
                            }

                            tmp2 += grid[ny][nx];
                        }
                    }
                    answer = Math.max(answer, Math.max(tmp1, tmp2));
                }
            }

            ans.append(String.format("#%d %d\n", test_case, answer));
        }

        System.out.println(ans);
    }
}
