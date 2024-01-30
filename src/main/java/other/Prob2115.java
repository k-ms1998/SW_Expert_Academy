package other;

import java.io.*;
import java.util.*;

/**
 * 1. 두 명의 일꾼을 통해 서로 안겹치게 벌통의 꿀을 채취
 * 2. 각 일꾼이 채취할 수 있는 벌통의 수 = M, 하나의 꿀통에서는 모든 꿀을 채취 해야하고 최대 C만큼의 꿀만 채취 가능
 * 3. 각 일꾼들이 채취할 수 있는 연속되는 M개의 벌통의 모든 조합을 찾는다 -> 완전탐색(조합)
 * 4. 각 연속되는 M개의 벌통에서 얻을 수 있는 최대의 꿀의 양을 찾는다 -> 완전탐색(조합)
 * 5. 4에서 얻은 값중 최대 값이 최종 정답
 */
public class Prob2115 {

    static int n, m, c;
    static int[][] hive;
    static int[][] maxHive;

    public static void main(String args[]) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();

        int T = Integer.parseInt(br.readLine().trim());
        for(int testCase = 1; testCase <= T; testCase++){
            st = new StringTokenizer(br.readLine());
            n = Integer.parseInt(st.nextToken());
            m = Integer.parseInt(st.nextToken());
            c = Integer.parseInt(st.nextToken());

            hive = new int[n][n];
            maxHive = new int[n][n];
            for(int row = 0; row < n; row++){
                st = new StringTokenizer(br.readLine());
                for(int col = 0; col < n; col++){
                    int honey = Integer.parseInt(st.nextToken());
                    hive[row][col] = honey;
                }
            }
            for(int row = 0; row < n; row++){
                for(int col = 0; col < n; col++){
                    if(col + m > n){
                        break;
                    }
                    maxHive[row][col] = findMaxHoney(col, col + m, row, 0, 0);
                }
            }


            int maxHoney = 0;
            for(int row = 0; row < n; row++){
                for(int col = 0; col < n; col++){
                    if(col + m > n){
                        break;
                    }
                    int honeyA = maxHive[row][col];

                    for(int rowB = row; rowB < n; rowB++){
                        int colB = 0;
                        if(rowB == row){
                            colB = col + m;
                        }
                        for(; colB < n; colB++){
                            if(colB + m > n){
                                break;
                            }
                            int honeyB = maxHive[rowB][colB];

                            maxHoney = Math.max(maxHoney, honeyA + honeyB);
                        }
                    }
                }
            }

            sb.append(String.format("#%d %d\n", testCase, maxHoney));
        }

        System.out.println(sb);
    }

    public static int findMaxHoney(int sCol, int dCol, int row, int curCollected, int curHoney){
        // System.out.println(sCol + ", " + dCol + ", ");
        if(curCollected > c){
            return 0;
        }

        int result = curHoney;
        for(int col = sCol; col < dCol; col++){
            int honey = hive[row][col];
            result = Math.max(result, findMaxHoney(col + 1, dCol, row, curCollected + honey, curHoney + (honey * honey)));
        }

        return result;
    }
}
