import java.util.Scanner;

public class Ex12{

    public static boolean validaSenha(String str){
        if(str.length() < 8) return false;

        boolean maiscula = false;
        boolean minuscula = false;
        boolean number = false;
        boolean special = false;
        
        for(int i = 0; i < str.length(); i++){
            int temp = (int) str.charAt(i);

            if(temp >= 65 && temp <= 90) maiscula = true;
            
            if(temp >= 97 && temp <= 122) minuscula = true;

            if(temp >= 48 && temp <= 57) number = true;

            if(!(temp >= 65 && temp <= 90) && !(temp >= 97 && temp <= 122) && !(temp >= 48 && temp <= 57)) special = true;
        }

        return maiscula && minuscula && number && special;

    }

    public static void main(String[] args){
        Scanner input = new Scanner(System.in);

        String senha;

        senha = input.nextLine();
	
	while(!senha.equals("FIM")){
        	if(validaSenha(senha)){
            		System.out.println("SIM");
        	}else{
            		System.out.println("NÃO");
        	}
		senha = input.nextLine();
	}
    }

}
