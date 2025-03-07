#include <stdio.h>
#include <string.h>

int somaDigito(char* str, int tam,int index){
    if(index > tam){
	return 0;
    }
    int valor = (int) str[index] - '0';	
    return valor + somaDigito(str,tam,index+1);
}

int main(){
    char str[100]; 
    scanf("%s",str);

    while(strcmp(str,"FIM") != 0){
	printf("%d\n",somaDigito(str,strlen(str)-1,0));
	scanf("%s",str);
    }
}
