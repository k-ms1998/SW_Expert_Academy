package other;

import java.io.*;
import java.util.*;

/**
 * 1. 각 칸에는 팔고 있는 디저트의 종류를 저장하고 있다
 * 2. 카페들 사이에는 대각선 방향으로 움직일 수 있는 길들이 있다
 * 3. 어느한 카페에서 출발해서 대각선 방향으로 사각형 모형을 그리며 다시 출발한 카페로 돌아와야 한다
 *  3-1. 같은 숫자의 디저트를 팔고 있는 카페를 한번만 방문할 수 있다
 *  3-2. 하나의 카페만 방문하는 것은 불가능
 *  3-3. 왔던 길을 다시 돌아가는 것도 안된다
 *
 * Solution: 완전 탐색 + BackTracking
 * 각 좌표에서 대각선으로 움직여서 찾을 수 있는 모든 경로 찾기
 *
 */
public class Prob2105 {

    static int n;
    static int[][] cafe;
    static boolean[] used = new boolean[101];

    static final int[] dx = {1, -1, -1, 1};
    static final int[] dy = {1, 1, -1, -1};

    static int answer = 0;
    static final int INF = 100_000_000;

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();

        int T = Integer.parseInt(br.readLine());
        for(int testCase = 1; testCase <= T; testCase++){
            answer = 0;
            n = Integer.parseInt(br.readLine());
            cafe = new int[n][n];
            for(int row = 0; row < n; row++){
                st = new StringTokenizer(br.readLine());
                for(int col = 0; col < n; col++){
                    cafe[row][col] = Integer.parseInt(st.nextToken());
                }
            }

            for(int row = 0; row < n; row++){
                for(int col = 0; col < n; col++){
                    if((col + 1) < n && (col - 1) >= 0 && (row + 1) < n){
                        int curNum = cafe[row][col];
                        int[] dirCnt = {1, 0, 0, 0};
                        used[curNum] = true;
                        move0(col + dx[0], row + dy[0], 1, 0, dirCnt);
                        used[curNum] = false;
                    }
                }
            }

            sb.append(String.format("#%d %d\n", testCase, answer == 0 ? -1 : answer));
        }

        System.out.println(sb);
    }

    // dir = 0 방향으로 움직이기
    public static void move0(int col, int row, int depth, int dir, int[] dirCnt){
        if(col < 0 || col >= n || row < 0 || row >= n){
            return;
        }
        int curNum = cafe[row][col];
        if(used[curNum]){
            return;
        }

        used[curNum] = true;

        // dir = 0 방향으로 움직이기
        int nCol1 = col + dx[dir];
        int nRow1 = row + dy[dir];
        dirCnt[dir]++;
        move0(nCol1, nRow1, depth + 1, dir, dirCnt);
        dirCnt[dir]--;

        // dir = 1 방향으로 움직이기
        int nCol2 = col + dx[dir + 1];
        int nRow2 = row + dy[dir + 1];
        dirCnt[dir + 1]++;
        move1(nCol2, nRow2, depth + 1, dir + 1, dirCnt);
        dirCnt[dir + 1]--;

        used[curNum] = false;
    }

    public static void move1(int col, int row, int depth, int dir, int[] dirCnt){
        if(col < 0 || col >= n || row < 0 || row >= n){
            return;
        }
        int curNum = cafe[row][col];
        if(used[curNum]){
            return;
        }

        used[curNum] = true;

        // dir = 1 방향으로 움직이기
        int nCol1 = col + dx[dir];
        int nRow1 = row + dy[dir];
        dirCnt[dir]++;
        move1(nCol1, nRow1, depth + 1, dir, dirCnt);
        dirCnt[dir]--;

        // dir = 2 방향으로 움직이기
        int nCol2 = col + dx[dir + 1];
        int nRow2 = row + dy[dir + 1];
        dirCnt[dir + 1]++;
        move2(nCol2, nRow2, depth + 1, dir + 1, dirCnt);
        dirCnt[dir + 1]--;

        used[curNum] = false;
    }

    public static void move2(int col, int row, int depth, int dir, int[] dirCnt){
        if(col < 0 || col >= n || row < 0 || row >= n){
            return;
        }

        int curNum = cafe[row][col];
        if(used[curNum]){
            return;
        }

        used[curNum] = true;


        if(dirCnt[2] == dirCnt[0]){ // dir = 0 방향이랑 dir = 2 방향이랑 움직인 칸의 수가 같으면 dir = 3 방향으로 움직이기
            int nCol2 = col + dx[dir + 1];
            int nRow2 = row + dy[dir + 1];
            dirCnt[dir + 1]++;
            move3(nCol2, nRow2, depth + 1, dir + 1, dirCnt);
            dirCnt[dir + 1]--;
        }else { // dir = 2 방향으로 움직이기
            int nCol1 = col + dx[dir];
            int nRow1 = row + dy[dir];
            dirCnt[dir]++;
            move2(nCol1, nRow1, depth + 1, dir, dirCnt);
            dirCnt[dir]--;
        }

        used[curNum] = false;
    }

    public static void move3(int col, int row, int depth, int dir, int[] dirCnt){
        if(col < 0 || col >= n || row < 0 || row >= n){
            return;
        }
        if(dirCnt[3] == dirCnt[1]){ // dir = 1 방향이랑 dir = 3 방향이랑 움직인 칸의 수가 같으면 도착 지점에 도달 => 탐색 끝
            answer = Math.max(answer, depth);
            return;
        }
        int curNum = cafe[row][col];
        if(used[curNum]){
            return;
        }

        used[curNum] = true;

        // dir = 3 방향으로 움직이기
        int nCol1 = col + dx[dir];
        int nRow1 = row + dy[dir];
        dirCnt[dir]++;
        move3(nCol1, nRow1, depth + 1, dir, dirCnt);
        dirCnt[dir]--;

        used[curNum] = false;
    }

}
