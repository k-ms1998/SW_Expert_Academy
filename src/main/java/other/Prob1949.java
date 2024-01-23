package other;

import java.io.*;
import java.util.*;

/**
 * 1. N x N의 부지에 등산소를 만들 계획 -> 최대한 긴 등산로를 만드는 것이 목표
 * 2. 등산로는 다음 규칙에 의해 만들어진다
 *  2-1. 등산로는 가장 높은 봉우리에서 시작
 *  2-2. 등산로는 산으로 올라갈 수있도록 반드시 높은 지형에서 낮은 지형으로 가로 또는 세로 방향으로 연결굄
 *  2-3. 긴 등산로를 만들기 위해 딱 한 곳을 정해서 최대 k 깊이 만큼 지형을 깎는 공사를 할 수 있다
 *
 * 풀이:
 * 1. 가장 높은 봉우리의 높이를 찾고 해당 높이의 봉우리들을 찾는다
 */
public class Prob1949 {

    static int n, k;
    static int[][] map;
    static boolean[][] visited;

    static final int[] dx = {1, 0,-1, 0};
    static final int[] dy = {0, 1, 0, -1};
    static final int INF = 100_000_000;

    public static void main(String args[]) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder output = new StringBuilder();

        int T = Integer.parseInt(br.readLine().trim());
        for(int testCase = 1; testCase <= T; testCase++){
            st = new StringTokenizer(br.readLine().trim());
            n = Integer.parseInt(st.nextToken());
            k = Integer.parseInt(st.nextToken());

            int maxH = 0;
            map = new int[n][n];
            visited = new boolean[n][n];
            for(int row = 0; row < n; row++){
                st = new StringTokenizer(br.readLine().trim());
                for(int col = 0; col < n; col++){
                    map[row][col] = Integer.parseInt(st.nextToken());
                    maxH = Math.max(maxH, map[row][col]);
                }
            }

            int answer = 1;
            for(int row = 0; row < n; row++){
                for(int col = 0; col < n; col++){
                    if(map[row][col] == maxH){
                        visited[row][col] = true;
                        answer = Math.max(answer, dfs(col, row, 0, 1, map[row][col]));
                        visited[row][col] = false;
                    }
                }
            }

            output.append(String.format("#%d %d\n", testCase, answer));
        }

        System.out.println(output);
    }

    public static int dfs(int x, int y, int drill, int d, int prevH){
        int curD = d;
        for(int dir = 0; dir < 4; dir++){
            int nx = x + dx[dir];
            int ny = y + dy[dir];

            if(nx < 0 || ny < 0 || nx >= n || ny >= n){
                continue;
            }
            if(visited[ny][nx]){
                continue;
            }
            if(prevH > map[ny][nx]){
                visited[ny][nx] = true;
                curD = Math.max(curD, dfs(nx, ny, drill, d + 1, map[ny][nx]));
                visited[ny][nx] = false;
            }else{
                if(drill == 0){
                    if(prevH > map[ny][nx] - k){
                        visited[ny][nx] = true;
                        curD = Math.max(curD, dfs(nx, ny, 1, d + 1, prevH - 1));
                        visited[ny][nx] = false;
                    }
                }
            }
        }

        return curD;
    }
}