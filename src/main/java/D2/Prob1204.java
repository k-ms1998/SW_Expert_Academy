package D2;

import java.util.Scanner;
import java.io.FileInputStream;

public class Prob1204
{
    public static void main(String args[]) throws Exception
    {
        Scanner sc = new Scanner(System.in);
        int T;
        T=sc.nextInt();

        for(int test_case = 1; test_case <= T; test_case++)
        {
            int t = sc.nextInt();
            int[] cnt = new int[1001];
            for(int i = 0; i < 1000; i++){
                int num = sc.nextInt();
                cnt[num]++;
            }
            int maxNum = 0;
            int maxCnt = 0;
            for(int i = 1000; i >= 0; i--){
                if(maxCnt <  cnt[i]){
                    maxNum = i;
                    maxCnt = cnt[i];
                }
            }
            System.out.println(String.format("#%d %d", t, maxNum));
        }
    }
}