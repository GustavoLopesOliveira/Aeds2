public class Ex18 {

    // Método para cifrar a string recursivamente
    public static String cifra(String str) {
        return cifra(str, 0);
    }

    private static String cifra(String str, int i) {
        if (i == str.length()) return ""; // Caso base

        char c = (char) (str.charAt(i) + 3);
        return c + cifra(str, i + 1); 
    }

    public static void main(String[] args) {
        String str;

        while (true) {
            str = MyIO.readLine();
            if (str.equals("FIM")) return;

            String resultado = cifra(str); 
            MyIO.println(resultado);
        }
    }
}

