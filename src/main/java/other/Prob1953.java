package other;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.StringTokenizer;

/**
 * 1. 총 7가지의 터널 존재
 * 2. 시작위치가 주어지고 한시간에 한칸을 움직일 수 있음(row, col)으로 주어짐
 * 3. 이때 l시간 안에 탈주범이 도달할 수 있는 모든 위치의 개수를 구하기
 * 4. N x M (세로 x 가로) 입력 (1 <= N, M, <= 50), (0,0) ~ (N-1, M-1), 1 <= L <= 20
 */
public class Prob1953 {

    static final int[][] dx = {
            {0, 0, 0, 0}, // 빈칸
            {0, 1, 0, -1},
            {0, 0, 0, 0},
            {0, 1, 0, -1},
            {0, 1, 0, 0},
            {0, 1, 0, 0},
            {0, 0, 0, -1},
            {0, 0, 0, -1}
    };
    static final int[][] dy = {
            {0, 0, 0, 0},
            {-1, 0, 1, 0},
            {1, 0, -1, 0},
            {0, 0, 0, 0},
            {-1, 0, 0, 0},
            {0, 0, 1, 0},
            {0, 0, 1, 0},
            {-1, 0, 0, 0}
    };
    static final int INF = 100_000_000;

    static int[][] tunnels;
    static int[][] time;
    static int n, m, r, c, l;


    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();

        int T = Integer.parseInt(br.readLine());
        for(int testCase = 1; testCase <= T; testCase++) {
            st = new StringTokenizer(br.readLine().trim());
            n = Integer.parseInt(st.nextToken());
            m = Integer.parseInt(st.nextToken());
            r = Integer.parseInt(st.nextToken());
            c = Integer.parseInt(st.nextToken());
            l = Integer.parseInt(st.nextToken());

            tunnels = new int[n][m];
            time = new int[n][m];
            for(int row = 0; row < n; row++) {
                st = new StringTokenizer(br.readLine().trim());
                for(int col = 0; col < m; col++) {
                    tunnels[row][col] = Integer.parseInt(st.nextToken());
                    time[row][col] = INF;
                }
            }
            Deque<Point> queue = new ArrayDeque<>();
            queue.offer(new Point(c, r, 1));
            time[r][c] = 1;

            while(!queue.isEmpty()) {
//				System.out.println(queue);
                Point p = queue.poll();
                int x = p.x;
                int y = p.y;
                int t = p.t;


                if(t > l) {
                    continue;
                }

                int tType = tunnels[y][x];
                for(int dir = 0; dir < 4; dir++) {
                    int nx = x + dx[tType][dir];
                    int ny = y + dy[tType][dir];

                    if(nx < 0 || ny < 0 || nx >= m || ny >= n) {
                        continue;
                    }

                    if(time[ny][nx] > t + 1 && tunnels[ny][nx] != 0) {
                        int nextT = tunnels[ny][nx];
                        for(int nextDir = 0; nextDir < 4; nextDir++) {
                            int nnx = nx + dx[nextT][nextDir];
                            int nny = ny + dy[nextT][nextDir];

                            if(nnx < 0 || nny < 0 || nnx >= m || nny >= n) {
                                continue;
                            }

                            if(nnx == x && nny == y) {
                                time[ny][nx] = t + 1;
                                queue.offer(new Point(nx, ny, t + 1));
                                break;
                            }
                        }
                    }
                }
            }

            int answer = 0;
            for(int row = 0; row < n; row++) {
                for(int col = 0; col < m; col++) {
                    if(time[row][col] <= l && tunnels[row][col] != 0) {
                        answer++;
                    }
                }
            }


            sb.append(String.format("#%d %d\n", testCase, answer));
        }

        System.out.println(sb);
    }

    public static class Point{
        int x;
        int y;
        int t;

        public Point(int x, int y, int t) {
            this.x = x;
            this.y = y;
            this.t = t;
        }

        @Override
        public String toString() {
            return "[x=" + x + ", y=" + y + ", t=" + t + "]";
        }
    }

}