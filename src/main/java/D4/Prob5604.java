package D4;

import java.io.*;
import java.util.*;

/**
 *  점화식:
 *  dp[num] = (num을 첫번째 자릿수의 숫자 * dp[9])
 *   + (digit * dp[num을 마지막 자릿수의 숫자 - 1])
 *    + (num을 마지막 자릿수의 숫자 * dp[9]) * findSum(digit - 1, digit / 10)
 *     + dp[num % digit]
 * (digit = 10^(num의 자리수))
 */
public class Prob5604 {

	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer st;
	static StringBuilder sb = new StringBuilder();
	
	static long max = 1_000_000_000_000_000L;
	
	static int[] dp = {0, 1, 3, 6, 10, 15, 21, 28, 36, 45, 46};
	
	public static void main(String[] args) throws IOException{
		int T = Integer.parseInt(br.readLine());
		
		for(int testCase = 1; testCase <= T; testCase++) {
			st = new StringTokenizer(br.readLine());
			long start = Long.parseLong(st.nextToken()) - 1;
			long end = Long.parseLong(st.nextToken());
			
			long digitStart = findDigit(start);
			long digitEnd = findDigit(end);
			
			long sumStart = start < 0 ? 0 : findSum(start, digitStart);
			long sumEnd = end == 0 ? 0 : findSum(end, digitEnd);
		
			sb.append(String.format("#%d %d\n", testCase, (sumEnd - sumStart)));
		}
		
		System.out.println(sb);
	}
	
	public static long findSum(long num, long digit) {
		if(num < 10) {
			return dp[(int)num];
		}
		if(num < 100) {
			int firstDigit = (int)num / 10;
			int rem = (int)num % 10;
			
			return (firstDigit * dp[9])
					+ (10*dp[firstDigit - 1])
					+ ((rem + 1) * firstDigit)
					+ dp[rem];
		}

		long firstDigit = num / digit;
		long rem = num % digit;

		return (firstDigit * (rem + 1))
				+ (digit * dp[(int)firstDigit - 1])
				+ firstDigit* findSum(digit - 1, digit / 10)
				+ findSum(rem, findDigit(rem));
	}
	
	public static long findDigit(long num) {
		int count = 1;
		long mod = 10;
		
		while(num / mod > 0) {
			count++;
			mod *= 10;
		}
		
		return mod / 10;
	}
	
	public static int findPrevNum(int num, int digit) {
		if(num < 10) {
			return 9;
		}
		int tmpDigit = digit;
		int prevNum = ((num / 10) * 10) - 1;
		
		return prevNum;
	}
}
