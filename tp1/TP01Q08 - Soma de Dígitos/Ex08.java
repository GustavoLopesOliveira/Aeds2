import java.util.Scanner;

public class Ex08 {
    private static int somaDigitos(String number) {
        int soma = 0;

	for(int i = 0; i < number.length(); i++){
		soma += (int) number.charAt(i) - '0';
	}

	return soma;
    }
    public static void main(String[] args){
        Scanner input = new Scanner(System.in);

	String str = input.nextLine();
        while(!str.equals("FIM")) {
            MyIO.println(somaDigitos(str));
            str = input.nextLine();
        }
    }
}
