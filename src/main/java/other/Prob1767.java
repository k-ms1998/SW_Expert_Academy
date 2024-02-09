package other;

import java.io.*;
import java.util.*;

/**
 * 1. NxN의 격자가 주어짐 (1 <= N <= 12)
 *  1-1. 칸의 가장 자리에는 전원이 흐르고 있다(범위 밖)
 *      1-1-2. 가장자리에 위치한 core는 이미 전원이 연결된 것으로 간주
 *  1-2. 격자에 core가 배치되어 있을때, 최대한 많은 core를 전원에 연결할때 연결된 core의 수를 구하기
 *      1-2-1. core의 개수는 1이상 12이하
 * 2. 각 칸에는 한개의 core 또는 전선이 주어짐
 * 3. 전선은 직선으로만 설치가 가능하며, 교차할 수 없다
 * 4. 전원을 연결할 코어들의 조합을 구한다
 *  4-1. 만약 코어가 12개가 주어지고, 12개를 모두 연결 시도
 *      4-1-1. 각 방향마다 4개의 방향으로 전선을 놓을 수 있다 -> 4^12 = 약 17_000_000
 *      4-1-2. 각 방향으로 전선 놓기-> size만큼 확인 -> 12 * 17_000_000 = 204_000_000
 */
public class Prob1767 {

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static StringBuilder sb = new StringBuilder();

    static int size;
    static int[][] grid;
    static List<Point> cores = new ArrayList<>();

    static int[] coresDir;
    static int[][] coresWiresLength;

    static int[] selected;
    static boolean[][] wiresGrid;

    static final int[] dCol = {1, 0, -1, 0};
    static final int[] dRow = {0, 1, 0, -1};
    static final int INF = 100_000_000;

    public static void main(String args[]) throws IOException{
        int T = Integer.parseInt(br.readLine());
        for(int testCase  = 1; testCase <= T; testCase++){
            size = Integer.parseInt(br.readLine());
            grid = new int[size][size];
            cores = new ArrayList<>();
            wiresGrid = new boolean[size][size];

            for(int row = 0; row < size; row++){
                st = new StringTokenizer(br.readLine());
                for(int col = 0; col < size; col++){
                    grid[row][col] = Integer.parseInt(st.nextToken());
                    if(grid[row][col] == 1){
                        if(!isOuter(col, row)){
                            cores.add(new Point(col, row));
                        }
                    }
                }
            }

            int totalCores = cores.size();
            coresDir = new int[totalCores];
            coresWiresLength = new int[totalCores][4];
            for(int idx = 0; idx < totalCores; idx++){
                Point p = cores.get(idx);
                int col = p.col;
                int row = p.row;
                
                int canPlace = 0;
                for(int dir = 0; dir < 4; dir++){
                    int nCol = col;
                    int nRow = row;
                    while(true) {
                        nCol += dCol[dir];
                        nRow += dRow[dir];

                        if (isOutOfBounds(nCol, nRow)) { // 방해 없이 가장자리까지 도착 -> 전선을 놓을 수 있음
                            canPlace |= (1 << dir);
                            break;
                        }
                        if (grid[nRow][nCol] == 1) { // 현재 방향에 다른 코어가 있다 -> 해당 방향으로 전선을 놓지 못함
                            break;
                        }
                    }
                }

                coresDir[idx] = canPlace;
                coresWiresLength[idx][0] = size - col - 1;
                coresWiresLength[idx][1] = size - row - 1;
                coresWiresLength[idx][2] = col;
                coresWiresLength[idx][3] = row;
//                System.out.printf("%d:%d(%d)-> %d, %d, %d, %d\n", col, row, canPlace,
//                        coresWiresLength[idx][0], coresWiresLength[idx][1], coresWiresLength[idx][2], coresWiresLength[idx][3]);
            }

            int answer = 0;
            for(int count = totalCores; count >= 1; count--){
                selected = new int[count];
                int result = findCombination(0, 0, count);
//                System.out.printf("count=%d, result=%d\n", count, result);
                if(result < INF){
                    answer = result;
                    break;
                }
            }

            sb.append(String.format("#%d %d\n", testCase, answer));
        }

        System.out.println(sb);
    }

    public static int findCombination(int depth, int coreIdx, int target){
        if(depth == target){
            wiresGrid = new boolean[size][size];
            return placeWires(0, target, 0);
        }
        if(coreIdx == cores.size()){
            return INF;
        }

        int result = INF;
        selected[depth] = 0;
        result = Math.min(result, findCombination(depth, coreIdx + 1, target)); // 현재 코어 선택 X

        selected[depth] = coreIdx;
        result = Math.min(result, findCombination(depth + 1, coreIdx + 1, target)); // 현재 코어 선택

        return result;
    }

    public static int placeWires(int depth, int target, int totalWires){
        if(depth == target){
            return totalWires;
        }

        int sIdx = selected[depth];
        Point p = cores.get(sIdx);

        int result = INF;
        int col = p.col;
        int row = p.row;
        for(int dir = 0; dir < 4; dir++){
            int checkBit = (1 << dir);
            if((coresDir[sIdx] & checkBit) == checkBit){
                if(canPlace(col, row, dir)){
                    updateWiresGrid(col, row, dir, true);
                    result = Math.min(result, placeWires(depth + 1, target, totalWires + coresWiresLength[sIdx][dir]));
                    updateWiresGrid(col, row, dir, false);
                }
            }
        }

        return result;
    }

    public static boolean canPlace(int col, int row, int dir){
        if(dir == 0){ // 오른쪽
            for(int nCol = col; nCol < size; nCol++){
                if(wiresGrid[row][nCol]){
                    return false;
                }
            }
        }else if(dir == 1){ // 아래쪽
            for(int nRow = row; nRow < size; nRow++){
                if(wiresGrid[nRow][col]){
                    return false;
                }
            }
        }else if(dir == 2){ // 왼쪽
            for(int nCol = col; nCol >= 0; nCol--){
                if(wiresGrid[row][nCol]){
                    return false;
                }
            }
        }else{ // 위쪽
            for(int nRow = row; nRow >= 0; nRow--){
                if(wiresGrid[nRow][col]){
                    return false;
                }
            }
        }

        return true;
    }

    public static void updateWiresGrid(int col, int row, int dir, boolean target){
        wiresGrid[row][col] = target;
        int nCol = col;
        int nRow = row;
        while(true){
            nCol += dCol[dir];
            nRow += dRow[dir];

            if(isOutOfBounds(nCol, nRow)){
                return;
            }

            wiresGrid[nRow][nCol] = target;
        }
    }

    public static boolean isOuter(int col, int row){
        return col == 0 || row == 0 || col == size - 1 || row == size - 1;
    }

    public static boolean isOutOfBounds(int col, int row){
        return col < 0 || row < 0 || col >= size || row >= size;
    }

    public static class Point{
        int col;
        int row;

        public Point(int col, int row){
            this.col = col;
            this.row = row;
        }
    }
}