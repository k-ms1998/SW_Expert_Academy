package other;

import java.io.*;
import java.util.*;

/**
 * 1. 4개의 자석이 있고, 각 자석은 8개의 날을 가지고 있다
 * 	1-1. 각 날마다 N 또는 S 극의 자성을 가지고 있다
 * 2. 임의의 자석을 1칸 씩 총 k번 회전 시킨다
 * 	2-1. 1칸 회전될 때, 붙어 있는 자석은 서로 붙어 있는 날의 자성과 다를 경우에만 인력에 의해 반대 방향으로 1칸 회전된다
 */
public class Prob4013 {

	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer st;
	static StringBuilder sb = new StringBuilder();
	
	static int rotations;
	static int[][] magnets = new int[4][8]; // 북, 동북, 동, 동남, 남, 서남, 서, 서북 순으로 주어진다
	
	static int score = 0;
	static boolean[] moved;
	
	public static void main(String[] args) throws IOException{
		int T = Integer.parseInt(br.readLine());
		
		for(int testCase  = 1; testCase <= T; testCase++) {
			rotations = Integer.parseInt(br.readLine());
			score = 0;
			
			for(int idx = 0; idx < 4; idx++) {
				st = new StringTokenizer(br.readLine());
				for(int state = 0; state < 8; state++) {
					magnets[idx][state] = Integer.parseInt(st.nextToken());
				}
			}

			for(int rotation = 0; rotation < rotations; rotation++) {
				st = new StringTokenizer(br.readLine());
				int idx = Integer.parseInt(st.nextToken()) - 1;
				int direction = Integer.parseInt(st.nextToken());
				
				moved = new boolean[4];
				
				checkAndMoveRight(idx, idx + 1, direction);
				checkAndMoveLeft(idx, idx - 1, direction);
				moveMagnet(idx, direction);
			}
			for(int magnet = 0; magnet < 4; magnet++) {
				if(magnets[magnet][0] == 1) {
					score += (1 << magnet);
				}
			}
			
			sb.append(String.format("#%d %d\n", testCase, score));
		}
		
		System.out.println(sb);
	}
	
	public static void checkAndMoveRight(int prev, int idx, int direction) {
		if(idx < 0 || idx >= 4) {
			return;
		}
		
		if(magnets[prev][2] != magnets[idx][6]) {
			checkAndMoveRight(idx, idx + 1, -direction);
			moveMagnet(idx, -direction);
		}else {
			return;
		}
		
	}
	
	public static void checkAndMoveLeft(int prev, int idx, int direction) {
		if(idx < 0 || idx >= 4) {
			return;
		}
		
		if(magnets[prev][6] != magnets[idx][2]) {
			checkAndMoveLeft(idx, idx - 1, -direction);
			moveMagnet(idx, -direction);
		}else {
			return;
		}
		
	}
	
	public static void moveMagnet(int idx, int direction) {
		if(direction == 1) { // 시계 방향
			int tmp = magnets[idx][7];
			for(int pos = 7; pos >= 1; pos--) {
				magnets[idx][pos] = magnets[idx][pos - 1];
			}
			magnets[idx][0] = tmp;
			
		}else { // 반시계 방향
			int tmp = magnets[idx][0];
			for(int pos = 0; pos < 7; pos++) {
				magnets[idx][pos] = magnets[idx][pos + 1];
			}
			magnets[idx][7] = tmp;
		}
	}
	
	public static void printMagnet() {
		for(int magnet = 0; magnet < 4; magnet++) {
			for(int idx = 0; idx < 8; idx++) {
				System.out.print(magnets[magnet][idx] + " ");
			}
			System.out.println();
		}
		System.out.println("----------");
	}
}