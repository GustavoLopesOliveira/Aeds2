import random
import time
import matplotlib.pyplot as plt
from copy import deepcopy

# Algoritmos de ordenação
def bubble_sort(arr, comparacoes, trocas):
    n = len(arr)
    for i in range(n-1):
        for j in range(n-i-1):
            comparacoes[0] += 1
            if arr[j] > arr[j+1]:
                arr[j], arr[j+1] = arr[j+1], arr[j]
                trocas[0] += 1

def selection_sort(arr, comparacoes, trocas):
    n = len(arr)
    for i in range(n):
        menor = i
        for j in range(i+1, n):
            comparacoes[0] += 1
            if arr[j] < arr[menor]:
                menor = j
        if menor != i:
            arr[i], arr[menor] = arr[menor], arr[i]
            trocas[0] += 1

def insertion_sort(arr, comparacoes, trocas):
    n = len(arr)
    for i in range(1, n):
        key = arr[i]
        j = i-1
        while j >= 0:
            comparacoes[0] += 1
            if arr[j] > key:
                arr[j+1] = arr[j]
                trocas[0] += 1
                j -= 1
            else:
                break
        arr[j+1] = key
        if j+1 != i:
            trocas[0] += 1

def partition(arr, low, high, comparacoes, trocas):
    pivot = arr[high]
    i = low - 1
    for j in range(low, high):
        comparacoes[0] += 1
        if arr[j] < pivot:
            i += 1
            if i != j:
                arr[i], arr[j] = arr[j], arr[i]
                trocas[0] += 1
    if i+1 != high:
        arr[i+1], arr[high] = arr[high], arr[i+1]
        trocas[0] += 1
    return i+1

def quick_sort(arr, comparacoes, trocas, low=0, high=None):
    if high is None:
        high = len(arr) - 1
    if low < high:
        pi = partition(arr, low, high, comparacoes, trocas)
        quick_sort(arr, comparacoes, trocas, low, pi-1)
        quick_sort(arr, comparacoes, trocas, pi+1, high)


def testar_algoritmo(algoritmo, dados):
    dados_copia = deepcopy(dados)
    comparacoes = [0]
    trocas = [0]
    
    inicio = time.time()
    algoritmo(dados_copia, comparacoes, trocas)
    fim = time.time()

    return {
        'tempo': (fim - inicio) * 1000,
        'comparacoes': comparacoes[0],
        'trocas': trocas[0]
    }


TAMANHOS = [100, 1000, 10000, 100000]  
REPETICOES = 5
ALGORITMOS = {
    'Bubble Sort': bubble_sort,
    'Selection Sort': selection_sort,
    'Insertion Sort': insertion_sort,
    'QuickSort': quick_sort
}

#  testes
resultados = {nome: {tam: [] for tam in TAMANHOS} for nome in ALGORITMOS}

for tamanho in TAMANHOS:
    print(f"\nTestando com vetor de tamanho {tamanho}...")
    
    for _ in range(REPETICOES):
        dados = [random.randint(0, 100000) for _ in range(tamanho)]
        
        for nome, algoritmo in ALGORITMOS.items():
                res = testar_algoritmo(algoritmo, dados)
                resultados[nome][tamanho].append(res)
                print(f"{nome:15} | Tempo: {res['tempo']:8.2f}ms | Comparações: {res['comparacoes']:10} | Trocas: {res['trocas']:8}")
           

#  médias
medias = {nome: {} for nome in ALGORITMOS}
for nome in ALGORITMOS:
    for tamanho in TAMANHOS:
        if resultados[nome][tamanho]:
            medias[nome][tamanho] = {
                'tempo': sum(r['tempo'] for r in resultados[nome][tamanho]) / REPETICOES,
                'comparacoes': sum(r['comparacoes'] for r in resultados[nome][tamanho]) // REPETICOES,
                'trocas': sum(r['trocas'] for r in resultados[nome][tamanho]) // REPETICOES
            }

# Gráficos
def plotar_graficos(metrica, titulo, arquivo):
    plt.figure(figsize=(12, 6))
    for nome in ALGORITMOS:
        x = []
        y = []
        for tamanho in TAMANHOS:
            if tamanho in medias[nome]:
                x.append(tamanho)
                y.append(medias[nome][tamanho][metrica])
        if x and y:
            plt.plot(x, y, 'o-', label=nome)
    
    plt.xscale('log')
    plt.yscale('log')
    plt.title(titulo)
    plt.xlabel('Tamanho do vetor (log)')
    plt.ylabel(metrica.capitalize() + ' (log)')
    plt.xticks(TAMANHOS, labels=[str(t) for t in TAMANHOS])
    plt.grid(True, which="both", ls="--")
    plt.legend()
    plt.savefig(arquivo)
    plt.close()

plotar_graficos('tempo', 'Tempo de execução por algoritmo', 'tempo_execucao.png')
plotar_graficos('comparacoes', 'Número de comparações por algoritmo', 'comparacoes.png')
plotar_graficos('trocas', 'Número de trocas por algoritmo', 'trocas.png')

# Exibição das médias em tabela
print("\nResultados Médios:")
print(f"{'Algoritmo':<15} | {'Tamanho':<8} | {'Tempo (ms)':<10} | {'Comparações':<12} | {'Trocas':<8}")
print("-"*70)
for nome in ALGORITMOS:
    for tamanho in TAMANHOS:
        if tamanho in medias[nome]:
            m = medias[nome][tamanho]
            print(f"{nome:<15} | {tamanho:<8} | {m['tempo']:10.2f} | {m['comparacoes']:<12} | {m['trocas']:<8}")