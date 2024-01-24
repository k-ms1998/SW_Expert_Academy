package other;

import java.io.*;
import java.util.*;

/**
 * 가장 적은 비용으로 수영장을 이용할 수 있는 방법 찾기
 * 1. 수영장에서 판매하고 있는 4가지 종류
 *  1-1. 1일 이용권 -> 1일 이용 가능
 *  1-2. 1달 이용권 -> 1달 이용 가능. 매달 1일부터 시작
 *  1-3. 3달 이용권 -> 연속된 3달 이용 가능. 매달 1일부터 시작
 *                      (11월 또느 12월에도 구매가능하지만, 다음해로 이월되지는 않음)
 *  1-4. 1년 이용권 -> 1년 이용 가능. 매년 1월 1일부터 시작
 * 2. 각 달의 이용 계획이 주어진다
 *  2-1. 이용 계획에 나타나느 숫자는 해당 달에 수용장을 이용할 날의 수를 의미함
 */
public class Prob1952 {

    static int[] price = new int[4]; // 0=1일 이용권, 1=1달 이용권, 2=3달 이용권, 3=1년 이용권
    static int[] plan = new int[13];

    public static void main(String args[]) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();

        int T = Integer.parseInt(br.readLine());
        for(int testCase = 1; testCase <= T; testCase++){
            // 이용권의 가격 입력 받기
            price = new int[4];

            st = new StringTokenizer(br.readLine());
            for(int pIdx = 0; pIdx < 4; pIdx++){
                price[pIdx] = Integer.parseInt(st.nextToken());
            }

            // 12개월 이용 계획
            int dayCount = 0;
            int monthCount = 0;
            plan = new int[13];
            st = new StringTokenizer(br.readLine());
            for(int pIdx = 1; pIdx <= 12; pIdx++){
                plan[pIdx] = Integer.parseInt(st.nextToken());
                if(plan[pIdx] > 0){
                    dayCount += plan[pIdx];
                    monthCount++;
                }
            }

            int[] dpCost = new int[13];
            int maxCost = Math.min(dayCount * price[0], Math.min(monthCount * price[1], Math.min(4 * price[2], price[3])));
            dpCost[1] = Math.min(price[0] * plan[1], price[1]);
            dpCost[2] = Math.min(price[0] * plan[2], price[1]) + dpCost[1];

            for(int month = 3; month <= 12; month++){
                dpCost[month] = Math.min(dpCost[month - 3] + price[2],
                        Math.min(dpCost[month - 1] + price[0] * plan[month], dpCost[month - 1] + price[1])
                );
            }

            sb.append(String.format("#%d %d\n", testCase, Math.min(dpCost[12], maxCost)));
        }

        System.out.println(sb);
    }

}