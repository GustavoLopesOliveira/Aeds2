#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdbool.h>
#include <ctype.h>

#define MAX_LINE 2048
#define MAX_SHOWS 10000

typedef struct {
    char* show_id;
    char* type;
    char* title;
    char* director;
    char** cast;
    int cast_size;
    char* country;
    char* date_added;
    int release_year;
    char* rating;
    char* duration;
    char** listed_in;
    int listed_in_size;
} Show;

Show* shows[MAX_SHOWS];
int shows_count = 0;

char* strdup_strip(const char* src) {
    while (*src == ' ' || *src == '"') src++;
    size_t len = strlen(src);
    while (len > 0 && (src[len - 1] == ' ' || src[len - 1] == '"')) len--;
    char* dst = (char*)malloc(len + 1);
    if (!dst) return NULL;
    strncpy(dst, src, len);
    dst[len] = '\0';
    return dst;
}

int compare_strings(const void* a, const void* b) {
    return strcasecmp(*(const char**)a, *(const char**)b);
}

char** split_and_sort(const char* str, int* count) {
    char* temp = strdup(str);
    if (!temp) return NULL;

    char* token = strtok(temp, ",");
    char** result = NULL;
    int size = 0;

    while (token != NULL) {
        char** new_result = (char**)realloc(result, sizeof(char*) * (size + 1));
        if (!new_result) {
            free(temp);
            for (int i = 0; i < size; i++) free(result[i]);
            free(result);
            return NULL;
        }
        result = new_result;

        result[size] = strdup_strip(token);
        if (!result[size]) {
            free(temp);
            for (int i = 0; i < size; i++) free(result[i]);
            free(result);
            return NULL;
        }
        size++;
        token = strtok(NULL, ",");
    }

    free(temp);

    // Ordenar o array
    if (size > 1) {
        qsort(result, size, sizeof(char*), compare_strings);
    }

    *count = size;
    return result;
}

Show* parse_show(char* line) {
    Show* s = (Show*)malloc(sizeof(Show));
    if (!s) return NULL;

    char* fields[12]; // 11 campos + 1 de segurança
    char* field = (char*)malloc(strlen(line) + 1);
    if (!field) {
        free(s);
        return NULL;
    }

    int i = 0, f_index = 0;
    bool in_quotes = false;

    for (int j = 0; line[j] != '\0'; j++) {
        char c = line[j];
        if (c == '"') {
            in_quotes = !in_quotes;
        } else if (c == ',' && !in_quotes) {
            field[f_index] = '\0';
            fields[i++] = strdup_strip(field);
            f_index = 0;
            if (i >= 11) break; // Limite de campos
        } else {
            field[f_index++] = c;
        }
    }

    // Último campo
    if (i < 11) {
        field[f_index] = '\0';
        fields[i++] = strdup_strip(field);
    }
    free(field);

    if (i != 11) {
        for (int k = 0; k < i; k++) free(fields[k]);
        free(s);
        return NULL;
    }

    s->show_id = fields[0];
    s->type = fields[1];
    s->title = fields[2];
    s->director = fields[3];
    s->cast = split_and_sort(fields[4], &s->cast_size);
    s->country = fields[5];
    s->date_added = strlen(fields[6]) > 0 ? fields[6] : strdup("NaN");
    s->release_year = strlen(fields[7]) > 0 ? atoi(fields[7]) : -1;
    s->rating = fields[8];
    s->duration = fields[9];
    s->listed_in = split_and_sort(fields[10], &s->listed_in_size);

    // Liberar campos usados para split
    free(fields[4]);
    free(fields[10]);

    return s;
}

void print_show(Show* s) {
    printf("=> %s ## %s ## %s ## %s ## [",
           s->show_id, s->title, s->type, s->director);

    for (int i = 0; i < s->cast_size; i++) {
        printf("%s%s", s->cast[i], (i < s->cast_size - 1) ? ", " : "");
    }

    printf("] ## %s ## %s ## %d ## %s ## %s ## [",
           s->country, s->date_added, s->release_year, s->rating, s->duration);

    for (int i = 0; i < s->listed_in_size; i++) {
        printf("%s%s", s->listed_in[i], (i < s->listed_in_size - 1) ? ", " : "");
    }

    printf("]\n");
}

Show* search_by_id(const char* id) {
    for (int i = 0; i < shows_count; i++) {
        if (shows[i] && strcmp(shows[i]->show_id, id) == 0) {
            return shows[i];
        }
    }
    return NULL;
}

void free_show(Show* s) {
    if (!s) return;
    free(s->show_id);
    free(s->type);
    free(s->title);
    free(s->director);
    for (int i = 0; i < s->cast_size; i++) free(s->cast[i]);
    free(s->cast);
    free(s->country);
    free(s->date_added);
    free(s->rating);
    free(s->duration);
    for (int i = 0; i < s->listed_in_size; i++) free(s->listed_in[i]);
    free(s->listed_in);
    free(s);
}

void start_shows(const char* path) {
    FILE* file = fopen(path, "r");
    if (!file) {
        perror("Erro ao abrir o arquivo");
        return;
    }

    char line[MAX_LINE];
    fgets(line, sizeof(line), file); // Pular cabeçalho

    while (fgets(line, sizeof(line), file) && shows_count < MAX_SHOWS) {
        line[strcspn(line, "\n")] = '\0';
        Show* s = parse_show(line);
        if (s) {
            shows[shows_count++] = s;
        }
    }

    fclose(file);
}

int main() {
    start_shows("/tmp/disneyplus.csv");

    char input[64];
    while (fgets(input, sizeof(input), stdin)) {
        input[strcspn(input, "\n")] = '\0';
        if (strcmp(input, "FIM") == 0) break;

        Show* s = search_by_id(input);
        if (s) {
            print_show(s);
        } else {
            printf("Show não encontrado!\n");
        }
    }

    // Liberar memória
    for (int i = 0; i < shows_count; i++) {
        free_show(shows[i]);
    }

    return 0;
}