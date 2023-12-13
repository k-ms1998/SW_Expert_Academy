package D5;

import java.io.*;
import java.util.*;

public class Prob1248 {

    static StringBuilder ans = new StringBuilder();

    public static void main(String args[]) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;



        int T = Integer.parseInt(br.readLine());
        for(int t  = 1; t <= T; t++){
            st = new StringTokenizer(br.readLine());
            int v = Integer.parseInt(st.nextToken());
            int e = Integer.parseInt(st.nextToken());
            int s = Integer.parseInt(st.nextToken());
            int d = Integer.parseInt(st.nextToken());

            List<Integer>[] children = new List[v + 1];
            int[] parent = new int[v + 1];
            for(int i = 0; i < v + 1; i++){
                children[i] = new ArrayList<>();
                parent[i] = i;
            }

            st = new StringTokenizer(br.readLine());
            for(int i = 0; i < e; i++){
                int a = Integer.parseInt(st.nextToken());
                int b = Integer.parseInt(st.nextToken()); // a->b

                children[a].add(b);
                parent[b] = a;
            }

            int sH = findH(1, 1, s, children);
            int dH = findH(1, 1, d, children);

            while(sH != dH){
                // System.out.println("sH=" + sH + ", dH=" + dH + ", s=" + s + ", d=" + d);
                if(sH < dH){
                    dH--;
                    d = parent[d];
                    continue;
                }else{
                    sH--;
                    s = parent[s];
                }
            }

            while(s != d){
                s = parent[s];
                d = parent[d];
            }

            int common = s;
            int subTreeSize = findSubTreeSize(common, children) + 1;

            ans.append(String.format("#%d %d %d\n", t, common, subTreeSize));
        }

        System.out.println(ans);
    }

    public static int findH(int depth, int node, int target, List<Integer>[] children){
        if(node == target){
            return depth;
        }

        for(int next : children[node]){
            int res = findH(depth + 1, next, target, children);
            if(res != 0){
                return res;
            }
        }

        return 0;
    }

    public static int findSubTreeSize(int node, List<Integer>[] children){
        int tmp = children[node].size();
        for(int next : children[node]){
            tmp += findSubTreeSize(next, children);
        }

        return tmp;
    }

}