import java.util.*;
import java.io.*;
import java.text.*;

public class Show {
    private String showId;
    private String type;
    private String title;
    private String director;
    private String[] cast;
    private String country;
    private Date dateAdded;
    private int releaseYear;
    private String rating;
    private String duration;
    private String[] listedIn;

    public String getShowId() {
        return showId;
    }

    public void setShowId(String showId) {
        this.showId = showId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String[] getCast() {
        return cast;
    }

    public void setCast(String[] cast) {
        this.cast = cast;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Date getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(Date dateAdded) {
        this.dateAdded = dateAdded;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String[] getListedIn() {
        return listedIn;
    }

    public void setListedIn(String[] listedIn) {
        this.listedIn = listedIn;
    }

    public Show() {
        this.showId = "NaN";
        this.type = "NaN";
        this.title = "NaN";
        this.director = "NaN";
        this.cast = new String[]{"NaN"};
        this.country = "NaN";
        this.dateAdded = null;
        this.releaseYear = -1;
        this.rating = "NaN";
        this.duration = "NaN";
        this.listedIn = new String[]{"NaN"};
    }

    public Show(String showId, String type, String title, String director, String[] cast, String country, String dateAdded, int releaseYear, String rating, String duration, String[] listedIn) {
        this.showId = (showId != null && !showId.isEmpty()) ? showId : "NaN";
        this.type = (type != null && !type.isEmpty()) ? type : "NaN";
        this.title = (title != null && !title.isEmpty()) ? title : "NaN";
        this.director = (director != null && !director.isEmpty()) ? director : "NaN";
        this.cast = (cast != null && cast.length >= 0) ? cast : new String[]{"NaN"};
        this.country = (country != null && country.isEmpty()) ? country : "NaN";
        SimpleDateFormat data = new SimpleDateFormat("MMMM dd, yyyy");
        if (dateAdded != null && !dateAdded.isEmpty()) {
            try {
                this.dateAdded = data.parse(dateAdded);
            } catch (ParseException e) {
                System.out.println("Erro ao adicionar a data");
                this.dateAdded = null;
            }
        } else {
            this.dateAdded = null;
        }
        this.releaseYear = (releaseYear > 0) ? releaseYear : -1;
        this.duration = (duration != null && !duration.isEmpty()) ? duration : "NaN";
        this.listedIn = (listedIn != null && listedIn.length >= 0) ? listedIn : new String[]{"NaN"};
    }

    public void imprimir() {
        SimpleDateFormat data = new SimpleDateFormat("MMMM d, yyyy");
        System.out.print("=> " + getShowId());
        System.out.print(" ## " + getTitle());
        System.out.print(" ## " + getType());
        System.out.print(" ## " + getDirector());
        System.out.print(" ## " + Arrays.toString(getCast()));
        System.out.print(" ## " + getCountry());
        System.out.print(" ## " + (getDateAdded() != null ? data.format(getDateAdded()) : "NaN"));
        System.out.print(" ## " + (getReleaseYear() != -1 ? getReleaseYear() : "NaN"));
        System.out.print(" ## " + getRating());
        System.out.print(" ## " + getDuration());
        System.out.println(" ## " + Arrays.toString(getListedIn()) + " ##");
    }

    public static ArrayList<Show> ler() {
        ArrayList<Show> listaShow = new ArrayList<>();
        try (BufferedReader r = new BufferedReader(new FileReader("/tmp/disneyplus.csv"))) {
            String linha = "";
            r.readLine();
            while ((linha = r.readLine()) != null) {
                Show show = new Show();
                show.atribuir(linha);
                listaShow.add(show);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return listaShow;
    }

    public void atribuir(String linha) {
        String[] str = new String[11];
        Arrays.fill(str, "");
        str = separarLinha(linha);
        SimpleDateFormat formato = new SimpleDateFormat("MMMM dd, yyyy", Locale.ENGLISH);

        setShowId((str[0] != null) ? str[0] : "NaN");
        setType((str[1] != null) ? str[1] : "NaN");
        setTitle((str[2] != null) ? str[2] : "NaN");
        setDirector((str[3] != null) ? str[3] : "NaN");
        if (str[4] != null) {
            setCast(ordenaArray(str[4]));
        } else {
            setCast(new String[]{"NaN"});
        }
        setCountry((str[5] != null) ? str[5] : "NaN");
        try {
            if (str[6] != null && !str[6].isEmpty()) {
                Date data = formato.parse(str[6]);
                setDateAdded(data);
            } else if (str[6] == null) {
                Date data = formato.parse("March 1, 1900");
                setDateAdded(data);
            }
        } catch (Exception e) {
            System.out.println("Erro ao adicionar a data: " + str[6]);
            this.dateAdded = null;
        }
        setReleaseYear((str[7] != null) ? Integer.parseInt(str[7]) : -1);
        setRating((str[8] != null) ? str[8] : "NaN");
        setDuration((str[9] != null) ? str[9] : "NaN");
        if (str[10] != null) {
            setListedIn(ordenaArray(str[10]));
        } else {
            setListedIn(new String[]{"NaN"});
        }
    }

    public static String[] ordenaArray(String str) {
        int temp = 0, tam = 0;
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == ',') {
                tam++;
            }
        }

        String[] array = new String[tam + 1];
        Arrays.fill(array, "");

        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) != ',') {
                array[temp] += str.charAt(i);
            } else if (str.charAt(i) == ',') {
                i++; // para retirar o espaço extra
                temp++;
            }

        }

        for (int i = array.length - 1; i > 0; i--) {
            for (int j = 0; j < i; j++) {
                if (array[j].compareTo(array[j + 1]) > 0) {
                    String aux = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = aux;
                }
            }
        }
        return array;
    }

    public static String[] separarLinha(String linha) {
        String[] str = new String[11];
        Arrays.fill(str, "");
        int aux = 0, i = 0;
        while (i < linha.length() && aux != 11) {
            char letra = linha.charAt(i);
            char letra2 = 'c';
            if (i + 1 < linha.length()) {
                letra2 = linha.charAt(i + 1);
            }
            if (letra == ',' && letra2 != ' ') {
                if (str[aux] == "") {
                    str[aux] = null;
                }
                aux++;
            } else {
                if (letra != '"') {
                    str[aux] += letra;
                } else {
                    i++;
                    letra = linha.charAt(i);
                    while (i < linha.length() && linha.charAt(i) != '"') {
                        str[aux] += letra;
                        i++;
                        letra = linha.charAt(i);
                    }
                }
            }
            i++;
        }
        return str;
    }

    public Show clone() {
        Show copia = new Show();
        copia.setShowId(this.showId);
        copia.setType(this.type);
        copia.setTitle(this.title);
        copia.setDirector(this.director);
        copia.setCast(Arrays.copyOf(this.cast, this.cast.length));
        copia.setCountry(this.country);
        copia.setDateAdded(this.dateAdded != null ? new Date(this.dateAdded.getTime()) : null);
        copia.setReleaseYear(this.releaseYear);
        copia.setRating(this.rating);
        copia.setDuration(this.duration);
        copia.setListedIn(Arrays.copyOf(this.listedIn, this.listedIn.length));
        return copia;
    }
    
    public static int getMaior(Show[] array, int tam){
        int maior = array[0].getReleaseYear();
        for(int i = 1; i < tam; i++){
            if(array[i].getReleaseYear() > maior){
                maior = array[i].getReleaseYear();
            }
        }
        return maior;
    }
    
    public static int getMaxString(Show[] array, int tam){
        int max = 0;
    
        for (int i = 0; i < tam; i++) {
            int len = array[i].getTitle().length();
            if (len > max) {
                max = len;
            }
        }
        return max;
    }
    
    public static void countingSort(Show[] array, int tam, int[] comparacoes, int[] movimentacoes){
        int maior = getMaior(array, tam);
        int x = 0, p = 0, m = 0, ano;
        Show[] ordenado = new Show[tam];
        int[]  count = new int[maior+1];
        
        for(int i = 0; i < maior+1; i++){
            count[i] = 0;
        }
        for(int i = 0; i < tam; i++){
            count[array[i].getReleaseYear()]++;
        }
        for(int i = 1; i < count.length; i++){
            count[i] += count[i-1];
        }
        for(int i = tam-1; i >= 0; i--){
            ordenado[count[array[i].getReleaseYear()]-1] = array[i];
            count[array[i].getReleaseYear()]--;
        }
        for(int i = 0; i < tam; i++){
            array[i] = ordenado[i];
        }
        while (x != tam) {
            ano = array[x].getReleaseYear();
    
            while (x < tam && array[x].getReleaseYear() == ano) {
                x++;
            }
    
            int listaTam = x - p;
            Show[] lista = new Show[listaTam];
            for (int i = 0; i < listaTam; i++) {
                lista[i] = array[p + i];
            }
    
            int max = getMaxString(lista, listaTam);
            for (int pos = max - 1; pos >= 0; pos--) {
                countingsortString(lista, listaTam, pos, comparacoes, movimentacoes);
            }
    
            for (int i = 0; i < listaTam; i++) {
                array[p + i] = lista[i];
            }
            p = x;
        }
    }
    
    public static void countingsortString(Show[] array, int n, int esp, int comparacoes[], int movimentacoes[]){
        Show[] ordenado = new Show[n];
        int[]  count = new int[256];
        
        for (int i = 0; i < 256; i++) {
            count[i] = 0;
        }
        for(int i = 0; i < n; i++){
            int digit = (array[i].getTitle().length() > esp) ? Character.toLowerCase(array[i].getTitle().charAt(esp)) : 0;
            digit = (digit >= 0 && digit < 256) ? digit : 0;
            comparacoes[0]++;
            count[digit]++;
        }
        for(int i = 1; i < 256; i++){
            count[i] += count[i-1];
        }
        for(int i = n-1; i >= 0; i--){
            int digit = (array[i].getTitle().length() > esp) ? Character.toLowerCase(array[i].getTitle().charAt(esp)) : 0;
            digit = (digit >= 0 && digit < 256) ? digit : 0;
            comparacoes[0]++;
            ordenado[count[digit]-1] = array[i];
            count[digit]--;
            movimentacoes[0]++;
        }
        for(int i = 0; i < n; i++){
            array[i] = ordenado[i];
            movimentacoes[0]++;
        }
    }
    
    public static void arquivoLog(double duracao, int[] comparacoes, int[] movimentacoes){
        String matricula = "869118";
        try {
            PrintWriter w = new PrintWriter(matricula + "_countingsort.txt");
            w.printf("%s\t%d\t%d\t%fms", matricula, comparacoes[0], movimentacoes[0], duracao);
            w.close();
        } catch (IOException e) {
            System.err.println("Erro para escrever no arquivo de log: " + e.getMessage());
        }
    }
 
    public static void main(String[] args) {
        long inicioTempo = System.nanoTime();
        int[] comparacoes = new int[1];
        int[] movimentacoes = new int[1];
        int j = 0;
        Scanner input = new Scanner(System.in);
        ArrayList<Show> listaShow = ler();
        String id = "";
        Show[] lista = new Show[2000];
        
        id = input.nextLine();
        while (!id.equals("FIM")) {
            for (int i = 0; i < listaShow.size(); i++) {
                if (listaShow.get(i).getShowId().equals(id)) {
                    lista[j] = listaShow.get(i);
                    j++;
                }
            }
            id = input.nextLine();
        }
        countingSort(lista, j, comparacoes, movimentacoes);
        for(int i = 0; i < j; i++){
            lista[i].imprimir();
        }
        
        long fimTempo = System.nanoTime();
        double duracao = (fimTempo - inicioTempo) / 1_000_000.0; 
        arquivoLog(duracao, comparacoes, movimentacoes);
        input.close();
    }
}