package D4;

import java.io.*;
import java.util.*;

/**
 * 1.N마리의 소들과 M마리의 말들을 키우고 있다 (1 ≤ N, M ≤ 500_000)
 *  1-1. 모든 소들은 (c1, 0) 직선상에 존재하고, 모든 말들은 (c2, 0) 직선상에 존재한다
 * 2. 모든 송와 말의 쌍에 대해서 가장 가까운 쌍의 거리와 이러한 최소 거리를 가지는 쌍의 개수 구하기
 * ------
 * 1. 이분탐색
 * 2. y좌표는 항상 0이고, 모든 소들은 x좌표가 c1이고 모든 말들은 x좌표가 c2이기 때문에 서로의 쌍에 대해서 거리는 z좌표만 영향이 있음
 * 3. 각 소와 각 말의 z좌표의 차이들중 가장 작은 값을 구하면됨
 * 4. 이때, 각 소에 대해서 모든 말들과 비교할 필요 없이 앞뒤로 가장 가까운 위치에 있는 말들하고만 비교하먄됨 (더 멀리 있는 말은 비교할 필요가 없음)
 * 5. 모든 말들과 소들의 위치를 오름차순으로 정렬
 * 6. 이분탐색(BinarySearch)를 이용해서 현재 소가 말들의 위치 중 어디에 들어갈 수 있는지 확인
 *  6-1. 말들 중에서 현재 소보다 z좌표가 더 큰 말들 중에서 처음으로 나타나는 말의 바로 직전이 소가 들어갈 수 있는 위치
 * 7. 해당 위치에 넣었을때, 해당 위치, 바로 직전의 위치, 바로 다음의 위치에 있는 말들의 좌표를 보고 거리의 차이를 구해서 PQ에 넣는다
 * 8. PQ에서 가장 처음 값이 최소 거리
 * 9. PQ를 탐색하면서 해당 값을 가진 값들의 개수를 구한다
 */
public class Prob8898{

    static BufferedReader br =new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static StringBuilder sb = new StringBuilder();

    static int cowsCount, horsesCount;
    static int c1, c2;
    static List<Integer> cows;
    static List<Integer> horses;

    public static void main(String[]args) throws IOException{
        int T = Integer.parseInt(br.readLine());

        for(int testCase =  1; testCase <= T; testCase++){
            st = new StringTokenizer(br.readLine());
            cowsCount = Integer.parseInt(st.nextToken());
            horsesCount = Integer.parseInt(st.nextToken());

            st = new StringTokenizer(br.readLine());
            c1 = Integer.parseInt(st.nextToken());
            c2 = Integer.parseInt(st.nextToken());

            cows = new ArrayList<>();
            st = new StringTokenizer(br.readLine());
            for(int idx = 0; idx < cowsCount; idx++){
                cows.add(Integer.parseInt(st.nextToken()));
            }
            Collections.sort(cows);

            horses = new ArrayList<>();
            st = new StringTokenizer(br.readLine());
            for(int idx = 0; idx < horsesCount; idx++){
                horses.add(Integer.parseInt(st.nextToken()));
            }
            Collections.sort(horses);

            PriorityQueue<Integer> queue = new PriorityQueue<>();
            for(int idx = 0; idx < cowsCount; idx++){
                int cow = cows.get(idx);

                int insertIdx = Math.abs(Collections.binarySearch(horses, cow)) - 1;

                if(0 <= insertIdx && insertIdx < horsesCount){
                    queue.offer(Math.abs(cow - horses.get(insertIdx)));
                }
                if(insertIdx - 1 >= 0){
                    queue.offer(Math.abs(cow - horses.get(insertIdx - 1)));
                }
                if(insertIdx + 1 < horsesCount){
                    queue.offer(Math.abs(cow - horses.get(insertIdx + 1)));
                }
            }

            int min = queue.peek();
            int count = 0;
            while(!queue.isEmpty()){
                int curMin = queue.poll();
                if(curMin == min){
                    count++;
                }else{
                    break;
                }
            }

            sb.append(String.format("#%d %d %d\n", testCase, (min + (Math.abs(c1 -c2))), count));
        }

        System.out.println(sb);
    }

}
