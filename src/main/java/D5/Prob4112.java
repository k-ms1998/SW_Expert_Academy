package D5;
import java.util.*;
import java.io.*;
/**
 * 1
 * 2 3
 * 4 5 6
 * 7 8 9 10
 */
public class Prob4112
{
    static int[][] tree =  new int[1000][1000];
    static Node[] nodes = new Node[10001];
    static final int INF = 100_000_000;

    static int[] dx = {-1, 0, -1, 1, 0, 1};
    static int[] dy = {-1, -1, 0, 0, 1, 1};

    static StringBuilder ans = new StringBuilder();

    public static void main(String args[]) throws Exception
    {
        buildTree();

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int T = Integer.parseInt(br.readLine());
        for(int test_case = 1; test_case <= T; test_case++)
        {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());

            int[][] dist = new int[1000][1000];
            for(int y = 0; y < 1000; y++){
                Arrays.fill(dist[y], INF);
            }

            Node start = nodes[a];
            Node end = nodes[b];

            int minX = Math.min(start.x, end.x);
            int maxX = Math.max(start.x, end.x);
            int minY = Math.min(start.y, end.y);
            int maxY = Math.max(start.y, end.y);

            int answerD = INF;
            Deque<Node> queue = new ArrayDeque<>();
            queue.offer(start);
            while(!queue.isEmpty()){
                Node node = queue.poll();
                int x = node.x;
                int y = node.y;
                int d = node.d;

                if(tree[y][x] == b){
                    answerD = Math.min(answerD, d);
                    continue;
                }

                for(int i = 0; i < 6; i++){
                    int nx = x + dx[i];
                    int ny = y + dy[i];

                    if(nx < minX || ny < minY || nx > maxX  || ny > maxY){
                        continue;
                    }
                    if(tree[ny][nx] == 0){
                        continue;
                    }

                    if(dist[ny][nx] > d + 1){
                        dist[ny][nx] = d + 1;
                        queue.offer(new Node(nx, ny, d + 1));
                    }
                }
            }

            ans.append(String.format("#%d %d\n", test_case, answerD));
        }

        System.out.println(ans);
    }

    public static void buildTree(){
        int num = 1;
        for(int y = 0; y < 1000; y++){
            for(int x = 0; x <= y; x++){
                tree[y][x] = num;
                nodes[num] = new Node(x, y, 0);
                num++;
                if(num > 10000){
                    return;
                }
            }
        }
    }

    public static class Node{
        int x;
        int y;
        int d;

        public Node(int x, int y, int d){
            this.x = x;
            this.y = y;
            this.d = d;
        }
    }
}