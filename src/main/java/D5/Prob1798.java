package D5;
import java.io.*;
import java.util.*;

public class Prob1798 {

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static StringBuilder sb = new StringBuilder();

    static int totalLocations, totalDays;
    static int[][] dist;
    static int[] type;
    static int[] time;
    static int[] value;

    static int airport;
    static List<Integer> hotels;

    static int answerValue;
    static String answerRoute;

    static final int maxTime = 60 * 9;

    public static void main(String args[]) throws IOException{
        int T = Integer.parseInt(br.readLine());
        for(int testCase = 1; testCase <= T; testCase++){
            st = new StringTokenizer(br.readLine());
            totalLocations = Integer.parseInt(st.nextToken());
            totalDays = Integer.parseInt(st.nextToken());

            dist = new int[totalLocations][totalLocations];
            type = new int[totalLocations];
            time = new int[totalLocations];
            value = new int[totalLocations];

            airport = 0;
            hotels = new ArrayList<>();
            answerValue = 0;
            answerRoute = "";

            for(int locationA = 0; locationA < totalLocations - 1; locationA++){
                st = new StringTokenizer(br.readLine());
                for(int locationB = locationA + 1; locationB < totalLocations; locationB++){
                    int curDist = Integer.parseInt(st.nextToken());
                    dist[locationA][locationB] = curDist;
                    dist[locationB][locationA] = curDist;
                }
            }

            for(int idx = 0; idx < totalLocations; idx++){
                st = new StringTokenizer(br.readLine());
                String typeStr = st.nextToken();

                if(typeStr.equals("A")){
                    type[idx] = 1;
                    airport = idx;
                }
                if(typeStr.equals("P")){
                    int curTime = Integer.parseInt(st.nextToken());
                    int curValue = Integer.parseInt(st.nextToken());
                    type[idx] = 2;
                    time[idx] = curTime;
                    value[idx] = curValue;

                }
                if(typeStr.equals("H")){
                    type[idx] = 3;
                    hotels.add(idx);
                }
            }

            dfs(airport, 0, 0, 0, 0, new ArrayList<>());

            sb.append(String.format("#%d %d %s\n", testCase, answerValue, answerRoute));
        }


        System.out.println(sb);
    }

    public static void dfs(int node, int v, int t, int day, int visited, List<Integer> route){
        if(day >= totalDays){
            if(v > answerValue){
                answerValue = v;
                String curRoute = "";
                for(int r : route){
                    curRoute += (r + 1) + " ";
                }

                answerRoute = curRoute;
            }
            return;
        }

        for(int next = 0; next < totalLocations; next++){
            if(next == node){
                continue;
            }
            int nextType = type[next];
            int nextDist = t + dist[node][next] + time[next];
            if(nextDist > maxTime){
                continue;
            }
            int nextBit = (1 << next);
            if((visited & nextBit) == nextBit){
                continue;
            }

            if(nextType == 1){
                if(day == totalDays - 1){
                    route.add(next);
                    dfs(next, v, t, day + 1, visited, route);
                    route.remove(route.size() - 1);
                }
            }
            if(nextType == 2){
                route.add(next);
                dfs(next, v + value[next], nextDist, day, visited | nextBit, route);
                route.remove(route.size() - 1);
            }
            if(nextType == 3){
                if(day < totalDays - 1) {
                    route.add(next);
                    dfs(next, v, 0, day + 1, visited, route);
                    route.remove(route.size() - 1);
                }
            }
        }
    }
}