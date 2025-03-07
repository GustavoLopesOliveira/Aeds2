#include <stdio.h>
#include <string.h>
#include <locale.h>
#include <ctype.h>

int verificarPalindromo(unsigned char *str, int pos) {
    int tamanho = strlen((char *)str);
    if (pos >= tamanho - pos - 1) {
        return 1;
    }
    int temp = pos;
    if (str[temp] == 195) { // Caracter especial de 2 bytes
        temp++;
        if (str[temp] != str[tamanho - temp]) return 0;
    } else if (str[temp] == 239) { // Caracter especial de 3 bytes
        temp += 2;
        if (str[tamanho - temp - 1] != 239) return 0;
    } else if (str[temp] != str[tamanho - temp - 1]) {
        return 0;
    }
    return verificarPalindromo(str, temp + 1);
}

int main() {
    setlocale(LC_ALL, "pt_BR.utf8");
    unsigned char entrada[1000];
    scanf(" %[^\n]", entrada);
    
    while (strcmp((char *)entrada, "FIM")) {
        if (verificarPalindromo(entrada, 0)) {
            printf("SIM\n");
        } else {
            printf("NAO\n");
        }
        scanf(" %[^\n]", entrada);
    }
    return 0;
}
