package D4;

import java.io.*;
import java.util.*;

public class Prob3304 {

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static StringBuilder sb = new StringBuilder();

    static String strA, strB;

    static int lenA, lenB;
    static int[][] dp;

    public static void main(String[] args) throws IOException{
        int T = Integer.parseInt(br.readLine());
        for(int testCase = 1; testCase <= T; testCase++) {
            st = new StringTokenizer(br.readLine());
            strA = st.nextToken();
            strB = st.nextToken();

            lenA = strA.length();
            lenB = strB.length();

            dp = new int[lenA + 1][lenB + 1];
            for(int row = 1; row < lenA + 1; row++) {
                char cA = strA.charAt(row - 1);
                for(int col = 1; col < lenB + 1; col++) {
                    char cB = strB.charAt(col - 1);
//					System.out.println("cA=" + cA + ", cB=" + cB);
                    if(cA == cB) {
                        dp[row][col] = Math.max(dp[row][col], dp[row - 1][col - 1] + 1);
                    }else {
                        dp[row][col] = Math.max(dp[row - 1][col], dp[row][col - 1]);
                    }
                }
            }

            sb.append(String.format("#%d %d\n", testCase, dp[lenA][lenB]));
        }

        System.out.println(sb);
    }

}
