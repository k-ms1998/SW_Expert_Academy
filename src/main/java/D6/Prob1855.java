package D6;

import java.io.*;
import java.util.*;

/**
 * LCA(DP)
 * 
 * 1. 노드의 번호가 작은 순서대로 탐색 -> 1, 2, 3, 4 ...
 * 2. 이때, 현재 노드에서 다음 노드까지 최단 거리를 구해야한다
 *  2-1. 공통 조상으로 찾기
 *  2-2. 두 노의 거리 = 노드A에서 공통 조상까지의 거리 + 노드B에서 공통 조상까지의 거리
 * 3. 모든 거리들의 합이 최종 정답
 */
public class Prob1855 {

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static StringBuilder sb = new StringBuilder();

    static int nodeCount;
    static int maxH;

    static int[][] parents;
    static List<Integer>[] edges;
    static long[] depth;

    static int nodeA, nodeB;

    public static void main(String args[]) throws IOException{
        int T = Integer.parseInt(br.readLine());
        for(int testCase  = 1; testCase <= T; testCase++){
            nodeCount = Integer.parseInt(br.readLine());
            maxH = (int) (Math.log(nodeCount) / Math.log(2)) + 1;

            parents = new int[nodeCount + 1][maxH];
            edges = new List[nodeCount + 1];
            depth = new long[nodeCount + 1];
            for(int node = 0; node <= nodeCount; node++){
                edges[node] = new ArrayList<>();
            }

            // 노드 입력 받기 + 부모 설정 + 간선 추가
            st = new StringTokenizer(br.readLine());
            if(nodeCount == 1){
                sb.append(String.format("#%d %d\n", testCase, 0));
                continue;
            }
            for(int node = 2; node <= nodeCount; node++){
                int parent = Integer.parseInt(st.nextToken());

                parents[node][0] = parent;
                edges[parent].add(node);
            }


            // 각 노드의 높이 구하기
            Deque<Node> queue = new ArrayDeque<>();
            queue.offer(new Node(1, 0));
            depth[0] = -1; // 0번 인덱스는 노드가 없음 -> 높이를 -1로 초기화
            depth[1] = 0;
            while(!queue.isEmpty()){
                Node node = queue.poll();
                int cur = node.cur;
                int h = node.h;

                for(int next : edges[cur]){
                    depth[next] = h + 1;
                    queue.offer(new Node(next, h + 1));
                }
            }

            // parents 초기화 시키기
            for(int h = 1; h < maxH; h++){
                for(int node = 1; node <= nodeCount; node++){
                    parents[node][h] = parents[parents[node][h-1]][h-1];
                }
            }

            // BFS 탐색할 순서 구하기
            Deque<Integer> order = new ArrayDeque<>();
            Deque<Integer> bfsQ = new ArrayDeque<>();
            bfsQ.offer(1);

            while(!bfsQ.isEmpty()){
                int node = bfsQ.poll();
                order.offer(node);

                for(int next : edges[node]){
                    bfsQ.offer(next);
                }
            }
            // System.out.println(order);

            long totalDist = 0;
            while(true){
                nodeA = order.poll();
                if(order.isEmpty()){
                    break;
                }
                nodeB = order.peek();

                int orgNodeA = nodeA;
                int orgNodeB = nodeB;

                // nodeA랑 nodeB의 높이를 맞추기
                setHeight();

                // 두 노드의 공통조상 찾기
                int common = findCommon();
                long commonH = depth[common];
                long curDist = Math.abs(commonH - depth[orgNodeA]) + Math.abs(commonH - depth[orgNodeB]);
                // System.out.printf("%d %d %d\n", orgNodeA, orgNodeB, curDist);
                totalDist += curDist;
            }
            // System.out.println("-----------");

            sb.append(String.format("#%d %d\n", testCase, totalDist));
        }

        System.out.println(sb);
    }

    public static void setHeight(){
        long aH = depth[nodeA];
        long bH = depth[nodeB];

        if(aH > bH){
            for(int h = maxH - 1; h >= 0; h--){
                int parent = parents[nodeA][h];
                long pH = depth[parent];

                if(pH >= bH){
                    nodeA = parent;
                    if(pH == bH){
                        break;
                    }
                }

            }
        }else if(aH < bH){
            for(int h = maxH - 1; h >= 0; h--){
                int parent = parents[nodeB][h];
                long pH = depth[parent];

                if(pH >= aH){
                    nodeB = parent;
                    if(pH == aH){
                        break;
                    }
                }

            }
        }
    }

    public static int findCommon(){
        if(nodeA == nodeB){
            return nodeA;
        }


        for(int h = maxH - 1; h >= 0; h--){
            if(parents[nodeA][h] != parents[nodeB][h]){
                nodeA = parents[nodeA][h];
                nodeB = parents[nodeB][h];
            }
        }

        return parents[nodeA][0];
    }

    public static class Node{
        int cur;
        int h;

        public Node(int cur, int h){
            this.cur = cur;
            this.h = h;
        }
    }
}
/*
1
13 12 8 13
1 2 1 3 2 4 3 5 3 6 4 7 7 12 5 9 5 8 6 11 6 10 11 13
*/