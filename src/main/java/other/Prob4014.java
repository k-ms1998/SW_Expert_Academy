package other;

import java.io.*;
import java.util.*;

/**
 * 1. 높이가 다른 구간의 경우 활주로가 끊기기 때문에 경사로를 철치한다
 * 2. 경사로는 높이가 1, 가로가 x인 직각삼각형
 * -----
 * 1. 각 행과 열을 탐색
 * 2. 각 행의 맨위에서 부터 아래쪽으로 탐색 시작
 * 	2-1. 현재 칸의 높이와 다음 칸의 높이가 다른지 확인
 * 		2-1-1. 높이 차이가 -1일때:
 * 			2-1-1-1. 높이가 더 높은 칸에서 낮은 칸 방향으로 경사로 설치 시도(현재 칸 -> 다음 칸)
 * 			2-1-1-2. 다음 칸에서 시작해서 경사로의 길이 만큼 각 좌표들의 높이를 확인
 * 			2-1-1-3. 이때, 각 좌표의 높이가 (다음 칸)의 높이랑 같고 경사로가 놓인 상태가 아니면 경사로를 설치할 수 있다
 * 		2-1-2. 높이 차이가 1일때:
 * 			2-1-2-1. 높이가 더 낮은 칸에서 높은 칸 방향으로 경사로 설치 시도(현재 칸 -> 다음 칸)
 * 			2-1-2-2. 현재 칸에서 시작해서 경사로의 길이 만큼 각 좌표들의 높이를 확인 (이때, 확인 방향은 다음 칸 -> 현재 칸 방향으로 확인)
 * 			2-1-2-3. 이때, 각 좌표의 높이가 (현재 칸)의 높이랑 같고 경사로가 놓인 상태가 아니면 경사로를 설치할 수 있다
 * 		2-1-3. |높이 차이가| 1보다 클때:
 * 			2-1-3-1. 경사로를 놓을 수 없다
 * 3. 각 열에 대해서 똑같이 확인
 */
public class Prob4014 {

	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer st;
	static StringBuilder sb = new StringBuilder();
	
	static int size, slope;
	static int[][] grid;
	static int answer;
	
	static boolean[] possibleRow;
	static boolean[] possibleCol;
	
	public static void main(String[] args) throws IOException{
		int T = Integer.parseInt(br.readLine());
		
		for(int testCase  = 1; testCase <= T; testCase++) {
			st = new StringTokenizer(br.readLine());
			size = Integer.parseInt(st.nextToken());
			slope = Integer.parseInt(st.nextToken());
			possibleRow = new boolean[size];
			possibleCol = new boolean[size];

			
			grid = new int[size][size];
			for(int row = 0; row < size; row++) {
				st = new StringTokenizer(br.readLine());
				for(int col = 0; col < size; col++) {
					grid[row][col] = Integer.parseInt(st.nextToken());
				}
			}
			
			// 세로 - 남쪽 방향으로 확인
			checkVertical();
			checkHorizontal();
			
			answer = 0;
			for(int col = 0; col < size; col++) {
				if(possibleRow[col]) {
					answer++;
				}
			}
			for(int row = 0; row < size; row++) {
				if(possibleCol[row]) {
					answer++;
				}
			}
			
			sb.append(String.format("#%d %d\n", testCase, answer));
		}
		
		System.out.println(sb);
	}
	
	public static void checkVertical() {
		for(int col = 0; col < size; col++) {
			boolean flag = true;
			boolean[] placed = new boolean[size];
			for(int row = 0; row < size - 1; row++) {
				int curH = grid[row][col];
				int nextH = grid[row + 1][col];
				
				if(curH != nextH) {
					if(nextH - curH == -1) {
						for(int nextRow = row + 1; nextRow <= row + slope; nextRow++) {
							if(nextRow >= size) {
								flag = false;
								break;
							}
							if(grid[nextRow][col] != nextH || placed[nextRow]) {
								flag = false;
								break;
							}
						}
						if(flag) {
							for(int nextRow = row + 1; nextRow <= row + slope; nextRow++) {
								placed[nextRow] = true;
							}
						}
					}else if(nextH - curH == 1) {
						for(int nextRow = row; nextRow > row - slope; nextRow--) {
							if(nextRow < 0) {
								flag = false;
								break;
							}
							if(grid[nextRow][col] != curH || placed[nextRow]) {
								flag = false;
								break;
							}
						}
						
						if(flag) {
							for(int nextRow = row; nextRow > row - slope; nextRow--) {
								placed[nextRow] = true;
							}
						}
						
					} else {
						flag = false;
					}
					
					if(!flag) {
						break;
					}
				}
			}
			
			if(flag) {
				possibleRow[col] = true;
			}
		}
	}
	
	public static void checkHorizontal() {
		for(int row = 0; row < size; row++) {
			boolean flag = true;
			boolean[] placed = new boolean[size];
			for(int col = 0; col < size - 1; col++) {
				int curH = grid[row][col];
				int nextH = grid[row][col + 1];
				
				if(curH != nextH) {
					if(nextH - curH == -1) {
						for(int nextCol = col + 1; nextCol <= col + slope; nextCol++) {
							if(nextCol >= size) {
								flag = false;
								break;
							}
							if(grid[row][nextCol] != nextH  || placed[nextCol]) {
								flag = false;
								break;
							}
						}
						if(flag) {
							for(int nextCol = col + 1; nextCol <= col + slope; nextCol++) {
								placed[nextCol] = true;
							}
						}
					}else if(nextH - curH == 1) {
						for(int nextCol = col; nextCol > col - slope; nextCol--) {
							if(nextCol < 0) {
								flag = false;
								break;
							}
							if(grid[row][nextCol] != curH || placed[nextCol]) {
								flag = false;
								break;
							}
						}
						if(flag) {
							for(int nextCol = col; nextCol > col - slope; nextCol--) {
								placed[nextCol] = true;
							}
						}
					} else {
						flag = false;
					}
					
					if(!flag) {
						break;
					}
				}
			}
			
			if(flag) {
				possibleCol[row] = true;
			}
		}
	}
	
}