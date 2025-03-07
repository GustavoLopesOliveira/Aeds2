#include<stdio.h>
#include<string.h>
#include<locale.h>
#include<ctype.h>

void invert(char* s, int i){
    int j = strlen(s) - 1 -i;
    if(i>=j){
        return ;
    }
    char temp = s[i];
    s[i] = s[j];
    s[j] = temp;
    
    invert(s, i+1);
}

int main() {
     char text[1000];
    scanf(" %[^\r\n]", text);
    while (strcmp((char*)text, "FIM")) {
        invert(text,0);
        printf("%s\n", text);
        scanf(" %[^\n]", text);
    }

    return 0;
}
