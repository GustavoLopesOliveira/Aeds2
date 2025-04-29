import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.Scanner;

public class Ex05 {
    private static boolean boolArg(String arg) {
        return arg.equals("1");
    }
    
    private static String boolParaString(boolean arg) {
        return arg ? "1" : "0";
    }
    
    private static int charParaInt(char c) {
        return c - '0';
    }
    
    private static boolean ou(List<String> args) {
        boolean resultado = false;
        for (String arg : args) {
            if (boolArg(arg)) {
                return true;
            }
            resultado = resultado || boolArg(arg);
        }
        return resultado;
    }
    
    private static boolean e(List<String> args) {
        boolean resultado = true;
        for (String arg : args) {
            if (!boolArg(arg)) {
                return false;
            }
            resultado = resultado && boolArg(arg);
        }
        return resultado;
    }
    
    private static boolean nao(boolean arg) {
        return !arg;
    }
    
    private static boolean valorDoArgumento(List<Integer> argumentos, char letra) {
        return argumentos.get(Character.toLowerCase(letra) - 'a') == 1;
    }
    
    private static int obterVariaveis(String cmd, List<Integer> argumentos) {
        int i = 0;
        int qtd = 0;
        for (i = 0; cmd.charAt(i) != ' '; i++) {
            qtd = (qtd * 10) + charParaInt(cmd.charAt(i));
        }
        i++;
        for (int j = 0; j < qtd; i += 2, j++) {
            argumentos.add(charParaInt(cmd.charAt(i)));
        }
        return i;
    }
    
    private static String analisar(String cmd) {
        List<Integer> argumentos = new ArrayList<>();
        int i = obterVariaveis(cmd, argumentos);
        Stack<String> pilha = new Stack<>();
        for (int j = i; i < cmd.length(); i++) {
            char c = cmd.charAt(i);
            if (c == '(') {
                pilha.push(cmd.substring(j, i));
                j = i + 1;
            } else if (c >= 'A' && c <= 'Z') {
                pilha.push(boolParaString(valorDoArgumento(argumentos, c)));
                j = i + 1;
            } else if (c == ')') {
                List<String> listaDeArgumentos = new ArrayList<>();
                while (pilha.peek().charAt(0) == '0' || pilha.peek().charAt(0) == '1') {
                    listaDeArgumentos.add(pilha.pop());
                }
                String operacao = pilha.pop();
                boolean resultado = switch (operacao) {
                    case "nao" -> nao(boolArg(listaDeArgumentos.getFirst()));
                    case "e" -> e(listaDeArgumentos);
                    default -> ou(listaDeArgumentos);
                };
                pilha.push(boolParaString(resultado));
                j = i + 1;
            }
            while (j < cmd.length() && (cmd.charAt(j) == ' ' || cmd.charAt(j) == ',')) {
                j++;
            }
        }
        return pilha.pop();
    }
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String texto = sc.nextLine();
        while (!texto.equals("0")) {
            System.out.println(analisar(texto));
            texto = sc.nextLine();
        }
        sc.close();
    }
}
