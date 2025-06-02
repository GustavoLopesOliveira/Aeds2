#include<stdio.h>
#include<string.h>
#include<stdlib.h>

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


// Double Linked List

typedef struct Node {
    Show show;
    struct Node *next;
    struct Node *prev;
} Node;

Node* createNode(Show show){
    Node *newNode = (Node*) malloc(sizeof(Node));
    newNode->show = show;
    newNode->next = NULL;
    newNode->prev = NULL;
}

typedef struct List {
    Node *head;
    Node *tail;
} List;

List *createList(){
    List* list = (List*) malloc(sizeof(List));
    list->head = NULL;
    list->tail = NULL;
    return list;
}

void insertAtBegin(List *list, Show show) {
    Node *newNode = createNode(show);
    if (list->head == NULL) {
        list->head = newNode;
        list->tail = newNode;
    } else {
        newNode->next = list->head;
        list->head->prev = newNode;
        list->head = newNode;
    }
}

void insert(List* list, Show show, int pos) {
    if (pos == 0) {
        insertAtBegin(list, show);
    } else {
        Node *newNode = createNode(show);
        Node *current = list->head;
        for (int i = 0; i < pos - 1 && current != NULL; i++) {
            current = current->next;
        }
        if (current == NULL) {
            insertAtEnd(list, show);
        } else {
            newNode->next = current->next;
            newNode->prev = current;
            if (current->next != NULL) {
                current->next->prev = newNode;
            } else {
                list->tail = newNode;
            }
            current->next = newNode;
        }
    }
}

void insertAtEnd(List *list, Show show) {
    Node *newNode = createNode(show);
    if (list->head == NULL) {
        list->head = newNode;
        list->tail = newNode;
    } else {
        list->tail->next = newNode;
        newNode->prev = list->tail;
        list->tail = newNode;
    }
}

Show removeAtBegin(List *list) {
    if (list->head == NULL) {
        printf("Lista vazia!\n");
        return newShow();
    }
    Node *temp = list->head;
    Show show = temp->show;
    list->head = temp->next;
    if (list->head != NULL) {
        list->head->prev = NULL;
    } else {
        list->tail = NULL;
    }
    free(temp);
    return show;
}

Show removeAtEnd(List *list) {
    if (list->tail == NULL) {
        printf("Lista vazia!\n");
        return newShow();
    }
    Node *temp = list->tail;
    Show show = temp->show;
    list->tail = temp->prev;
    if (list->tail != NULL) {
        list->tail->next = NULL;
    } else {
        list->head = NULL;
    }
    free(temp);
    return show;
}

Show removeAt(List *list, int pos) {
    if (list->head == NULL) {
        printf("Lista vazia!\n");
        return newShow();
    }
    if (pos == 0) {
        return removeAtBegin(list);
    }
    Node *current = list->head;
    for (int i = 0; i < pos && current != NULL; i++) {
        current = current->next;
    }
    if (current == NULL) {
        printf("Posição inválida!\n");
        return newShow();
    }
    Show show = current->show;
    if (current->prev != NULL) {
        current->prev->next = current->next;
    } else {
        list->head = current->next;
    }
    if (current->next != NULL) {
        current->next->prev = current->prev;
    } else {
        list->tail = current->prev;
    }
    free(current);
    return show;
}

void showList(List *list) {
    Node *current = list->head;
    while (current != NULL) {
        imprimir(current->show);
        current = current->next;
    }
}

int compareShowDate(const char *a, const char *b) {
    // Assume format: "Month day, year" (e.g., "March 1, 2020")
    // Compare year, then month, then day
    int dayA = 0, yearA = 0, dayB = 0, yearB = 0;
    char monthA[20], monthB[20];
    if (sscanf(a, "%19s %d, %d", monthA, &dayA, &yearA) != 3) return strcmp(a, b);
    if (sscanf(b, "%19s %d, %d", monthB, &dayB, &yearB) != 3) return strcmp(a, b);

    if (yearA != yearB) return yearA - yearB;
    int monthNumA = 0, monthNumB = 0;
    const char *months[] = {"January","February","March","April","May","June","July","August","September","October","November","December"};
    for (int i = 0; i < 12; i++) {
        if (strcasecmp(monthA, months[i]) == 0) monthNumA = i+1;
        if (strcasecmp(monthB, months[i]) == 0) monthNumB = i+1;
    }
    if (monthNumA != monthNumB) return monthNumA - monthNumB;
    return dayA - dayB;
}

int compareByDateAddedThenTitle(Show *a, Show *b) {
    int cmpDate = compareShowDate(a->dateAdded, b->dateAdded);
    if (cmpDate != 0) {
        return cmpDate;
    }
    return strcasecmp(a->title, b->title);
}

void swapS(Node *a, Node *b, int *mov) {
    Show temp = a->show;
    a->show = b->show;
    b->show = temp;
    if (mov) *mov += 3;
}

Node *partition(Node *low, Node *high, int *comp, int *mov) {
    Show *pivot = &high->show;
    Node *i = low->prev;
    for (Node *j = low; j != high; j = j->next) {
        if (comp) (*comp)++;
        if (compareByDateAddedThenTitle(&j->show, pivot) < 0) {
            i = (i == NULL) ? low : i->next;
            swapS(i, j, mov);
        }
    }
    i = (i == NULL) ? low : i->next;
    swapS(i, high, mov);
    return i;
}

void quickSort(Node *low, Node *high, int *comp, int *mov) {
    if (low != NULL && high != NULL && low != high && low != high->next) {
        Node *pivot = partition(low, high, comp, mov);
        quickSort(low, pivot->prev, comp, mov);
        quickSort(pivot->next, high, comp, mov);
    }
}

int length(List *list) {
    int count = 0;
    Node *current = list->head;
    while (current != NULL) {
        count++;
        current = current->next;
    }
    return count;
}

Node* getNodeAt(List* list, int index) {
    Node* current = list->head;
    int idx = 0;
    while (current != NULL && idx < index) {
        current = current->next;
        idx++;
    }
    return current;
}

void arquivoLog(double duracao, int comparacoes, int movimentacoes) {
    const char* matricula = "869118";
    FILE* f = fopen("869118_quicksort2.txt", "w");
    if (f != NULL) {
        fprintf(f, "%s\t%d\t%d\t%.6fms", matricula, comparacoes, movimentacoes, duracao);
        fclose(f);
    } else {
        fprintf(stderr, "Erro para escrever no arquivo de log.\n");
    }
}

int main() {
    Show *show = ler();
    int quant = quantidade();
    char id[MAX_LIST];
    scanf(" %[^\r\n]", id);

    List *list = createList();

    while (strcmp(id, "FIM") != 0) {
        for (int i = 0; i < quant; i++) {
            if (strcmp(id, show[i].showId) == 0) {
                insertAtEnd(list, show[i]);
            }
        } 
        scanf(" %[^\r\n]", id);
    }

    // int tt;
    // scanf("%d", &tt);
    // getchar();

    // char line[256];
    // for(int i = 0; i < tt; i++){
    //     if (fgets(line, sizeof(line), stdin) == NULL) break;

    //     char op[4], showId[MAX];
    //     int pos;
    //     if(line[1] == '*'){
    //         sscanf(line, "%s %d %s", op, &pos, showId);
    //     }else{
    //         sscanf(line, "%s %s", op, showId);
    //     }
    //printf("%s %s %d\n",op, showId, pos);
    //     if(strcmp(op, "II") == 0){
    //             for(int i = 0; i < quant; i++){
    //                 if(strcmp(showId,show[i].showId) == 0){
    //                     insertAtBegin(list, show[i]);
    //                     break;
    //                 }
    //             }
    //         }else if(strcmp(op, "IF") == 0){
    //             for(int i = 0; i < quant; i++){
    //                 if(strcmp(showId,show[i].showId) == 0){
    //                     insertAtEnd(list, show[i]);
    //                     break;
    //                 }
    //             }
    //         }else if(strcmp(op, "I*") == 0){
    //             for(int i = 0; i < quant; i++){
    //                 if(strcmp(showId,show[i].showId) == 0){
    //                     insert(list, show[i], pos);
    //                     break;
    //                 }
    //             }
    //         }else if(strcmp(op, "RI") == 0){
    //             Show removedShow = removeAtBegin(list);
    //             if (strcmp(removedShow.showId, "NaN") != 0) {
    //                 printf("(R) %s\n",removedShow.title);
    //             }
    //         }else if(strcmp(op, "RF") == 0){
    //             Show removedShow = removeAtEnd(list);
    //             if (strcmp(removedShow.showId, "NaN") != 0) {
    //                 printf("(R) %s\n",removedShow.title);
    //             }
    //         }else if(strcmp(op, "R*") == 0){
    //                 Show removedShow = removeAt(list, pos);
    //                 if (strcmp(removedShow.showId, "NaN") != 0) {
    //                     printf("(R) %s\n",removedShow.title);
    //             }
    //         }
    // }

    //showList(list);
    // QuickSort
    int comparacoes = 0, movimentacoes = 0;
    double start = clock();
    if (list->head != NULL && list->tail != NULL) {
        quickSort(list->head, list->tail, &comparacoes, &movimentacoes);
    }
    double end = clock();
    double duracao = ((end - start) * 1000.0);
    arquivoLog(duracao, comparacoes, movimentacoes);

    //printf("=== Lista ordenada ===\n");
    showList(list);
}



