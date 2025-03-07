import java.io.*;

public class Ex14{
    public static void main(String[] args) {
        try {
            int n = MyIO.readInt();
            String nomeArquivo = "dados.txt";
            
            RandomAccessFile arquivo = new RandomAccessFile(nomeArquivo, "rw");
            
            for (int i = 0; i < n; i++) {
                String valor = MyIO.readLine();
                arquivo.writeLine(valor);
            }
            
            arquivo.close();
            arquivo = new RandomAccessFile(nomeArquivo, "r");
            
            long tamanhoArquivo = arquivo.length();
            long posicao = tamanhoArquivo - 8;
            
            while (posicao >= 0) {
                arquivo.seek(posicao);
                String valor = arquivo.readLine();
                System.out.println(valor);
                posicao -= 8;
            }
            
            arquivo.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

