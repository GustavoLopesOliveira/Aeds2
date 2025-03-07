public class Ex16{

	public static boolean ePalindromo(String str, int i, int j){
		if(j == 0 ) return true;

		if(str.charAt(i) == str.charAt(j)){
			return true && ePalindromo(str,i+1,j-1);
		}

		return false;
	}

	public static boolean ePalindromo(String str){
		return ePalindromo(str,0,str.length() -1);
	}

	public static void main(String[] args){
		String str;

		str = MyIO.readLine();

		do{
			String saida;
			if(ePalindromo(str)){
				saida = "SIM";
			}else{
				saida = "NAO";
			}

			MyIO.println(saida);

			str = MyIO.readLine();

		}while(!str.equals("FIM"));
	
	}
}
