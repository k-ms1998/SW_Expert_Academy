package D3;

import java.io.*;
import java.util.*;

public class Prob5215 {

    static int answer = 0;

    public static void main(String args[]) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        StringBuilder ans = new StringBuilder();
        int totalT = Integer.parseInt(br.readLine());
        for(int t = 1; t <= totalT; t++){
            answer = 0;

            st = new StringTokenizer(br.readLine());
            int n = Integer.parseInt(st.nextToken());
            int l = Integer.parseInt(st.nextToken());

            int[] taste = new int[n];
            int[] cal = new int[n];

            for(int i = 0; i < n; i++){
                st = new StringTokenizer(br.readLine());
                taste[i] = Integer.parseInt(st.nextToken());
                cal[i] = Integer.parseInt(st.nextToken());
            }

            findAnswer(0, 0, 0, n, l, taste, cal, 0);
            ans.append(String.format("#%d %d\n", t, answer));
        }
        System.out.println(ans.toString());
    }


    private static void findAnswer(int depth, int cTaste, int cCal, int targetD, int targetC, int[] taste, int[] cal, int bit){
        if(cCal > targetC){
            return;
        }

        answer = Math.max(answer, cTaste);
        if(depth >= targetD){
            return;
        }

        findAnswer(depth + 1, cTaste, cCal, targetD, targetC, taste, cal, bit);
        findAnswer(depth + 1, cTaste + taste[depth], cCal + cal[depth], targetD, targetC, taste, cal, bit | (1 << depth));
    }
}