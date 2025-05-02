import java.util.Scanner;

public class Ex07{
    
    //Metodo iterativo que cria uma nova String ao contrario e retorna ela.
    public static String inverteString(String str){
        String strInvertida = "";

        int tam = str.length();

        for(int i = 0; i < tam; i++){
            strInvertida += str.charAt(tam - 1 - i);
        }

        return strInvertida;

    }

    public static void main(String[] args){
        String str;

        Scanner input = new Scanner(System.in);

        str = input.nextLine();

       System.out.println(inverteString(str));
       
       input.close();
    }
}
