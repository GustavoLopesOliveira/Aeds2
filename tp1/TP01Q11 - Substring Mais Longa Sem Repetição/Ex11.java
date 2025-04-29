import java.util.HashMap;
import java.util.Scanner;

public class Ex11 {
    public static int comMaxSubstring(String s) {
        HashMap<Character, Integer> ultimaPosicao = new HashMap<>();
        int maxComprimento = 0;
        int inicio = 0;

        for (int fim = 0; fim < s.length(); fim++) {
            char c = s.charAt(fim);

            // Se o caractere já apareceu movemos o início
            if (ultimaPosicao.containsKey(c) && ultimaPosicao.get(c) >= inicio) {
                inicio = ultimaPosicao.get(c) + 1;
            }

            // Atualiza a posição caractere
            ultimaPosicao.put(c, fim);

            // Atualiza o tam maximo 
            maxComprimento = Math.max(maxComprimento, fim - inicio + 1);
        }

        return maxComprimento;
    }

    public static void main(String[] args) {
	    Scanner input = new Scanner(System.in);
	    String str;
	    str = input.nextLine();
        do{            
	    System.out.println(comMaxSubstring(str));
	    str = input.nextLine();
        }while(!str.equals("FIM"));

	input.close();

    }
}

