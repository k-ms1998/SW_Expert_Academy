package other;

import java.io.*;
import java.util.*;

/**
 * 1. 모든 집들을 시작 보안 범위의 시작 지점으로 두고 탐색 시작
 * 2. 각 위치마다 k의 값을 1씩 증가 시키면서 손해를 보지 않고 서비스를 제공할 수 있는지 확인
 *
 */
public class Prob2117 {

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static StringBuilder sb = new StringBuilder();

    static int size;
    static int bill;
    static int[][] grid;
    static List<Point> houses = new ArrayList<>();

    public static void main(String[] args) throws IOException{
        int T = Integer.parseInt(br.readLine());

        for(int testCase = 1; testCase <= T; testCase++){
            st = new StringTokenizer(br.readLine());
            size = Integer.parseInt(st.nextToken());
            bill = Integer.parseInt(st.nextToken());

            grid = new int[size][size];
            houses = new ArrayList<>();
            for(int row = 0; row < size; row++){
                st = new StringTokenizer(br.readLine());
                for(int col = 0; col < size; col++){
                    grid[row][col] = Integer.parseInt(st.nextToken());
                    if(grid[row][col] == 1){
                        houses.add(new Point(col, row));
                    }
                }
            }

            int answer = 1;
            for(int row = 0; row < size; row++){
                for(int col = 0; col < size; col++){
                    for(int k = 1; k <= size + 1; k++){
                        int availableHouses = findHouses(col, row, k);
                        answer = Math.max(answer, availableHouses);
                    }
                }
            }

            sb.append(String.format("#%d %d\n", testCase, answer));
        }

        System.out.println(sb);
    }

    public static int findHouses(int col, int row, int k){
        int curCost = k * k + (k - 1) * (k - 1);
        int result = 0;

        for(Point nextHouse : houses){
            int nCol = nextHouse.col;
            int nRow = nextHouse.row;

            if(inRange(col, row, nCol, nRow, k)){
//                System.out.println("nextHouse = " + nextHouse);
                result++;
            }
        }

        int curIncome = result * bill;
//        System.out.println("curCost = " + curCost + ", curIncome=" + curIncome);
        return curCost <= curIncome ? result : -1;
    }

    public static boolean inRange(int col, int row, int nCol, int nRow, int k){
        int diffRow = Math.abs(row - nRow);
        int startCol = Math.max(0, col - (k - 1) + diffRow);
        int endCol = Math.min(size - 1, col + (k - 1) - diffRow);

        return startCol > endCol ? false : (startCol <= nCol && nCol <= endCol);
    }

    public static class Point{
        int col;
        int row;

        public Point(int col, int row){
            this.col = col;
            this.row = row;
        }

        @Override
        public String toString(){
            return String.format("col=%d, row=%d", col, row);
        }
    }
}
