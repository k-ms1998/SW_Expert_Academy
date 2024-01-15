package D3;

import java.io.*;
import java.util.*;

public class Prob1289 {
    
    public static void main(String args[]) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder output = new StringBuilder();

        int totalCase = Integer.parseInt(br.readLine());
        for(int testCase = 1; testCase <= totalCase; testCase++){
            String target = br.readLine();
            int targetSize = target.length();

            int[] currentState = new int[targetSize];
            int[] targetState = new int[targetSize];
            for(int idx = 0; idx < targetSize; idx++){
                char curC = target.charAt(idx);
                if(curC == '1'){
                    targetState[idx] = 1;
                }
            }

            int cnt = 0;
            for(int idx = 0; idx < targetSize; idx++){
                if(currentState[idx] != targetState[idx]){
                    cnt++;
                    for(int nextIdx = idx; nextIdx < targetSize; nextIdx++){
                        currentState[nextIdx] = (currentState[nextIdx] + 1) % 2;
                    }
                }
            }


            output.append(String.format("#%d %d\n", testCase, cnt));
        }

        System.out.println(output);
    }
}