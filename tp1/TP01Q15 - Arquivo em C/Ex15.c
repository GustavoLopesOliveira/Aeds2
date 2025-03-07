#include <stdio.h>

int main() {
    FILE *arquivo;
    int n;
    double numero;

    // Abrindo o arquivo para escrita
    arquivo = fopen("dados.txt", "wb");  // Usamos "wb" para escrita binária
    if (arquivo == NULL) {
        printf("Erro ao abrir o arquivo para escrita.\n");
        return 1;
    }

    // Lendo o número de valores
    scanf("%d", &n);

    // Escrevendo os valores no arquivo
    for (int i = 0; i < n; i++) {
        scanf("%lf", &numero);
        fwrite(&numero, sizeof(double), 1, arquivo);
    }

    fclose(arquivo);  // Fecha o arquivo após a escrita

    // Reabrindo o arquivo para leitura reversa
    arquivo = fopen("dados.txt", "rb");  // "rb" para leitura binária
    if (arquivo == NULL) {
        printf("Erro ao abrir o arquivo para leitura.\n");
        return 1;
    }

    // Posicionando o ponteiro no final do último número
    fseek(arquivo, -sizeof(double), SEEK_END);

    // Lendo os números de trás para frente
    for (int i = 0; i < n; i++) {
        fread(&numero, sizeof(double), 1, arquivo);
        if(numero == (int) numero){
		printf("%.0lf\n",numero);
	}else{
		printf("%.3lf\n", numero);
	}
        // Se não for o primeiro número, move para o anterior
        if (i < n - 1) {
            fseek(arquivo, -2 * sizeof(double), SEEK_CUR);
        }
    }

    fclose(arquivo);  // Fecha o arquivo após a leitura

    return 0;
}

