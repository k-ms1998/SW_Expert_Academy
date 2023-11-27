package D4;
import java.util.*;
import java.io.*;

public class Prob4408
{

    public static void main(String args[]) throws Exception
    {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int T = Integer.parseInt(br.readLine());
        for(int test_case = 1; test_case <= T; test_case++)
        {
            int n = Integer.parseInt(br.readLine());

            int[] cnt = new int[201];
            for(int i = 0; i < n; i++){
                st = new StringTokenizer(br.readLine());
                int a = Integer.parseInt(st.nextToken());
                int b = Integer.parseInt(st.nextToken());
                if(a%2 == 1){
                    a++;
                }
                if(b%2 == 1){
                    b++;
                }
                int t1 = Math.min(a, b)/2;
                int t2 = Math.max(a, b)/2;

                for(int j = t1; j <= t2; j++){
                    cnt[j]++;
                }
                // cnt[t1]++;
                // cnt[t2]++;
            }

            int time = 1;
            for(int i = 1; i < 201; i++){
                time = Math.max(time, cnt[i]);
                // System.out.println(i + "->" + cnt[i]);
            }


            System.out.println(String.format("#%d %s", test_case, time));
        }
    }
}