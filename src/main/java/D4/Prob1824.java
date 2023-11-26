package D4;

import java.util.*;
import java.io.*;

//1824. 혁진이의 프로그램 검증
public class Prob1824
{
    static boolean[][][][][] visited;
    static int[] dx = {1, 0, -1, 0}; // 오른쪽, 위쪽, 왼쪽, 아래쪽
    static int[] dy = {0, -1, 0, 1};

    public static void main(String args[]) throws Exception
    {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int T;
        T=Integer.parseInt(br.readLine());
        for(int test_case = 1; test_case <= T; test_case++)
        {
            st = new StringTokenizer(br.readLine());
            int n = Integer.parseInt(st.nextToken());
            int m = Integer.parseInt(st.nextToken());

            visited = new boolean[16][n][m][n][m];
            char[][] grid = new char[n][m];
            for(int y = 0; y < n; y++){
                String row = br.readLine();
                for(int x = 0; x < m; x++){
                    grid[y][x] = row.charAt(x);
                }
            }

            String ans = move(n, m, grid) ? "YES" : "NO";

            System.out.println(String.format("#%d %s", test_case, ans));
        }
    }

    public static boolean move(int n, int m, char[][] grid){
        Deque<Info> queue = new ArrayDeque<>();
        queue.offer(new Info(0, 0, 0, 0));
        visited[0][0][0][0][0] = true;
        while(!queue.isEmpty()){
            Info info = queue.poll();
            int x = info.x;
            int y = info.y;
            int value = info.value;
            int d = info.d;

            boolean random = false;
            char c = grid[y][x];
            if(c >= '0' && c <= '9'){
                value = Integer.parseInt(String.valueOf(c));
            }else if(c == '<'){
                d = 2;
            }else if(c == '>'){
                d = 0;
            }else if(c == '^'){
                d = 1;
            }else if(c == 'v'){
                d = 3;
            }else if(c == '_'){
                if(value == 0){
                    d = 0;
                }else{
                    d = 2;
                }
            }else if(c == '|'){
                if(value == 0){
                    d = 3;
                }else{
                    d = 1;
                }
            }else if(c == '?'){
                random = true;
            }else if(c == '@'){
                return true;
            }else if(c == '+'){
                value = (value + 1) % 16;
            }else if(c == '-'){
                value = (value + 15) % 16;
            }

            if(!random){
                int nx = x + dx[d];
                int ny = y + dy[d];
                if(nx < 0){
                    nx = m - 1;
                }
                if(nx >= m){
                    nx = 0;
                }
                if(ny < 0){
                    ny = n - 1;
                }
                if(ny >= n){
                    ny = 0;
                }

                if(!visited[value][ny][nx][y][x]){
                    visited[value][ny][nx][y][x] = true;
                    queue.offer(new Info(nx, ny, value, d));
                }
            }else{
                for(int i = 0; i < 4; i++){
                    int nx = x + dx[i];
                    int ny = y + dy[i];
                    if(nx < 0){
                        nx = m - 1;
                    }
                    if(nx >= m){
                        nx = 0;
                    }
                    if(ny < 0){
                        ny = n - 1;
                    }
                    if(ny >= n){
                        ny = 0;
                    }

                    if(!visited[value][ny][nx][y][x]){
                        visited[value][ny][nx][y][x] = true;
                        queue.offer(new Info(nx, ny, value, i));
                    }
                }
            }
        }

        return false;
    }

    public static class Info{
        int x;
        int y;
        int value;
        int d;

        public Info(int x, int y, int value, int d){
            this.x = x;
            this.y = y;
            this.value = value;
            this.d = d;
        }
    }
}