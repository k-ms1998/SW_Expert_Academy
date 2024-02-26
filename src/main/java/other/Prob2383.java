package other;
import java.io.*;
import java.util.*;

/**
 * 1. 방의 한 변의 길이 N은 4이상 10이하의 정수
 * 2. 각 사람이 계단 입구까지 이동하는 시간 = |사람의 열 - 계단의 열| + |사람의 행 - 계단의 행| 분
 * 3. 각 계단을 내려가는데 걸리는 시간 = k분
 * 4. 각 계단은 최대 3명씩 사용할 수 있다
 * 	4-1. 만약 이미 3명이 사용중이면 한 명이 계단을 다 내려갈때까지 기다려야 한다
 * ---
 * 1. 모든 경우의 수를 확인한다
 * 	1-1. 모든 사람이 1번 계단을 사용 ~ 모든 사람이 2번 계단을 사용
 * 	1-2. 비트마스킹으로 통해 조합을 구한다
 * 2. 각 조합마다 모든 사람이 계단을 내려다는데 걸리는 시간을 계산한다
 * 	2-1. 모든 사람마다 목표 계단까지의 이동 시간을 구하고, 이동 시간이 작은 순으로 정렬
 * 	2-2. 순서대로 계단에 간다
 * 		2-2-1. 계단에 3명 미만의 사람이 있다 -> 계단에 사람 추가
 * 		2-2-2. 계단에 3명 이상의 사람이 있다
 * 			2-2-2-1. 가장 앞에 있는 사람은 뺀다
 * 			2-2-2-2. 해당 사람이 계단에 도달하고 내려가는데 소요된 총 시간 계산 -> 계단까지의 거리 + 계단의 길이
 * 			2-2-2-3. 새로 추가할 사람이 계단에 도달하는데 걸리는 시간과 2-2-2-2의 시간을 비교해서 더 오래 걸린 시간을 계단에 넣는다
 *	2-3. 마지막에 계단에 가장 마지막으로 들오온 사람을 파악하고, 해당 사람이 계단에 들어온 시간 + 계단의 길이 + 1 값이 해당 계단에 조합에 있는 사람들이 모두 사용하는데 걸리 시간
 * 3. 2을 통해서 구한 시간 중 가장 작은 값이 정답
 */
public class Prob2383 {

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static StringBuilder sb = new StringBuilder();

    static int size;
    static int[][] grid;
    static List<Point> people = new ArrayList<>();
    static int totalPeople;
    static int targetCol1, targetRow1, targetCol2, targetRow2;

    public static void main(String[] args) throws IOException{
        int T = Integer.parseInt(br.readLine());
        for(int testCase = 1; testCase <= T; testCase++) {
            size = Integer.parseInt(br.readLine());
            grid = new int[size][size];
            people = new ArrayList<>();
            targetCol1 = -1;
            targetRow1 = -1;
            targetCol2 = -1;
            targetRow2 = -1;

            for(int row = 0; row < size; row++) {
                st = new StringTokenizer(br.readLine());
                for(int col = 0; col < size; col++) {
                    grid[row][col] = Integer.parseInt(st.nextToken());
                    if(grid[row][col] == 1) {
                        people.add(new Point(col, row));
                    }
                    if(grid[row][col] > 1) {
                        if(targetCol1 == -1 && targetRow1 == -1) {
                            targetCol1 = col;
                            targetRow1 = row;
                        }else {
                            targetCol2 = col;
                            targetRow2 = row;
                        }
                    }
                }
            }

            int answer = Integer.MAX_VALUE;
            totalPeople = people.size();
            int allIncludedBit = (1 << totalPeople) - 1;
            for(int bit = 0; bit <= allIncludedBit; bit++) { // 1번 계단을 사용할 사람들
                int reverseBit = allIncludedBit ^ bit; // 2번 계단을 사용할 사람들

                // 1번 계단을 조합에 있는 사람들이 모두 사용하는데 걸린 시간
                int result1 = findTotalTime(bit, targetCol1, targetRow1, grid[targetRow1][targetCol1]);

                // 2번 계단을 조합에 있는 사람들이 모두 사용하는데 걸린 시간
                int result2 = findTotalTime(reverseBit, targetCol2, targetRow2, grid[targetRow2][targetCol2]);

                // 1번 계단 사용과 2번 계단 사용은 병렬적으로 일어남 -> 각 계단을 사용하는데 걸린 시간 중 큰 값을 확인ㄴ
                answer = Math.min(answer, Math.max(result1, result2));
            }

            sb.append(String.format("#%d %d\n", testCase, answer));
        }

        System.out.println(sb);
    }

    public static int findTotalTime(int bit, int targetCol, int targetRow, int stairLength) {
        PriorityQueue<Integer> times = new PriorityQueue<>();

        // 조합에 포함되어 있는 사람들이 목표 계단까지의 이동 시간을 구해서 min heap에 저장
        for(int idx = 0; idx < totalPeople; idx++) {
            if((bit & (1 << idx)) == (1 << idx)) {
                Point p = people.get(idx);
                int toStair = Math.abs(p.col - targetCol) + Math.abs(p.row - targetRow);
                times.offer(toStair);
            }
        }

        // min heap에 저장된 값들을 하나씩 반환받아서 계단에 넣는다
        Deque<Integer> inStairs = new ArrayDeque<>();
        while(!times.isEmpty()) {
            int next = times.poll();

            if(inStairs.size() < 3) { // 계단에 아직 3명 미만이 있을때
                inStairs.offer(next);
            }else { // 계단에 3명 이상이 있을때
                int first = inStairs.poll(); // 가장 앞에 있는 사람
                int firstEnd = first + stairLength; // 가장 앞에 있는 사람이 계단을 내려갔을때의 시간
                inStairs.offer(Math.max(firstEnd, next)); // 위에서 구한 시간과 다음 사람이 계단에 도달하는 시간 중 더 큰 값을 넣는다
                // 현재 사람이 계단에 도달한 이후에 가장 앞에 있는 사람이 계단에 아직 있는 경우에는 기다려야 하기 때문에
            }
        }

        return inStairs.isEmpty() ? 0 : inStairs.pollLast() + stairLength + 1;
    }

    public static class Point{
        int col;
        int row;

        public Point(int col, int row) {
            this.col = col;
            this.row = row;
        }
    }

}
