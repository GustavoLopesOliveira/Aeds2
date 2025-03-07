import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

public class Ex19 {
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

    private static int obterIndiceFechamento(String cmd, int i) {
        int aberto = 0;
        for (; i < cmd.length(); i++) {
            char c = cmd.charAt(i);
            if (c == '(') {
                aberto++;
            } else if (c == ')') {
                aberto--;
            }
            if (aberto == 0) {
                return i;
            }
        }
        return i;
    }

    private static int obterVariaveis(String cmd, List<Integer> argumentos) {
        int i = 0;
        int qtd = 0;
        for (i = 0; cmd.charAt(i) != ' '; i++) {
            qtd = (qtd * 10) + charParaInt(cmd.charAt(i));
        }
        i++; // Pular o espaço
        for (int j = 0; j < qtd; i += 2, j++) {
            argumentos.add(charParaInt(cmd.charAt(i)));
        }
        return i;
    }

    private static void realizarOperacoes(Stack<String> pilha) {
        List<String> listaDeArgumentos = new ArrayList<>();
        while (pilha.peek().charAt(0) == '0' || pilha.peek().charAt(0) == '1') {
            listaDeArgumentos.add(pilha.pop());
        }

        String operacao = pilha.pop();
        boolean resultado = switch (operacao) {
            case "nao" -> nao(boolArg(listaDeArgumentos.get(0)));
            case "e" -> e(listaDeArgumentos);
            default -> ou(listaDeArgumentos);
        };
        pilha.push(boolParaString(resultado));
    }

    private static String metodo(String cmd) {
        if (cmd.equals("or")) {
            return "ou";
        } else if (cmd.equals("nd")) {
            return "e";
        }
        return "nao";
    }

    private static void analisar(String cmd, List<Integer> argumentos, Stack<String> pilha, int i) {
        if (i >= cmd.length()) {
            return;
        }

        char c = cmd.charAt(i);
        int icpy = i + 1;
        if (c == '(') {
            int j = obterIndiceFechamento(cmd, i);
            pilha.push(metodo(cmd.substring(i - 2, i)));
            analisar(cmd.substring(i + 1, j), argumentos, pilha, 0);
            realizarOperacoes(pilha);
            icpy = j;
        } else if (c >= 'A' && c <= 'Z') {
            pilha.push(boolParaString(valorDoArgumento(argumentos, c)));
        }

        analisar(cmd, argumentos, pilha, icpy);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String texto = sc.nextLine();
        while (!texto.equals("0")) {
            Stack<String> pilha = new Stack<>();
            List<Integer> argumentos = new ArrayList<>();
            int i = obterVariaveis(texto, argumentos);
            analisar(texto, argumentos, pilha, i);
            System.out.println(pilha.pop());
            texto = sc.nextLine();
        }
        sc.close();
    }
}

