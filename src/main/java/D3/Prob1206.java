package D3;

import java.io.*;
import java.util.*;

public class Prob1206 {
    public static void main(String args[]) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        StringBuilder ans = new StringBuilder();
        for(int t = 1; t <= 10; t++){
            int n = Integer.parseInt(br.readLine());
            int[] b = new int[n + 2];
            st = new StringTokenizer(br.readLine());
            for(int i = 0; i < n; i++){
                b[i] = Integer.parseInt(st.nextToken());
            }
            b[n] = 0;
            b[n + 1] = 0;

            int cnt = 0;
            for(int x = 0; x < n; x++){
                if(b[x] > 0){
                    for(int y = b[x]; y >= 1; y--){
                        if(check(x, y, b)){
                            cnt++;
                        }else{
                            break;
                        }
                    }
                }
            }
            ans.append(String.format("#%d %d\n", t, cnt));
        }
        System.out.println(ans.toString());
    }

    public static boolean check(int x, int y, int[] b){
        return b[x-1] < y && b[x-2] < y && b[x+1] < y && b[x+2] < y;
    }
}