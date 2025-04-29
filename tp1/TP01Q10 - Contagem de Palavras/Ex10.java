import java.util.Scanner;

public class Ex10{

    public static void main(String[] args){

        String str;
	Scanner input = new Scanner(System.in);

        str = input.nextLine();
	do{
        	String[] a = str.split(" ");
        
        	int cont = a.length;

        	System.out.println(cont);
		
		str = input.nextLine();
	}while(!str.equals("FIM"));

	input.close();
    }
}
