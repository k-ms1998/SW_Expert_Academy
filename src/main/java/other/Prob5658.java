package other;

import java.util.Scanner;
import java.io.FileInputStream;
import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Deque;
import java.util.ArrayDeque;
import java.util.Collections;

public class Prob5658
{
    public static void main(String args[]) throws Exception
    {

        Scanner sc = new Scanner(System.in);
        int T;
        T=sc.nextInt();

        for(int test_case = 1; test_case <= T; test_case++)
        {
            int n = sc.nextInt();
            int k = sc.nextInt();
            String row = sc.next();

            Set<Integer> set = new HashSet<>();
            Deque<Integer> queue = new ArrayDeque<>();
            for(int i = 0; i < n; i++){
                char c = row.charAt(i);
                if(c == 'A'){
                    queue.offer(10);
                }else if(c == 'B'){
                    queue.offer(11);
                }else if(c == 'C'){
                    queue.offer(12);
                }else if(c == 'D'){
                    queue.offer(13);
                }else if(c == 'E'){
                    queue.offer(14);
                }else if(c == 'F'){
                    queue.offer(15);
                }else{
                    queue.offer(Integer.parseInt(String.valueOf(c)));
                }
            }
            int blockSize = n / 4;
            int[] tmp = new int[blockSize + 1];
            int exp = 1;
            for(int i = 0; i < blockSize + 1; i++){
                tmp[i] = exp;
                exp *= 16;
            }
            for(int i = 0; i < n; i++){
                for(int j = 0; j < 4; j++){
                    int cur = 0;
                    for(int r = 0; r < blockSize; r++){
                        int num = queue.pollLast();
                        cur += num*tmp[r];
                        queue.offerFirst(num);
                    }
                    set.add(cur);
                }
                int num = queue.pollLast();
                queue.offerFirst(num);
            }
            List<Integer> list = new ArrayList<>();
            for(int num : set){
                list.add(num);
            }
            Collections.sort(list, Collections.reverseOrder());
            int answer = list.get(k - 1);
            //System.out.println(list);
            System.out.println(String.format("#%d %d", test_case, answer));
        }
    }
}