import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 1. 얇은 투명막 D장을 쌓아서 제작 -> 각 막은 W개의 셀들을 붙여서 만듬
 * 	1-1. 각 보호필름은 D x W의 크기가된다(D = 세로, W = 가로)
 * 	1-2. 각 셀은 A 또는 B 특성을 가진다
 * 		1-2-1. 셀들의 특성이 어떻게 배치됨에 따라 보호필름의 성능이 결정된다
 * 2. 세로 방향으로 충격을 가하기
 * 	2-1. 세로 방향으로 k만큼 충격을 가했을때 모든 가로 줄에 동일한 특성의 셀이 k개 이상이 연속으로 있어야함
 * 3. 세로 줄에 약품을 넣을 수 있음
 * 	3-1. 약품 A -> 해당 줄의 모든 특성이 A로 변함 -> 0
 * 	3-1. 약품 B -> 해당 줄의 모든 특성이 B로 변함 -> 1
 * 4. 테스트를 통과하기 위해 필요한 최소한의 약품을 출력
 * 
 * 풀이: 완전탐색
 */
public class Solution {
	
	static int d, w, k;
	static int[] film;

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();
		
		int T = Integer.parseInt(br.readLine());
		for(int testCase = 1; testCase <= T; testCase++) {
			st = new StringTokenizer(br.readLine());
			d = Integer.parseInt(st.nextToken());
			w = Integer.parseInt(st.nextToken());
			k = Integer.parseInt(st.nextToken());
			
			film = new int[d];
			for(int row = 0; row < d; row++) {
				st = new StringTokenizer(br.readLine());
				int bit = 0;
				for(int col = 0; col < w; col++) {
					int num = Integer.parseInt(st.nextToken());
					if(num == 1) {
						bit |= (1 << col);
					}
				}
				film[row] = bit;
			}
			
			int answer = d;
			for(int target = 0; target < d - 1; target++) {
				if(dfs(0, 0, target)) {
					answer = target;
					break;
				}
			}
			sb.append(String.format("#%d %d\n", testCase, answer));
		}
		
		System.out.println(sb);
	}
	
	public static boolean dfs(int curRow, int count, int target) {
		if(count >= target) {
			if(checkFilm()){
//				printFilm();
				return true;
			}
			
			return false;
		}
		
		for(int row = curRow; row < d; row++) {
			int tmpBit = film[row];
			
			// 현재 row에 A 약품을 투입
			film[row] = 0;
			if(dfs(row + 1, count + 1, target)) {
				return true;
			}
			film[row] = tmpBit;
			
			// 현재 row에 B 약픔을 투입
			film[row] = (1 << w) - 1;
			if(dfs(row + 1, count + 1, target)) {
				return true;
			}
			film[row] = tmpBit;
		}
		
		return false;
	}
	
	public static boolean checkFilm() {
		for(int col = 0; col < w; col++) {
			int prev = (film[0] & (1 << col)) == (1 << col) ? 1 : 0;
			int cnt = 1;
			boolean failed = true;
			for(int row = 1; row < d; row++) {
				int cur = (film[row] & (1 << col)) == (1 << col) ? 1 : 0;
				if(prev == cur) {
					cnt++;
				}else {
					if(cnt >= k) {
						failed = false;
						break;
					}
					prev = cur;
					cnt = 1;
				}
			}
			if(cnt >= k) {
				failed = false;
			}
			if(failed) {
				return false;
			}
		}
		
		return true;
	}
	
	public static void printFilm() {
		for(int row = 0; row < d; row++) {
			for(int col = 0; col < w; col++) {
				System.out.print(((film[row] & (1 << col)) == (1 << col) ? 1 : 0) + " ");
			}
			System.out.println();
		}
		System.out.println("-----------");
	}

}