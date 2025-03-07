import java.util.Scanner;

public class Ex20 {
    
    // Funções auxiliares 
    public static boolean eNumero(char c) {
        return (c >= '0' && c <= '9');
    }

    public static boolean eVogal(char c) {
        return "aeiouAEIOU".indexOf(c) != -1;
    }
    
    public static boolean somenteVogais(String str, int i) {
        if (i == str.length()) return true; 
        if (!eVogal(str.charAt(i))) return false;
        return somenteVogais(str, i + 1);
    }

    public static boolean somenteVogais(String str) {
        return somenteVogais(str, 0);
    }

    public static boolean somenteConsoantes(String str, int i) {
        if (i == str.length()) return true;
        if (eVogal(str.charAt(i)) || eNumero(str.charAt(i))) return false;
        return somenteConsoantes(str, i + 1);
    }

    public static boolean somenteConsoantes(String str) {
        return somenteConsoantes(str, 0);
    }

    public static boolean eNumeroInteiro(String str, int i) {
        if (i == str.length()) return true;
        if (!eNumero(str.charAt(i))) return false;
        return eNumeroInteiro(str, i + 1);
    }

    public static boolean eNumeroInteiro(String str) {
        return eNumeroInteiro(str, 0);
    }

    public static boolean eNumeroReal(String str, int i, int contPonto, int contVirgula) {
        if (i == str.length()) return (contPonto + contVirgula == 1);
        char c = str.charAt(i);

        if (c == '.') contPonto++;
        else if (c == ',') contVirgula++;
        else if (!eNumero(c)) return false;

        return eNumeroReal(str, i + 1, contPonto, contVirgula);
    }

    public static boolean eNumeroReal(String str) {
        return eNumeroReal(str, 0, 0, 0);
    }

    public static void processarEntrada() {
        Scanner scanner = new Scanner(System.in);
        processarEntradaRecursivo(scanner);
        scanner.close();
    }

    public static void processarEntradaRecursivo(Scanner scanner) {
        String str = scanner.nextLine();

        if (str.equals("FIM")) return;

        String saida = "";

        if (somenteVogais(str)) {
            saida += "SIM ";
        } else {
            saida += "NAO ";
        }

        if (somenteConsoantes(str)) {
            saida += "SIM ";
        } else {
            saida += "NAO ";
        }

        if (eNumeroInteiro(str)) {
            saida += "SIM ";
        } else {
            saida += "NAO ";
        }

        if (eNumeroReal(str)) {
            saida += "SIM";
        } else {
            saida += "NAO";
        }

        System.out.println(saida);
        processarEntradaRecursivo(scanner); // Chamada recursiva no lugar do loop
    }

    public static void main(String[] args) {
        processarEntrada();
    }
}

