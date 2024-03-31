package D4;

import java.io.*;
import java.util.*;

/**
 * 1. 학생들을 비교한 결과들이 주어질때, 자신의 키가 몇 번째인지 알 수 있는 학생들을 계산하기
 * 	1-1. 학생이 자신의 키가 몇번재인지 알기 위해서는 자신보다 큰 학생들의 숫와 자신보다 작은 학생들의 수를 알고 있어야함
 * 	1-2. 플로이드-워셜을 통해 각 학생마다 자신이 다른 학생보다 키가 큰지 작은지 알 수 있음
 * 	1-3. 플로이드 워셜을 진행하면 k번째 학생에 대해서 자신보다 큰 학생들이 누구인지 파악 가능
 * 		1-3-1. k번째에서 시작해서 도달 가능한 학생들은 모두 자신보다 큰 학생들
 * 	1-4. k번재 학생보다 작은 학생들을 구하기 위해서는 자신보다 작은 학생들 중에서 k번재 학생에 도달 가능한 학생들
 * 		1-4-1. k번째보다 작을수도 있는 학생들는 k번째 학생에서 도달 불가능한 학생들
 *  1-5. 이를 통해 '(k번째 학생들이 도달 가능한 학생의 + k에 도달 가능한 학생의 수) + 1 = 총 학생의 수' 이면 k번째 학생은 본인이 몇번째로 큰지 알 수 있음
 *  	1-5-1. 위에서 1을 더하는 이유는 자기 자신을 포함시켜야 하기 때문에
 */
public class Prob5643 {

	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer st;
	static StringBuilder sb = new StringBuilder();
	
	static int size, compareCount;
	static List<Integer>[] edges;
	static List<Integer>[] edgesReverse;
	
	static int[] inCount;
	static int[] inCountReverse;
	
	static int[] ranks;
	static int[] ranksReverse;
	
	static final int INF = 1_000_000_000;
	
	public static void main(String[] args) throws IOException{
		int T = Integer.parseInt(br.readLine().trim());
		
		for(int testCase  = 1; testCase <= T; testCase++) {
			size = Integer.parseInt(br.readLine().trim());
			compareCount = Integer.parseInt(br.readLine().trim());
			
			edges = new List[size];
			edgesReverse = new List[size];
			inCount = new int[size];
			inCountReverse = new int[size];
			ranks = new int[size];
			ranksReverse = new int[size];
			for(int nodeA = 0; nodeA < size; nodeA++) {
				edges[nodeA] = new ArrayList<>();
				edgesReverse[nodeA] = new ArrayList<>();
			}
			
			for(int idx = 0; idx < compareCount; idx++) {
				st = new StringTokenizer(br.readLine());
				int shorter = Integer.parseInt(st.nextToken()) - 1;
				int taller = Integer.parseInt(st.nextToken()) - 1;
				
				edges[shorter].add(taller);
				edgesReverse[taller].add(shorter);
				
				inCount[taller]++;
				inCountReverse[shorter]++;
			}
			
			findOrder();
			findOrderReverse();
			
//			for(int idx = 0; idx < size; idx++) {
//				System.out.print(ranks[idx] + " ");
//			}
//			System.out.println();
//			for(int idx = 0; idx < size; idx++) {
//				System.out.print(ranksReverse[idx] + " ");
//			}
//			System.out.println();
			
			int answer = 0;
			for(int idx = 0; idx < size; idx++) {
				int count = 0;
				for(int next = 0; next < size; next++) {
					if(idx == next) {
						continue;
					}
					
					if((ranks[next] > ranks[idx] && ranksReverse[next] < ranksReverse[idx])
							|| (ranks[next] < ranks[idx] && ranksReverse[next] > ranksReverse[idx])) {
						count++;
					}
				}
				
//				System.out.printf("idx=%d, count=%d\n", idx, count);
				if(count + 1 == size) {
					answer++;
				}
			}
			
			sb.append(String.format("#%d %d\n", testCase, answer));
		}
		
		System.out.println(sb);
	}
	
	public static void findOrder() {
		Deque<Node> queue = new ArrayDeque<>();
		for(int idx = 0; idx < size; idx++) {
			if(inCount[idx] == 0) {
				queue.offer(new Node(idx, 0));
				ranks[idx] = 0;
			}
		}
		
		while(!queue.isEmpty()) {
			Node node = queue.poll();
			int num = node.num;
			int rank = node.rank;
			
			for(int next : edges[num]) {
				inCount[next]--;
				if(inCount[next] == 0) {
					ranks[next] = rank + 1;
					queue.offer(new Node(next, rank + 1));
				}
			}
		}
	}
	
	public static void findOrderReverse() {
		Deque<Node> queue = new ArrayDeque<>();
		for(int idx = 0; idx < size; idx++) {
			if(inCountReverse[idx] == 0) {
				queue.offer(new Node(idx, 0));
				ranksReverse[idx] = 0;
			}
		}
		
		while(!queue.isEmpty()) {
			Node node = queue.poll();
			int num = node.num;
			int rank = node.rank;
			
			for(int next : edgesReverse[num]) {
				inCountReverse[next]--;
				if(inCountReverse[next] == 0) {
					ranksReverse[next] = rank + 1;
					queue.offer(new Node(next, rank + 1));
				}
			}
		}
	}
	
	public static class Node{
		int num;
		int rank;
		
		public Node(int num, int rank) {
			this.num = num;
			this.rank = rank;
		}
	}
	
}
