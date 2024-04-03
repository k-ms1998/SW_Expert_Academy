package D4;

import java.io.*;
import java.util.*;

/**
 * 1. N개의 격자점이 있다
 * 2. 이 점들을 몇번 움직여 모두 원점으로 이동시키고 싶다
 * 3. 한번의 움직임은 모든 점을 움직이고, i번째 움직임에서는 각 점은 상하좌우로 i만큼의 거리를 반드시 이동한다
 * 4. 최소 몇 번의 움직임으로 모든 점을 원점에 모을 수 있는지 구하기
 * -----
 * 1. 모든 좌표들이 원점으로 부터 거리가 모두 홀수인지 짝수인지 확인
 * 	1-1. x에서 0으로 가는데 걸리는 시간 = 0에서 x로 가는데 걸리는 시간
 * 	1-2. 0 -> 1 -> -1 -> 2 -> -2 -> 3 -> -3 .... 원점에서 충분한 시간만 주어진다면서 어떤 숫자여도 만들 수 있음
 * 	1-3. 이때, 찾을려는 모든 숫자들이 모두 홀수 횟수에 도달 가능 또는 모두 짝수 횟수에 도달 가능해 서로 엇갈리지 않고 찾을 수 있음
 * 2. 1번을 만족시키면 각 좌표가 원점으로 돌아오는데 걸리는 시간을 확인
 * 	2-1. 가장 먼 점에서부터 N을 늘려가며 거리를 줄인다
 *  2-2. 가장 먼 점이 원점에 도착했을때, 남은 횟수가 짝수라면 바로 종료, 홀수라면 홀수번 움직여야 종료된다.
 * 
 */
public class Prob8458 {

	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer st;
	static StringBuilder sb = new StringBuilder();
	
	static int size;
	static int[] cols;
	static int[] rows;
	static int[] dist;
	static int answer;
	
	static final int INF = 1_000_000_000;
	static final int[] dCol = {1, 0, -1, 0};
	static final int[] dRow = {0, 1, 0, -1};
	
	public static void main(String[] args) throws IOException{
		int T = Integer.parseInt(br.readLine());
		
		for(int testCase  = 1; testCase <= T; testCase++) {
			size = Integer.parseInt(br.readLine());
			
			answer = 0;
			cols = new int[size];
			rows = new int[size];
			dist = new int[size];
			for(int idx = 0; idx < size; idx++) {
				st = new StringTokenizer(br.readLine());
				int col = Integer.parseInt(st.nextToken());
				int row = Integer.parseInt(st.nextToken());
				
				cols[idx] = col;
				rows[idx] = row;
				dist[idx] = Math.abs(cols[idx]) + Math.abs(rows[idx]);
			}
			
			// 모든 좌표들이 원점으로부터 거리가 모두 짝수이거나 홀수일때 원점으로 돌아올 수 있다
			if(isAllOdd() || isAllEven()) {
				int maxDist = 0;
				for(int idx = 0; idx < size; idx++) {
					maxDist = Math.max(maxDist, dist[idx]);
				}

			    while(maxDist > 0){
			        answer++;
			        maxDist -= answer;
			    }
			    answer = maxDist % 2 == 0 ? answer : answer % 2 == 0 ? answer + 1 : answer + 2;
				
			}else {
				answer = -1;
			}
			
			sb.append(String.format("#%d %d\n", testCase, answer));
		}
		
		System.out.println(sb);
	}
	
	public static boolean isAllOdd() {
		for(int idx = 0; idx < size; idx++) {
			if(dist[idx] % 2 == 0) {
				return false;
			}
		}
		
		return true;
	}
	
	public static boolean isAllEven() {
		for(int idx = 0; idx < size; idx++) {
			if(dist[idx] % 2 == 1) {
				return false;
			}
		}
		
		return true;
	}
}
