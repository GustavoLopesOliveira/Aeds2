
public class Ex10{

    public static void main(String[] args){

        String str; 

        str = MyIO.readLine();
	do{
        	int cont = 0;

        	for(int i = 0; i < str.length(); i++){
            	if(str.charAt(i) == ' ') cont++;
        	}
        
        	cont++;

        	System.out.println(cont);
		
		str = MyIO.readLine();
	}while(!str.equals("FIM"));
    }
}
