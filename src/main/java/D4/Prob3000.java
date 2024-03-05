package D4;

import java.io.*;
import java.util.*;

public class Prob3000 {

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static StringBuilder sb = new StringBuilder();

    static int size;
    static PriorityQueue<Long> left;
    static PriorityQueue<Long> right;
    static long mid;

    static final int MOD = 20_171_109;

    public static void main(String[] args) throws IOException{
        int T = Integer.parseInt(br.readLine());
        for(int testCase = 1; testCase <= T; testCase++) {
            st = new StringTokenizer(br.readLine());
            size = Integer.parseInt(st.nextToken());
            long start = Long.parseLong(st.nextToken());

            left = new PriorityQueue<>(Collections.reverseOrder());
            right = new PriorityQueue<>();
            mid = start;

            long total = 0;
            for(int idx = 0; idx < size; idx++) {
                st = new StringTokenizer(br.readLine());
                long numA = Long.parseLong(st.nextToken());
                long numB = Long.parseLong(st.nextToken());

                if(mid < numA) {
                    right.offer(numA);
                }else {
                    left.offer(numA);
                }
                if(mid < numB) {
                    right.offer(numB);
                }else {
                    left.offer(numB);
                }

                balanceQueue();

                total += mid;
                total %= MOD;
            }

//			System.out.println("left=" + left);
//			System.out.println("right=" + right);
            sb.append(String.format("#%d %d\n", testCase, total));
        }

        System.out.println(sb);
    }

    public static void balanceQueue() {
        while(left.size() > right.size()) {
            long peekLeft = left.poll();
            right.add(mid);
            mid = peekLeft;
        }

        while(left.size() < right.size()) {
            long peekRight = right.poll();
            left.add(mid);
            mid = peekRight;
        }
    }

}

