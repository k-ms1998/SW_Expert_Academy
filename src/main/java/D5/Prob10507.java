package D5;

import java.io.*;
import java.util.*;

public class Prob10507 {

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static StringBuilder ans = new StringBuilder();

    static int size, p;
    static int lastDay;
    static boolean[] studied;

    public static void main(String args[]) throws IOException{
        int T = Integer.parseInt(br.readLine());

        for(int t = 1; t <= T; t++){
            st = new StringTokenizer(br.readLine());
            size = Integer.parseInt(st.nextToken());
            p = Integer.parseInt(st.nextToken());

            lastDay = 0;
            studied = new boolean[1_000_001];
            st = new StringTokenizer(br.readLine());
            for(int idx = 0; idx < size; idx++){
                int day = Integer.parseInt(st.nextToken());
                studied[day] = true;

                lastDay = Math.max(lastDay, day);
            }

            int answer = p + 1;
            int left = 1;
            int right = 1;
            int curDays = 0;
            while(right < lastDay + 1){
                if(studied[right]){
                    curDays++;
                    right++;
                    answer = Math.max(answer, curDays);
                }else{
                    if(p == 0){
                        answer = Math.max(answer, curDays);
                        if(!studied[left]){
                            p++;
                        }
                        left++;
                        curDays--;
                    }else{
                        p--;
                        curDays++;
                        right++;
                    }
                }
            }

            ans.append(String.format("#%d %d\n", t, answer));
        }

        System.out.println(ans);
    }

}