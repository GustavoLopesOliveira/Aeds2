import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Ex09 {

    public static boolean eAnagrama(String str1, String str2) {
        if (str1.length() != str2.length()) {
            return false;
        }

        Map<Character, Integer> map = new HashMap<>();

        for (char c : str1.toCharArray()) {
            map.put(c, map.getOrDefault(c, 0) + 1);
        }

        for (char c : str2.toCharArray()) {
            map.put(c, map.getOrDefault(c, 0) - 1);
        }

        for (int count : map.values()) {
            if (count != 0) {
                return false;
            }
        }

        return true;
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
	String text = input.nextLine();

        while (!text.equals("FIM")) {
            int j = text.indexOf('-');

            if (j == -1 || j == 0 || j == text.length() - 1) {
                System.out.println("NÃO"); // Caso o "-" esteja no início ou no final, não é válido
            } else {
                String str1 = text.substring(0, j);
                String str2 = text.substring(j + 1);

                boolean is = eAnagrama(str1, str2);
                System.out.println(!is ? "SIM" : "NÃO");
            }

            text = input.nextLine();
        }

	input.close();
    }
}

