package D3;

import java.io.*;
import java.util.*;

/**
 * 
 */
public class Prob5607 {

	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer st;
	static StringBuilder sb = new StringBuilder();
	
	static int n, r;
	
	static long[] factorial = new long[1_000_001];
	static final int MOD = 1234567891;
	
	public static void main(String[] args) throws IOException{
		initFactorial();
		int T = Integer.parseInt(br.readLine());
		
		for(int testCase  = 1; testCase <= T; testCase++) {
			st = new StringTokenizer(br.readLine());
			n = Integer.parseInt(st.nextToken());
			r = Integer.parseInt(st.nextToken());
			
			long mul = factorial[n];
			long div = (factorial[n - r] * factorial[r]) % MOD;
			long answer = (mul * pow(div, MOD - 2)) % MOD;
			
			sb.append(String.format("#%d %d\n", testCase, answer));
		}
		
		System.out.println(sb);
	}
	
	public static long pow(long base, long power) {
		if(power == 0) {
			return 1;
		}else if(power == 1) {
			return base;
		}
		
		if(power % 2 == 0) {
			long tmp = pow(base, power / 2);
			return tmp * tmp % MOD;
		}else {
			long tmp = pow(base, power - 1);
			return tmp * base % MOD;
		}
	}
	
	public static void initFactorial() {
		factorial[0] = 1;
		for(int num = 1; num <= 1_000_000; num++) {
			factorial[num] = factorial[num - 1] * num % MOD;
		}
	}
	
}
