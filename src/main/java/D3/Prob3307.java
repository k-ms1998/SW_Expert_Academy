package D3;
import java.io.*;
import java.util.*;

public class Prob3307 {
    public static void main(String args[]) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder output = new StringBuilder();

        int totalCase = Integer.parseInt(br.readLine());
        for(int testCase = 1; testCase <= totalCase; testCase++){
            st = new StringTokenizer(br.readLine());
            int n = Integer.parseInt(st.nextToken());

            int[] nums = new int[n];
            st = new StringTokenizer(br.readLine());
            for(int numsIdx = 0; numsIdx < n; numsIdx++){
                nums[numsIdx] = Integer.parseInt(st.nextToken());
            }

            int lastSequenceIdx = 0;
            int[] sequence = new int[n + 1];
            for(int numsIdx = 0; numsIdx < n; numsIdx++){
                int curNum = nums[numsIdx];
                if(lastSequenceIdx == 0){
                    sequence[lastSequenceIdx] = curNum;
                    lastSequenceIdx++;
                }else{
                    int lastNum = sequence[lastSequenceIdx - 1];
                    if(lastNum < curNum){
                        sequence[lastSequenceIdx] = curNum;
                        lastSequenceIdx++;
                    }else{
                        int left = 0;
                        int right = lastSequenceIdx;
                        while(left <= right){
                            int mid = (left + right) / 2;
                            if(sequence[mid] < curNum){
                                left = mid + 1;
                            }else{
                                right = mid - 1;
                            }
                        }
                        sequence[left] = curNum;
                    }
                }
            }

            output.append(String.format("#%d %d\n", testCase, lastSequenceIdx));
        }

        System.out.println(output);
    }
}