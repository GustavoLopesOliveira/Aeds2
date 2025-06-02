#include<stdio.h>
#include<string.h>
#include<stdlib.h>
#include <math.h>


#define MAX 200
#define MAX_LIST 11

typedef struct Show {
    char showId[MAX];
    char type[MAX];
    char title[MAX];
    char director[MAX];
    char cast[MAX_LIST][MAX];
    char country[MAX];
    char dateAdded[MAX];
    int releaseYear;
    char rating[MAX];
    char duration[MAX];
    char listedIn[MAX_LIST][MAX];    
} Show;

Show newShow();
Show *ler();
void atribuir(Show *show, char *linha, int index);
char **separarLinha(char *linha);
char **separarLista(char *lista);
void ordenarLista(char **lista);
void imprimir(Show show);
int quantidade();

Show newShow() {
    Show show;
    strcpy(show.showId, "NaN");
    strcpy(show.type, "NaN");
    strcpy(show.title, "NaN");
    strcpy(show.director, "NaN");
    strcpy(show.cast[0], "NaN");
    for (int i = 1; i < MAX_LIST; i++) {
        show.cast[i][0] = '\0';
    }
    strcpy(show.country, "NaN");
    strcpy(show.dateAdded, "NaN");
    show.releaseYear = -1;
    strcpy(show.rating, "NaN");
    strcpy(show.duration, "NaN");
    strcpy(show.listedIn[0], "NaN");
    for (int i = 1; i < MAX_LIST; i++) {
        show.listedIn[i][0] = '\0';
    }
    return show;
}

int quantidade() {
    int cont = 0;
    FILE *arq = fopen("/tmp/disneyplus.csv", "r");
    char linha[1500];
    if (arq) {
        fgets(linha, sizeof(linha), arq);
        while (fgets(linha, sizeof(linha), arq)) {
            cont++;
        }
    } else {
        printf("Erro ao abrir o arquivo!");
    }
    fclose(arq);
    return cont;
}

Show *ler() {
    int i = 0;
    Show *show = (Show*)malloc(2000 * sizeof(Show));
    FILE *arq = fopen("/tmp/disneyplus.csv", "r");
    char linha[1500];
    if (arq) {
        fgets(linha, sizeof(linha), arq);
        while (fgets(linha, sizeof(linha), arq)) {
            atribuir(show, linha, i);
            i++;
        }
    } else {
        printf("Erro ao abrir o arquivo!");
    }
    fclose(arq);
    return show;
}

void atribuir(Show *show, char *linha, int index) {
    show[index] = newShow();
    char **str = separarLinha(linha);
    strcpy(show[index].showId, str[0]);
    if (strlen(str[1]) > 0) {
        strcpy(show[index].type, str[1]);
    } else {
        strcpy(show[index].type, "NaN");
    }
    if (strlen(str[2]) > 0) {
        strcpy(show[index].title, str[2]);
    } else {
        strcpy(show[index].title, "NaN");
    }
    if (strlen(str[3]) > 0) {
        strcpy(show[index].director, str[3]);
    } else {
        strcpy(show[index].director, "NaN");
    }
    if (strlen(str[4]) > 0) {
        char **lista = separarLista(str[4]);
        for (int i = 0; i < MAX_LIST && lista[i][0] != '\0'; i++) {
            strcpy(show[index].cast[i], lista[i]);
        }

        for (int i = 0; i < MAX_LIST; i++) {
            free(lista[i]);
        }
        free(lista);
    } else {
        strcpy(show[index].cast[0], "NaN");
        for (int i = 1; i < MAX_LIST; i++) {
            show[index].cast[i][0] = '\0';
        }
    }
    if (strlen(str[5]) > 0) {
        strcpy(show[index].country, str[5]);
    } else {
        strcpy(show[index].country, "NaN");
    }
    if (strlen(str[6]) > 0) {
        strcpy(show[index].dateAdded, str[6]);
    } else {
        strcpy(show[index].dateAdded, "March 1, 1900");
    }
    show[index].releaseYear = atoi(str[7]);
    if (strlen(str[8]) > 0) {
        strcpy(show[index].rating, str[8]);
    } else {
        strcpy(show[index].rating, "NaN");
    }
    if (strlen(str[9]) > 0) {
        strcpy(show[index].duration, str[9]);
    } else {
        strcpy(show[index].duration, "NaN");
    }
    if (strlen(str[10]) > 0) {
        char **lista = separarLista(str[10]);
        for (int i = 0; i < MAX_LIST && lista[i][0] != '\0'; i++) {
            strcpy(show[index].listedIn[i], lista[i]);
        }

        for (int i = 0; i < MAX_LIST; i++) {
            free(lista[i]);
        }
        free(lista);
    } else {
        strcpy(show[index].listedIn[0], "NaN");
        for (int i = 1; i < MAX_LIST; i++) {
            show[index].listedIn[i][0] = '\0';
        }
    }
    for (int i = 0; i < MAX_LIST; i++) {
        free(str[i]);
    }
    free(str);
}

char **separarLista(char *lista) {
    char **str = (char**)malloc(MAX_LIST * sizeof(char*));
    for (int k = 0; k < MAX_LIST; k++) {
        str[k] = (char*)malloc((MAX + 1) * sizeof(char));
        str[k][0] = '\0';
    }

    int i = 0, j = 0, pos = 0;
    while (lista[i] != '\0') {
        if (lista[i] != ',') {
            str[pos][j] = lista[i];
            j++;
        } else {
            str[pos][j] = '\0';
            pos++;
            j = 0;
            if (lista[i + 1] == ' ') { 
                i++;
            }
        }
        i++;
    }
    str[pos][j] = '\0';
    ordenarLista(str);
    return str;
}

void imprimir(Show show) {
    int i = 0;
    printf("=> %s ## ", show.showId);
    printf("%s ## ", show.title);
    printf("%s ## ", show.type);
    printf("%s ## ", show.director);
    printf("[");
    while (show.cast[i][0] != '\0') {
        printf("%s", show.cast[i]);
        if (show.cast[i + 1][0] != '\0') {
            printf(", ");
        }
        i++;
    }
    printf("] ## ");
    printf("%s ## ", show.country);
    printf("%s ## ", show.dateAdded);
    printf("%d ## ", show.releaseYear);
    printf("%s ## ", show.rating);
    printf("%s ## ", show.duration);
    printf("[");
    i = 0;
    while (show.listedIn[i][0] != '\0') {
        printf("%s", show.listedIn[i]);
        if (show.listedIn[i + 1][0] != '\0') {
            printf(", ");
        }
        i++;
    }
    printf("] ##");
    printf("\n");
}

void ordenarLista(char **lista) {
    char temp[MAX];
    int tam = 0;
    while (lista[tam][0] != '\0') {
        tam++;
    }
    tam--;
    for (int i = tam; i > 0; i--) {
        for (int j = 0; j < i; j++) {
            if (strcmp(lista[j], lista[j + 1]) > 0) {
                strcpy(temp, lista[j]);
                strcpy(lista[j], lista[j + 1]);
                strcpy(lista[j + 1], temp);
            }
        }
    }
}

char **separarLinha(char *linha) {
    char **str = (char**)malloc(MAX_LIST * sizeof(char*));
    for (int k = 0; k < MAX_LIST; k++) {
        str[k] = (char*)malloc((MAX + 1) * sizeof(char));
        str[k][0] = '\0';
    }

    int aux = 0, i = 0, j = 0;
    while (i < strlen(linha) && aux < 11) {
        char letra = linha[i];
        char letra2;
        if (i + 1 < strlen(linha)) {
            letra2 = linha[i + 1];
        }
        if (letra == ',' && letra2 != ' ') {
            str[aux][j] = '\0';
            aux++;
            j = 0;
        } else {
            if (letra != '"') {
                str[aux][j] = letra;
                j++;
            } else {
                i++;
                letra = linha[i];
                while (i < strlen(linha) && linha[i] != '"') {
                    str[aux][j] = letra;
                    j++;
                    i++;
                    letra = linha[i];
                }
            }
        }
        i++;
    }
    if (j > 0 && aux < MAX_LIST) {
        str[aux][j] = '\0';
    }
    return str;
}


// Fila Circular;

typedef struct Node
{
    struct Node *next;
    struct Node *previous;
    Show *value;
} Node;
Node *createNode(Show *show)
{
    Node *newNode = (Node *)malloc(sizeof(Node));
    newNode->value = show;
    newNode->next = NULL;
    newNode->previous = NULL;
    return newNode;
}

typedef struct QueueLLShow
{
    Node *head;
    Node *tail;
    int size;
    int sum;
    int maxSize;
} QueueLLShow;
QueueLLShow *createQueueLLShow()
{
    QueueLLShow *ll = (QueueLLShow *)malloc(sizeof(QueueLLShow));
    ll->head = NULL;
    ll->tail = NULL;
    ll->size = 0;
    ll->sum = 0;
    ll->maxSize = 5;
    return ll;
}
Show *dequeueLL(QueueLLShow *q)
{
    if (q->head == NULL)
        return NULL;

    Node *node = q->tail;
    Show *removed = node->value;

    if (q->head == q->tail)
    {
        q->head = NULL;
        q->tail = NULL;
    }
    else
    {
        q->tail = q->tail->previous;
        q->tail->next = NULL;
    }

    free(node);
    q->size--;
    q->sum -= removed->releaseYear;

    return removed;
}
void enqueueLL(QueueLLShow *q, Show *show)
{
    if (q->size == q->maxSize)
    {
        dequeueLL(q);
    }
    Node *newNode = createNode(show);
    if (q->head == NULL && q->tail == NULL)
    {
        q->tail = q->head = newNode;
        newNode->next = newNode->previous = newNode;
    }
    else
    {
        newNode->next = q->head;
        newNode->previous = q->tail;
        q->head->previous = newNode;
        q->tail->next = newNode;
        q->head = newNode;
    }
    q->size++;
    q->sum += show->releaseYear;

    printf("[Media] %d\n", q->sum / q->size);
}
void printQueueLL(QueueLLShow *s)
{
    Node *cur = s->tail;
    for (int i = s->size - 1; i >= 0 && cur != NULL; cur = cur->previous, i--)
    {
        printf("[%d] ", i);
        imprimir(*(cur->value));
    }
}

int main() {
    Show *show = ler();
    int quant = quantidade();
    char id[MAX_LIST];
    scanf(" %[^\r\n]", id);



        QueueLLShow *list  = createQueueLLShow();

    while (strcmp(id, "FIM") != 0) {
        for (int i = 0; i < quant; i++) {
            if (strcmp(id, show[i].showId) == 0) {
                enqueueLL(list, show+i);
            }
        } 
        scanf(" %[^\r\n]", id);
    }

    int tt;
    scanf("%d", &tt);
    getchar();

    char line[256];
    for(int i = 0; i < tt; i++){
        if (fgets(line, sizeof(line), stdin) == NULL) break;

        char op[4], showId[MAX];
        if (line[0] == 'I') {
            sscanf(line, "%s %s", op, showId);
            for (int j = 0; j < quant; j++) {
            if (strcmp(showId, show[j].showId) == 0) {
                enqueueLL(list, &show[j]);
                break;
            }
            }
        } else if (line[0] == 'R') {
            Show *removedShow = dequeueLL(list);
            if (removedShow != NULL && strcmp(removedShow->showId, "NaN") != 0) {
            printf("(R) %s\n", removedShow->title);
            } else {
            printf("Fila vazia!\n");
            }
        }
    }

    printQueueLL(list);
}