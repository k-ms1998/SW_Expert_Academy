package D3;
import java.io.*;
import java.util.*;

public class Prob1244{

    static int maxNum = 0;
    static boolean[][] visited;

    public static void main(String args[]) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        StringBuilder ans = new StringBuilder();

        int t = Integer.parseInt(br.readLine()); // 1 <= t <= 10
        int idx = 0;
        while(t-- > 0){
            idx++;
            maxNum = 0;
            st = new StringTokenizer(br.readLine());
            int num = Integer.parseInt(st.nextToken());
            int count = Integer.parseInt(st.nextToken());
            int size = findSize(num); // 1 <= size <= 6

            visited = new boolean[count + 1][1000000];
            int[] arr = numToArray(num, size);
            dfs(arr, count, size, 0, 0);
            ans.append(String.format("#%d %d", idx, maxNum)).append("\n");
        }

        System.out.println(ans);
    }

    public static void dfs(int[] arr, int target, int size, int idx, int depth){
        if(arr[0] == 0){
            return;
        }
        int num = 0;
        for(int i = 0; i < size; i++){
            num *= 10;
            num += arr[i];
        }
        if(visited[depth][num]){
            return;
        }
        visited[depth][num] = true;
        if(depth >= target){
            maxNum = Math.max(maxNum, num);

            return;
        }

        for(int i = 0; i < size; i++){
            for(int j = i + 1; j < size; j++){
                swap(i, j, arr);
                dfs(arr, target, size, idx, depth + 1);
                swap(i, j, arr);
            }
        }
    }

    public static int[] numToArray(int num, int size){
        int[] result = new int[size];
        for(int i = size - 1; i >= 0; i--){
            result[i] = num % 10;
            num /= 10;
        }

        return result;
    }

    public static int findSize(int num){
        int cnt = 0;

        int e = 1;
        while(e <= num){
            e *= 10;
            cnt++;
        }

        return cnt;
    }

    public static void swap(int i, int j, int[] arr){
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;

    }
}