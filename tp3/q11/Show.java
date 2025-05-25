import java.util.Scanner;

public class Show {
    static class Matriz {
        class Node {
            int data;
            Node up, down, left, right;

            Node(int data) {
                this.data = data;
                up = down = left = right = null;
            }
        }

        Node first = null;

        Matriz(int linha, int coluna) {
            Node prev = null;
            for (int j = 0; j < coluna; j++) {
                Node newNode = new Node(0);
                if (first == null) {
                    first = newNode;
                } else {
                    prev.right = newNode;
                    newNode.left = prev;
                }
                prev = newNode;
            }

            Node top = first;
            for (int i = 1; i < linha; i++) {
                Node rowStart = null;
                prev = null;
                
                for (int j = 0; j < coluna; j++) {
                    Node newNode = new Node(0);
                    if (rowStart == null) {
                        rowStart = newNode;
                    } else {
                        prev.right = newNode;
                        newNode.left = prev;
                    }
                    
                    newNode.up = top;
                    top.down = newNode;
                    
                    prev = newNode;
                    top = top.right;
                }
                top = rowStart;
            }
        }

        public int get(int linha, int coluna) {
            Node i = first;
            for (int j = 0; j < linha; j++) {
                i = i.down;
                if (i == null) return 0; 
            }
            for (int j = 0; j < coluna; j++) {
                i = i.right;
                if (i == null) return 0; 
            }
            return i.data;
        }

        public Matriz soma(Matriz m, int linha, int coluna) {
            Matriz resultado = new Matriz(linha, coluna);
            for (int i = 0; i < linha; i++) {
                for (int j = 0; j < coluna; j++) {
                    resultado.set(i, j, this.get(i, j) + m.get(i, j));
                }
            }
            return resultado;
        }

        public Matriz multiplicacao(Matriz m, int linha, int coluna) {
            Matriz resultado = new Matriz(linha, coluna);
            for (int i = 0; i < linha; i++) {
                for (int j = 0; j < coluna; j++) {
                    int soma = 0;
                    for (int k = 0; k < coluna; k++) {
                        soma += this.get(i, k) * m.get(k, j);
                    }
                    resultado.set(i, j, soma);
                }
            }
            return resultado;
        }

        public void diagonalPrincipal(int linha, int coluna) {
            for (int i = 0; i < linha; i++) {
                for (int j = 0; j < coluna; j++) {
                    if (i == j) {
                        System.out.print(this.get(i, j) + " ");
                    }
                }
            }
            System.out.println();
        }

        public void diagonalSecundaria(int linha, int coluna) {
            for (int i = 0; i < linha; i++) {
                for (int j = 0; j < coluna; j++) {
                    if (i + j == coluna - 1) {
                        System.out.print(this.get(i, j) + " ");
                    }
                }
            }
            System.out.println();
        }

        public void printMatriz() {
            Node i = first;
            while (i != null) {
                Node j = i;
                while (j != null) {
                    System.out.print(j.data + " ");
                    j = j.right;
                }
                System.out.println();
                i = i.down;
            }
        }

        public void set(int linha, int coluna, int valor) {
            Node i = first;
            for (int j = 0; j < linha; j++) {
                if (i == null) return; 
                i = i.down;
            }
            for (int j = 0; j < coluna; j++) {
                if (i == null) return; 
                i = i.right;
            }
            if (i != null) {
                i.data = valor;
            }
        }
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        int tt = input.nextInt();

        for (int t = 0; t < tt; t++) {
            int linha1 = input.nextInt();
            int coluna1 = input.nextInt();
            Matriz m1 = new Matriz(linha1, coluna1);
            for (int i = 0; i < linha1; i++) {
                for (int j = 0; j < coluna1; j++) {
                    m1.set(i, j, input.nextInt());
                }
            }

            int linha2 = input.nextInt();
            int coluna2 = input.nextInt();
            Matriz m2 = new Matriz(linha2, coluna2);
            for (int i = 0; i < linha2; i++) {
                for (int j = 0; j < coluna2; j++) {
                    m2.set(i, j, input.nextInt());
                }
            }

            m1.diagonalPrincipal(linha1, coluna1);
            m1.diagonalSecundaria(linha1, coluna1);

            if (linha1 == linha2 && coluna1 == coluna2) {
                Matriz soma = m1.soma(m2, linha1, coluna1);
                soma.printMatriz();
            }

            if (coluna1 == linha2) {
                Matriz mult = m1.multiplicacao(m2, linha1, coluna2);
                mult.printMatriz();
            }
        }
    }
}