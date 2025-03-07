import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Scanner;

public class Ex13 {
    private static final HashMap<Character, Integer> mapa = new HashMap<>();
    static {
        mapa.put('a', 0);
        mapa.put('e', 1);
        mapa.put('i', 2);
        mapa.put('o', 3);
        mapa.put('u', 4);
        
        mapa.put('\u00E1', 5); // á
        mapa.put('\u00E9', 6); // é
        mapa.put('\u00ED', 7); // í
        mapa.put('\u00F3', 8); // ó
        mapa.put('\u00FA', 9); // ú
        
        mapa.put('\u00E0', 10); // à
        mapa.put('\u00E8', 11); // è
        mapa.put('\u00EC', 12); // ì
        mapa.put('\u00F2', 13); // ò
        mapa.put('\u00F9', 14); // ù
        
        mapa.put('\u00E3', 15); // ã
        mapa.put('\u00F5', 16); // õ
        
        mapa.put('\u00E2', 17); // â
        mapa.put('\u00EA', 18); // ê
        mapa.put('\u00EE', 19); // î
        mapa.put('\u00F4', 20); // ô
        mapa.put('\u00FB', 21); // û
    }

    private static String obterHtml(String endereco) {
        StringBuilder resposta = new StringBuilder();
        try {
            URL url = new URI(endereco).toURL();
            BufferedReader leitor = new BufferedReader(new InputStreamReader(url.openStream(), StandardCharsets.UTF_8));
            String linha;
            while ((linha = leitor.readLine()) != null) {
                resposta.append(linha).append("\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resposta.toString();
    }

    static boolean ehBr(String s) {
        return s.equals("<br>");
    }

    static boolean ehTabela(String s) {
        return s.equals("<table>");
    }

    static int[] contarElementos(String texto) {
        int[] contagem = new int[25];
        String temp;
        for (int i = 0; i < texto.length(); i++) {
            char c = texto.charAt(i);
            Integer indice = mapa.get(c);
            if (indice != null) {
                contagem[indice]++;
            } else if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')) {
                contagem[22]++; // Consoante
            } else if (c == '<') {
                int j = i;
                while (j < texto.length() && texto.charAt(j) != '>') {
                    j++;
                }
                temp = texto.substring(i, j + 1);
                if (ehBr(temp)) {
                    contagem[23]++;
                    i += 3;
                } else if (ehTabela(temp)) {
                    contagem[24]++;
                    i += 6;
                }
            }
        }
        return contagem;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8);
        String nomePagina = scanner.nextLine();
        while (!nomePagina.equals("FIM")) {
            String endereco = scanner.nextLine();
            String html = obterHtml(endereco);
            int[] x = contarElementos(html);

            MyIO.print("a(" + x[0] + ") ");
            MyIO.print("e(" + x[1] + ") ");
            MyIO.print("i(" + x[2] + ") ");
            MyIO.print("o(" + x[3] + ") ");
            MyIO.print("u(" + x[4] + ") ");
            MyIO.print("\u00E1(" + x[5] + ") "); //á
            MyIO.print("\u00E9(" + x[6] + ") "); //é
            MyIO.print("\u00ED(" + x[7] + ") "); //í
            MyIO.print("\u00F3(" + x[8] + ") "); //ó
            MyIO.print("\u00FA(" + x[9] + ") "); //ú
            MyIO.print("\u00E0(" + x[10] + ") "); //à
            MyIO.print("\u00E8(" + x[11] + ") "); //è
            MyIO.print("\u00EC(" + x[12] + ") "); //ì
            MyIO.print("\u00F2(" + x[13] + ") "); //ò
            MyIO.print("\u00F9(" + x[14] + ") "); //ù
            MyIO.print("\u00E3(" + x[15] + ") "); //ã
            MyIO.print("\u00F5(" + x[16] + ") "); //õ
            MyIO.print("\u00E2(" + x[17] + ") "); //â
            MyIO.print("\u00EA(" + x[18] + ") "); //ê
            MyIO.print("\u00EE(" + x[19] + ") "); //î
            MyIO.print("\u00F4(" + x[20] + ") "); //ô
            MyIO.print("\u00FB(" + x[21] + ") "); //û
            MyIO.print("consoante(" + x[22] + ") ");
            MyIO.print("<br>(" + x[23] + ") ");
            MyIO.print("<table>(" + x[24] + ") ");
            MyIO.println(nomePagina);

            nomePagina = scanner.nextLine();
        }
        scanner.close();
    }
}

