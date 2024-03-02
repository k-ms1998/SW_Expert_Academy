package D4;

import java.io.*;
import java.util.*;
/**
 * 1. 입력에 따라 트리 생성
 * 	1-1. 루트 노드는 항상 1로 시작
 * 2. 왼쪽 노드 -> 현재 노드 -> 오른쪽 노드 순선로 확인
 * 	2-1. 이때, 항상 숫자 -> 연산자(+, -, *, /) -> 숫자 순으로 되어야 적절한 식이다
 * 	2-2. 적절한 식이면 => 1 반환, 적절하지 않은 식이면 0 반환
 * 	2-3. 리프 노드인데 숫자가 아니면 적절한 식을 만들 수 없음 -> 0 반환
 */
public class Prob1233 {

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static StringBuilder sb = new StringBuilder();

    static int size;
    static Node[] nodes;

    public static void main(String[] args) throws IOException{
        for(int testCase = 1; testCase <= 10; testCase++) {
            size = Integer.parseInt(br.readLine());
            nodes = new Node[size + 1];

            for(int idx = 0; idx < size; idx++) {
                st = new StringTokenizer(br.readLine().trim());
                int nodeNum = Integer.parseInt(st.nextToken());
                char nodeData = st.nextToken().charAt(0);
                int leftChildIdx = 0;
                int rightChildIdx = 0;

                if(st.hasMoreTokens()) { // StringTokenizer에 토근이 남아 있을때 -> 리프 노드는 자식 노드가 없기 때문에 추가로 체크해줘야함
                    leftChildIdx = Integer.parseInt(st.nextToken());
                }
                if(st.hasMoreTokens()) { // StringTokenizer에 토근이 남아 있을때 -> 리프 노드는 자식 노드가 없기 때문에 추가로 체크해줘야함
                    rightChildIdx = Integer.parseInt(st.nextToken());
                }

                nodes[nodeNum] = new Node(nodeData, leftChildIdx, rightChildIdx);
            }

            sb.append(String.format("#%d %d\n", testCase, checkIfValid(1))); // 루트 노드에서 시작해서 그래프 순회
        }

        System.out.println(sb);
    }

    public static class Node{
        char data;
        int leftChildIdx;
        int rightChildIdx;

        public Node(char data, int leftChildIdx, int rightChildIdx) {
            this.data = data;
            this.leftChildIdx = leftChildIdx;
            this.rightChildIdx = rightChildIdx;
        }

        @Override
        public String toString() {
            return "Node [data=" + data + ", leftChildIdx=" + leftChildIdx + ", rightChildIdx=" + rightChildIdx + "]";
        }
    }

    public static int checkIfValid(int nodeIdx) {
        Node node = nodes[nodeIdx];
        if(node == null) {
            return 1;
        }

        int leftChildIdx = node.leftChildIdx;
        int rightChildIdx = node.rightChildIdx;
        if(leftChildIdx == 0 && rightChildIdx == 0) { // 리프 노드일때
            if(node.data == '+' || node.data == '-'
                    || node.data == '/' || node.data == '*') { // 리프 노드는 무조건 숫자여야함 -> 숫자가 아닐 경우 적절한 식을 만들지 못함 -> 0 반환
                return 0;
            }

            return node.data - '0';
        }

        int leftData = checkIfValid(leftChildIdx);
        char curData = node.data;
        int rightData = checkIfValid(rightChildIdx);

        if(leftData == 0 || rightData == 0
                || (node.data >= '0' && node.data <= '9')) { // leftData 또는 rightData가 0 반환 -> 적잘한 식을 만들지 못함, 현재 데이터가 연산자가 아니다 -> 적잘한 식을 만들지 못함 => 0 반환
            return 0;
        }

        return 1; // 정상적인 식 => 1 반환
    }
}