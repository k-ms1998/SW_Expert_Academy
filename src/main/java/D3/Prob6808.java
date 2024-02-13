package D3;
import java.io.*;
import java.util.*;

/**
 * 1. 1~18장의 카드로 게임을 진행 -> 9장씩 나눠 갖고, 9개의 라운드에 걸쳐 게임을 진행
 * 2. 한 라운드에는 한장씩 카드를 낸 다음에 두 사람이 낸 카드에 적힌 수를 비교해서 점수를 계산
 * 	2-1. 이긴 사람은 두 카드에 적힌 수의 합만큼 점수를 얻는다
 * 3. 규영이가 카드 9장이 주어졌을때 규영이가 이기는 경우와 지는 경우를 구하기
 * 	3-1. 규영이가 낼 수 있는 카드의 순서를 구하기
 * 	3-2. 각 순서마다 인영이가 낼 수 있는 모든 카드의 순서랑 비교
 * 	3-3. 비교했을때의 총 점수를 비교해서 승자 결정
 */
public class Prob6808 {

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static StringBuilder sb = new StringBuilder();

    static boolean[] used = new boolean[19];
    static int[] playerA = new int[9];
    static int[] playerB = new int[9];

    static boolean[] selectedB = new boolean[9];
    static int[] selectedNumbersB = new int[9];

    static int winA = 0;
    static int loseA = 0;

    public static void main(String[] args) throws IOException{
        int T = Integer.parseInt(br.readLine());
        for(int testCase = 1; testCase <= T; testCase++) {
            selectedB = new boolean[9];
            selectedNumbersB = new int[9];
            used = new boolean[19];
            winA = 0;
            loseA = 0;

            st = new StringTokenizer(br.readLine());
            for(int idx = 0; idx < 9; idx++) {
                int curNumber = Integer.parseInt(st.nextToken());
                playerA[idx] = curNumber;
                used[curNumber] = true;
            }
            int playerBIdx = 0;
            for(int number = 1; number <= 18; number++) {
                if(!used[number]) {
                    playerB[playerBIdx] = number;
                    playerBIdx++;
                }
            }

            createPermutation(0, selectedNumbersB);

            sb.append(String.format("#%d %d %d\n", testCase, winA, loseA));
        }

        System.out.println(sb);
    }

    public static void createPermutation(int selectedIdx, int[] selectedNumbers) {
        if(selectedIdx == 9) {
            int scoreA = 0;
            int scoreB = 0;
            for(int idx = 0; idx < 9; idx++) {
                if(playerA[idx] > selectedNumbers[idx]) {
                    scoreA += playerA[idx] + selectedNumbers[idx];
                }else {
                    scoreB += playerA[idx] + selectedNumbers[idx];
                }
            }

            if(scoreA > scoreB) {
                winA++;
            }else if(scoreA < scoreB) {
                loseA++;
            }
//			else {
//				winA++;
//				loseA++;
//			}

            return;
        }

        for(int idx = 0; idx < 9; idx++) {
            if(!selectedB[idx]) {
                selectedB[idx] = true;
                selectedNumbers[selectedIdx] = playerB[idx];
                createPermutation(selectedIdx + 1, selectedNumbers);
                selectedNumbers[selectedIdx] = 0;
                selectedB[idx] = false;
            }
        }
    }

}
