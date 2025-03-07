import java.util.Scanner;

public class Ex06{
    
    //Funcoes auxiliares
    public static boolean eNumero(char c){
        if(c == '1') return true;
        if(c == '2') return true;
        if(c == '3') return true;
        if(c == '4') return true;
        if(c == '5') return true;
        if(c == '6') return true;
        if(c == '7') return true;
        if(c == '8') return true;
        if(c == '9') return true;

        return false;
    }

    public static boolean eVogal(char c){
        
        if( c == 'a' || c == 'A' ) return true;
        if( c == 'e' || c == 'E' ) return true;
        if( c == 'i' || c == 'I' ) return true;
        if( c == 'o' || c == 'O' ) return true;
        if( c == 'u' || c == 'U' ) return true;

        return false;
    }
    
    //Verifica se todos os char de uma string sao vogais.
    public static boolean somenteVogais(String str){
        
        for(int i = 0; i < str.length(); i++){
            if(!eVogal(str.charAt(i))) return false; 
        }

        return true;
    }
    
    //Verifica se todos os char de uma string sao consoantes.
    public static boolean somenteConsoantes(String str){
        
        for(int i = 0; i < str.length(); i++){
            if(eVogal(str.charAt(i))) return false;
        }

        return true;
    }

    public static boolean eNumeroInteiro(String str){

        for(int i = 0; i < str.length(); i++){
            if(!eNumero(str.charAt(i))) return false;
        }

        return true;
    }

    public static boolean eNumeroReal(String str){
        
        int contPonto = 0, contVirgula = 0;

        for(int i = 0; i < str.length(); i++){
            char c = str.charAt(i);

            if(c == '.'){
                contPonto++;
            }else if(c == ','){
                contVirgula++;
            }else if(!eNumero(str.charAt(i))) {
                return false;
            }
        }

        if(contPonto + contVirgula != 1) return false;

        return true;
    }


    public static void main(String[] args){
    
        

        String str;

        str = MyIO.readLine();

	do{
		String saida = "";

		if(somenteVogais(str)){
			saida += "SIM ";
		}else{
			saida += "NAO ";
		}

		if(somenteConsoantes(str)){
			saida += "SIM ";
		}else{
			saida += "NAO ";
		}

		if(eNumeroInteiro(str)){
			saida += "SIM ";
		}else{
			saida += "NAO ";
		}

		if(eNumeroReal(str)){
			saida += "SIM";
		}else{
			saida += "NAO";
		}

		MyIO.println(saida);
		str = MyIO.readLine();


	}while(!str.equals("FIM"));

    }

}
