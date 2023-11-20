package D4;

import java.io.*;
import java.util.*;

public class Prob3752 {

    static boolean[] used = new boolean[10_001];

    public static void main(String args[]) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        StringBuilder ans = new StringBuilder();
        int totalT = Integer.parseInt(br.readLine());
        for(int t = 1; t <= totalT; t++){
            int n = Integer.parseInt(br.readLine());
            used = new boolean[10_001];
            used[0] = true;

            st = new StringTokenizer(br.readLine());
            for(int i = 0; i < n; i++){
                int num = Integer.parseInt(st.nextToken());
                Deque<Integer> queue = new ArrayDeque<>();
                for(int j = 0; j < 10_001; j++){
                    if(used[j]){
                        queue.add(j);
                    }
                }

                while(!queue.isEmpty()){
                    int next = queue.poll() + num;
                    used[next] = true;
                }
            }

            int cnt = 0;
            for(int i = 0; i < 10_001; i++){
                if(used[i]){
                    cnt++;
                }
            }

            ans.append(String.format("#%d %d\n", t, cnt));
        }

        System.out.println(ans.toString());
    }
}