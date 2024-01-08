package D2;

import java.io.*;
import java.util.*;

public class Prob1959 {
    public static void main(String args[]) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder ans = new StringBuilder();

        int T = Integer.parseInt(br.readLine());
        for(int t = 1; t <= T; t++){
            st = new StringTokenizer(br.readLine());
            int n = Integer.parseInt(st.nextToken());
            int m = Integer.parseInt(st.nextToken());

            int[] gridA = new int[n];
            int[] gridB = new int[m];

            st = new StringTokenizer(br.readLine());
            for(int i = 0; i < n; i++){
                gridA[i] = Integer.parseInt(st.nextToken());
            }

            st = new StringTokenizer(br.readLine());
            for(int i = 0; i < m; i++){
                gridB[i] = Integer.parseInt(st.nextToken());
            }

            if(n > m){
                int result = check(gridA, gridB);
                ans.append(String.format("#%d %d\n", t, result));
            }else{
                int result = check(gridB, gridA);
                ans.append(String.format("#%d %d\n", t, result));
            }
        }

        System.out.println(ans);
    }

    public static int check(int[] org, int[] move){
        int a = org.length;
        int b = move.length;
        int dif = a - b;

        int result = 0;
        for(int i = 0; i <= dif; i++){
            int tmp = 0;
            for(int j = 0; j < b; j++){
                tmp += org[i + j] * move[j];
            }
            result = Math.max(result, tmp);
        }

        return result;
    }
}