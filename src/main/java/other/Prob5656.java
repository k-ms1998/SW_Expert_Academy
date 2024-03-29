package other;

import java.io.*;
import java.util.*;

/**
 * 1. 구슬을 N번 쏘아 벽돌을 깨뜨리는 게임
 * 	1-0. 1 <= N <= 4, 2 <= W <= 12, 2 <= H <= 15
 * 	1-1. 구슬은 좌, 우로만 움직일 수있음
 * 	1-2. 항상 맨 위에 있는 벽돌만 깨트릴 수 있다
 * 2. 명중한 벽돌은 상하좌우로 (벽돌에 적힌 숫자 - 1) 만큼 같이 제거된다
 * 	2-1. 벽돌 밑에 빈 공간이 있을 경우 밑으로 떨어진다
 */
public class Prob5656 {

	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer st;
	static StringBuilder sb = new StringBuilder();
	
	static int totalShoot, height, width;
	static int[][] grid;
	static int answer;
	
	static final int INF = 1_000_000_000;
	static final int[] dCol = {1, 0, -1, 0};
	static final int[] dRow = {0, 1, 0, -1};
	
	public static void main(String[] args) throws IOException{
		int T = Integer.parseInt(br.readLine());
		
		for(int testCase  = 1; testCase <= T; testCase++) {
			st = new StringTokenizer(br.readLine());
			totalShoot = Integer.parseInt(st.nextToken());
			width = Integer.parseInt(st.nextToken());
			height = Integer.parseInt(st.nextToken());
			answer = INF;
			
			grid = new int[height][width];
			for(int row = 0; row < height; row++) {
				st = new StringTokenizer(br.readLine());
				for(int col = 0; col < width; col++) {
					grid[row][col] = Integer.parseInt(st.nextToken());
				}
			}
			
			dfs(0);
			
			sb.append(String.format("#%d %d\n", testCase, answer));
		}
		
		System.out.println(sb);
	}
	
	public static void dfs(int depth) {
		if(depth == totalShoot) {
			int cnt = 0;
			for(int row = 0; row < height; row++) {
				for(int col = 0; col < width; col++) {
					if(grid[row][col] != 0) {
						cnt++;
					}
				}
			}
			answer = Math.min(answer, cnt);
			
			return;
		}
		
		int[][] gridCopy = copy();
		
		for(int col = 0; col < width; col++) {
			int row = shoot(col);
			int num = grid[row][col];
			
			remove(new Block(col, row, num));
			gravity();
			dfs(depth + 1);
			revert(gridCopy);
		}
		
	}
	
	public static void remove(Block initBlock) {
		Deque<Block> queue = new ArrayDeque<>();
		queue.offer(initBlock);
		grid[initBlock.row][initBlock.col] = 0;
		
		while(!queue.isEmpty()) {
			Block block = queue.poll();
			int col = block.col;
			int row = block.row;
			int num = block.num;
			
			for(int dir = 0; dir < 4; dir++) {
				for(int cnt = 1; cnt < num; cnt++) {
					int nCol = col + cnt * dCol[dir];
					int nRow = row + cnt * dRow[dir];
					
					if(nCol < 0 || nRow < 0 || nCol >= width || nRow >= height) {
						continue;
					}
					
					if(grid[nRow][nCol] != 0) {
						queue.offer(new Block(nCol, nRow, grid[nRow][nCol]));
						grid[nRow][nCol] = 0;
					}
				}
			}
		}
		
	}
	
	public static void gravity() {
		for(int col = 0; col < width; col++) {
			for(int row = height - 1; row >= 0; row--) {
				if(grid[row][col] != 0) {
					int num = grid[row][col];
					int len = 1;
					while(true) {
						int nRow = row + len;
						if(nRow >= height) {
							len--;
							break;
						}
						if(grid[nRow][col] != 0) {
							len--;
							break;
						}
						len++;
					}
					if(len == 0) {
						continue;
					}
					
					grid[row + len][col] = num;
					grid[row][col] = 0;
				}
			}
		}
	}
	
	public static int shoot(int col) {
		for(int row = 0; row < height; row++) {
			if(grid[row][col] != 0) {
				return row;
			}
		}
		
		return height - 1;
	}
	
	public static int[][] copy(){
		int[][] output = new int[height][width];
		
		for(int row = 0; row < height; row++) {
			for(int col = 0; col < width; col++) {
				output[row][col] = grid[row][col];
			}
		}
		
		return output;
	}
	
	public static void revert(int[][] gridCopy) {
		for(int row = 0; row < height; row++) {
			for(int col = 0; col < width; col++) {
				grid[row][col] = gridCopy[row][col];
			}
		}
	}
	
	public static class Block{
		int col;
		int row;
		int num;
		
		public Block(int col, int row, int num) {
			this.col = col;
			this.row = row;
			this.num = num;
		}
	}
	
	public static void printGrid() {
		for(int row = 0; row < height; row++) {
			for(int col = 0; col < width; col++) {
				System.out.print(grid[row][col] + " ");
			}
			System.out.println();
		}
		System.out.println("----------");
	}
}
/*
1
3 10 10
0 0 0 0 0 0 0 0 0 0
3 0 1 0 1 0 0 0 0 0
3 0 3 0 1 1 0 0 0 1
1 1 1 0 1 2 0 0 0 9
1 1 4 0 1 1 0 0 1 1
1 1 4 1 1 1 2 1 1 1
1 1 5 1 1 1 1 2 1 1
1 1 6 1 1 1 1 1 2 1
1 1 1 1 1 1 1 1 1 5
1 1 7 1 1 1 1 1 1 1

1
3 10 10
0 0 0 0 0 0 0 0 0 0
1 0 1 0 1 0 0 0 0 0
1 0 3 0 1 1 0 0 0 1
1 1 1 0 1 2 0 0 0 9
1 1 4 0 1 1 0 0 1 1
1 1 4 1 1 1 2 1 1 1
1 1 5 1 1 1 1 2 1 1
1 1 6 1 1 1 1 1 2 1
1 1 1 1 1 1 1 1 1 5
1 1 7 1 1 1 1 1 1 1
*/
