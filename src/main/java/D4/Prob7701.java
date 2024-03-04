package D4;

import java.io.*;
import java.util.*;

public class Prob7701 {

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static StringBuilder sb = new StringBuilder();

    static int size;
    static Set<String> used;
    static List<String> names;

    public static void main(String[] args) throws IOException{
        int T = Integer.parseInt(br.readLine());
        for(int testCase = 1; testCase <= T; testCase++) {
            size = Integer.parseInt(br.readLine());
            used = new HashSet<>();
            names = new ArrayList<>();

            for(int idx = 0; idx < size; idx++) {
                String name = br.readLine();
                if(!used.contains(name)) {
                    used.add(name);
                    names.add(name);
                }
            }

            StringBuilder namesSb = new StringBuilder();
            Collections.sort(names, new Comparator<String>() {
                @Override
                public int compare(String s1, String s2) {
                    if(s1.length() == s2.length()) {
                        return s1.compareTo(s2);
                    }

                    return s1.length() - s2.length();
                }
            });
            for(String name : names) {
                namesSb.append(name).append("\n");
            }

            sb.append(String.format("#%d\n%s", testCase, namesSb));
        }

        System.out.println(sb);
    }


}
