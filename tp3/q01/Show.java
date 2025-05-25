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

    public String getShowId() { return showId; }
    public void setShowId(String showId) { this.showId = showId; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDirector() { return director; }
    public void setDirector(String director) { this.director = director; }
    public String[] getCast() { return cast; }
    public void setCast(String[] cast) { this.cast = cast; }
    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }
    public Date getDateAdded() { return dateAdded; }
    public void setDateAdded(Date dateAdded) { this.dateAdded = dateAdded; }
    public int getReleaseYear() { return releaseYear; }
    public void setReleaseYear(int releaseYear) { this.releaseYear = releaseYear; }
    public String getRating() { return rating; }
    public void setRating(String rating) { this.rating = rating; }
    public String getDuration() { return duration; }
    public void setDuration(String duration) { this.duration = duration; }
    public String[] getListedIn() { return listedIn; }
    public void setListedIn(String[] listedIn) { this.listedIn = listedIn; }

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
                i++; 
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

    static class LinkedList{
        class Node{
            Show show;
            Node next;
            public Node(Show show){
                this.show = show;
                this.next = null;
            }
        }

        Node head,tail;

        public LinkedList(){
            head = null;
            tail = null;
        }
        
        public void addFront(Show show){
            Node newNode = new Node(show);
            if(head == null){
                head = newNode;
                tail = newNode;
            }else{
                newNode.next = head;
                head = newNode;
            }
        }

        public void addBack(Show show){
            Node newNode = new Node(show);
            if(head == null){
                head = newNode;
                tail = newNode;
            }else{
                tail.next = newNode;
                tail = newNode;
            }
        }

        public void addMiddle(Show show, int pos){
            Node newNode = new Node(show);
            if(head == null){
                head = newNode;
                tail = newNode;
            }else{
                Node current = head;
                for(int i = 0; i < pos - 1 && current != null; i++){
                    current = current.next;
                }
                if(current != null){
                    newNode.next = current.next;
                    current.next = newNode;
                }else{
                    tail.next = newNode;
                    tail = newNode;
                }
            }
        }

        public Show removeFront(){
            if(head == null){
                return null;
            }else{
                Show show = head.show;
                head = head.next;
                return show;
            }
        }
        public Show removeBack(){
            if(head == null){
                return null;
            }else{
                Show show = tail.show;
                if(head == tail){
                    head = null;
                    tail = null;
                }else{
                    Node current = head;
                    while(current.next != tail){
                        current = current.next;
                    }
                    current.next = null;
                    tail = current;
                }
                return show;
            }
        }

        public Show removeMiddle(int pos){
            if(head == null){
                return null;
            }else{
                Node current = head;
                for(int i = 0; i < pos - 1 && current != null; i++){
                    current = current.next;
                }
                if(current != null && current.next != null){
                    Show show = current.next.show;
                    current.next = current.next.next;
                    return show;
                }else{
                    return null;
                }
            }
        }

        public void showList(){
            Node i = head;
            while(i != null){
                i.show.imprimir();
                i = i.next;
            }
        }
    }
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        ArrayList<Show> listaShow = ler();
        LinkedList lista = new LinkedList();
        String id = "";
        id = input.nextLine();
        while (!id.equals("FIM")) {
            for (int i = 0; i < listaShow.size(); i++) {
                if (listaShow.get(i).getShowId().equals(id)) {
                    lista.addBack(listaShow.get(i));
                }
            }
            id = input.nextLine();
        }
        
        int cont = input.nextInt();
        input.nextLine();

        
        for(int i = 0; i < cont; i++){
            String entrada = input.nextLine();
            String[] str = entrada.split(" ");
            String comando = str[0];
            
            if(comando.equals("II")){
                String idShow = str[1];
                for(int j = 0; j < listaShow.size(); j++){
                    if(listaShow.get(j).getShowId().equals(idShow)){
                        lista.addFront(listaShow.get(j));
                    }
                }
            }else if(comando.equals("IF")){
                String idShow = str[1];
                for(int j = 0; j < listaShow.size(); j++){
                    if(listaShow.get(j).getShowId().equals(idShow)){
                        lista.addBack(listaShow.get(j));
                    }
                }
            }else if(comando.equals("I*")){
                String posicao = str[1];
                String idShow = str[2];
                int pos = Integer.parseInt(posicao);

                for(int j = 0; j < listaShow.size(); j++){
                    if(listaShow.get(j).getShowId().equals(idShow)){
                        lista.addMiddle(listaShow.get(j), pos);
                    }
                }
                
            }else if(comando.equals("RI")){
                Show show = lista.removeFront();
                System.out.println("(R) " + show.getTitle());
            }else if(comando.equals("RF")){
                Show show = lista.removeBack();
                System.out.println("(R) " + show.getTitle());
            }else if(comando.equals("R*")){
                String posicao = str[1];
                int pos = Integer.parseInt(posicao);
                Show show = lista.removeMiddle(pos);
                System.out.println("(R) " + show.getTitle());
            }

        }

        lista.showList();
        input.close();

    }
}