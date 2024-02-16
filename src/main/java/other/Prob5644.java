package other;

import java.io.*;
import java.util.*;

/**
 * 1. 최적의 충전 알고리즘 개발하기
 * 2. 매초마다 특정 BC안에 들어오면 BC랑 접속 가능
 * 	2-1. 만약 하나의 BC에 두 명의 사용자가 접속한 경우, 사용자 끼리 균등하게 배분
 * 3. 모든 BC에 대해서 자신의 범위를 배열에 저장(grid)
 * 	3-1. 비트로 저장 -> grid[0][0] = 0101 -> (0,0)에는 0번째 BC와 2번째 BC가 닿을 수 있는 거리
 * 4. 사용자의 이동 궤적이 주어졌을때, 모든 사용자가 충전한 양의 합의 최댓값을 구하기
 * 	4-1.매초마다 사용자들의 위치 확인
 * 	4-2. 해당 위치가 BC 범위 내에 있는지 확인
 * 	4-3. 만약 둘 다 BC 범위 내에 있으면:
 * 		4-3-1. 둘이 공통으로 갖는 BC 찾기 (maxCommon)
 * 		4-3-2. 4-3-1에서 찾은 BC를 제외하고 본인들이 가질 수 있는 충전양이 가장 큰 BC들을 찾기 (maxA, maxB)
 * 		4-3-3. 충전 가능한 최대양 = max(4-3-1에서 찾은 BC 나누기, A랑 B 둘다 4-3-2에서 각각 찾은 가장 큰 BC 사용하기, A가 4-3-1을 갖고 B는 4-3-2에서 찾은 가장 큰 BC, B가 4-3-1을 갖고 A는 4-3-2에서 찾은 가장 큰 BC)
 * 	4-4. 만약 둘 중 하나만 BC 범위 내에 있으면:
 * 		4-4-1. 본인이 범위 내에 있는 BC 중 가장 큰 충전량 더하기
 */
public class Prob5644 {

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static StringBuilder sb = new StringBuilder();

    // 제자리, 상, 우, 하, 좌
    static final int[] dCol = {0, 0, 1, 0, -1};
    static final int[] dRow = {0, -1, 0, 1, 0};

    static int totalTime, bcCount;
    static int[] commandA;
    static int[] commandB;
    static int[][] grid;

    static BC[] bcs;

    public static void main(String[] args) throws IOException{
        int T = Integer.parseInt(br.readLine());
        for(int testCase = 1; testCase <= T; testCase++) {
            st = new StringTokenizer(br.readLine());
            totalTime = Integer.parseInt(st.nextToken());
            bcCount = Integer.parseInt(st.nextToken());
            grid = new int[11][11];
            bcs = new BC[bcCount];

            commandA = new int[totalTime + 1];
            st = new StringTokenizer(br.readLine());
            for(int time = 0; time < totalTime; time++) {
                commandA[time] = Integer.parseInt(st.nextToken());
            }

            commandB = new int[totalTime + 1];
            st = new StringTokenizer(br.readLine());
            for(int time = 0; time < totalTime; time++) {
                commandB[time] = Integer.parseInt(st.nextToken());
            }

            // 각 BC에 대해서 범위 내에 있는 좌표들을 표시하기
            for(int idx = 0; idx < bcCount; idx++) {
                st = new StringTokenizer(br.readLine());
                int col = Integer.parseInt(st.nextToken());
                int row = Integer.parseInt(st.nextToken());
                int range = Integer.parseInt(st.nextToken());
                int charge = Integer.parseInt(st.nextToken());

                bcs[idx] = new BC(idx, col, row, range, charge);

                for(int delta = 0; delta <= range; delta++) {
                    if(row + delta >= 11) {
                        break;
                    }
                    int startCol = Math.max(1, col - range + delta);
                    int endCol = Math.min(10, col + range - delta);
                    if(startCol > endCol) {
                        continue;
                    }
                    for(int tCol = startCol; tCol <= endCol; tCol++) {
                        grid[row + delta][tCol] |= (1 << idx);
                    }
                }
                for(int delta = 0; delta <= range; delta++) {
                    if(row - delta < 0) {
                        break;
                    }
                    int startCol = Math.max(1, col - range + delta);
                    int endCol = Math.min(10, col + range - delta);
                    if(startCol > endCol) {
                        continue;
                    }
                    for(int tCol = startCol; tCol <= endCol; tCol++) {
                        grid[row - delta][tCol] |= (1 << idx);
                    }
                }
            }


            int answer = 0;
            int colA = 1;
            int rowA = 1;
            int colB = 10;
            int rowB = 10;
            for(int time = 0; time <= totalTime; time++) {
                // 둘 다 범위 BC 범위 내에 있을때
                if(grid[rowA][colA] != 0 && grid[rowB][colB] != 0) {
                    int both = grid[rowA][colA] & grid[rowB][colB]; // 둘이 공통으로 가질 수 있는 BC들

                    int maxIdx = 0;
                    int maxCharge = 0;
                    if(both != 0) { // 둘이 공통으로 가질 수 있는 BC가 있을때 -> 그 중에서 가장 큰 BC 찾기
                        for(int idx = 0; idx < bcCount; idx++){
                            int targetBit = (1 << idx);
                            if((both & targetBit) == targetBit) {
                                BC curBc = bcs[idx];
                                if(curBc.charge > maxCharge) {
                                    maxCharge = curBc.charge;
                                    maxIdx = (1 << idx);
                                }
                            }
                        }
                    }

                    // 둘이 공통으로 가질 수 있는 가장 큰 BC를 제외한 A가 가질 수 있는 가장 큰 BC
                    int maxChargeA = 0;
                    int xorA = grid[rowA][colA] ^ maxIdx;
                    for(int idx = 0; idx < bcCount; idx++){
                        int targetBit = (1 << idx);
                        if((xorA & targetBit) == targetBit) {
                            BC curBc = bcs[idx];
                            if(curBc.charge > maxChargeA) {
                                maxChargeA = curBc.charge;
                            }
                        }
                    }

                    // 둘이 공통으로 가질 수 있는 가장 큰 BC를 제외한 B가 가질 수 있는 가장 큰 BC
                    int maxChargeB = 0;
                    int xorB = grid[rowB][colB] ^ maxIdx;
                    for(int idx = 0; idx < bcCount; idx++){
                        int targetBit = (1 << idx);
                        if((xorB & targetBit) == targetBit) {
                            BC curBc = bcs[idx];
                            if(curBc.charge > maxChargeB) {
                                maxChargeB = curBc.charge;
                            }
                        }
                    }

                    int curCharge = Math.max(maxCharge,
                            Math.max(maxChargeA + maxChargeB,
                                    Math.max(maxChargeA + maxCharge, maxCharge + maxChargeB)));
                    answer += curCharge;

                }else if(grid[rowA][colA] != 0){ // A만 범위 내에 있을때
                    int maxCharge = 0;
                    for(int idx = 0; idx < bcCount; idx++){
                        int targetBit = (1 << idx);
                        if((grid[rowA][colA] & targetBit) == targetBit) {
                            maxCharge =  Math.max(maxCharge, bcs[idx].charge);
                        }
                    }
                    answer += maxCharge;
                }else if(grid[rowB][colB] != 0){ // B만 범위 내에 있을때
                    int maxCharge = 0;
                    for(int idx = 0; idx < bcCount; idx++){
                        int targetBit = (1 << idx);
                        if((grid[rowB][colB] & targetBit) == targetBit) {
                            maxCharge =  Math.max(maxCharge, bcs[idx].charge);
                        }
                    }
                    answer += maxCharge;
                }

                colA += dCol[commandA[time]];
                rowA += dRow[commandA[time]];

                colB += dCol[commandB[time]];
                rowB += dRow[commandB[time]];
            }

            sb.append(String.format("#%d %d\n", testCase, answer));
        }

        System.out.println(sb);
    }

    public static class BC{
        int idx;
        int col;
        int row;
        int range;
        int charge;

        public BC(int idx, int col, int row, int range, int charge) {
            this.idx = idx;
            this.col = col;
            this.row = row;
            this.range = range;
            this.charge = charge;
        }
    }
}
