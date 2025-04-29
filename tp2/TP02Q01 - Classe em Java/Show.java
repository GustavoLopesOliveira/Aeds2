import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Show{

		private String show_id;
		private String type;
		private String title;
		private String director;
		private String[] cast;
		private String country;
		private String data_added;
		private int release_year;
		private String rating;
		private String duration;
		private String[] listed_in;

		//Construtores

		Show(){
			this.show_id = "";
			this.type = "";
			this.title = "";
			this.director = "";
			this.cast = null;
			this.country = "";
			this.data_added = "";
			this.release_year = -1;
			this.rating = "";
			this.duration = "";
			this.listed_in = null;
		}

		Show(String show_id, String type, String title, String director, String[] cast, String country,
			 String data_added, int release_year, String rating, String duration, String[] listed_in){
			this.show_id = show_id;
			this.type = type;
			this.title = title;
			this.director = director;
			this.cast = cast;
			this.country = country;
			this.data_added = data_added;
			this.release_year = release_year;
			this.rating = rating;
			this.duration = duration;
			this.listed_in = listed_in;
		}

		//Getters
		public String getShow_id() { return show_id; }
		public String getType() { return type; }
		public String getTitle() { return title; }
		public String getDirector() { return director; }
		public String[] getCast() { return cast; }
		public String getCountry() { return country; }
		public String getData_added() { return data_added; }
		public int getRelease_year() { return release_year; }
		public String getRating() { return rating; }
		public String getDuration() { return duration; }
		public String[] getListed_in() { return listed_in; }

		//Setters
		public void setShow_id(String show_id) { this.show_id = show_id; }
		public void setType(String type) { this.type = type; }
		public void setTitle(String title) { this.title = title; }
		public void setDirector(String director) { this.director = director; }
		public void setCast(String[] cast) { this.cast = cast; }
		public void setCountry(String country) { this.country = country; }
		public void setData_added(String data_added) { this.data_added = data_added; }
		public void setRelease_year(int release_year) { this.release_year = release_year; }
		public void setRating(String rating) { this.rating = rating; }
		public void setDuration(String duration) { this.duration = duration; }
		public void setListed_in(String[] listed_in) { this.listed_in = listed_in; }

		public Show clone(){
            return new Show(getShow_id(),
					getType(),
					getTitle(),
					getDirector(),
					getCast(),
					getCountry(),
					getData_added(),
					getRelease_year(),
					getRating(),
					getDuration(),
					getListed_in()
			);
		}

		//To String
		@Override
		public String toString(){
			String str = "=> "
					+ getShow_id() + " ## "
					+  getTitle() + " ## "
					+ getType() + " ## "
					+ getDirector() + " ## ";

			str +=  "[";
			String[] casts = getCast();

			for(int i = 0; i < casts.length; i++) {
				str += casts[i].trim();
				if(i < casts.length - 1) str += ", ";
			}
		 	str += "] ## ";

			str +=	 getCountry() + " ## "
					+ getData_added() + " ## "
					+ getRelease_year() + " ## "
					+ getRating() + " ## "
					+ getDuration() + " ## ";

			str +=  "[";

			String[] listeds = getListed_in();

			for(int i = 0; i < listeds.length; i++) {
				str += listeds[i];
				if(i < listeds.length - 1) str += ", ";
			}

			str +=	 "]";

			return str;
		}

		public static List<Show> loadFromCSV(String filename) {
			List<Show> shows = new ArrayList<>();

			try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
				// Pular cabeçalho
				br.readLine();

				String line;
				while ((line = br.readLine()) != null) {
					String[] data = parseCSVLine(line);

					// Tratar campos vazios ou nulos
					String director = data[3].isEmpty() ? "NaN" : data[3];
					String country = data[5].isEmpty() ? "NaN" : data[5];

					// Converter arrays (cast e listed_in)
					String[] cast = data[4].isEmpty() ? new String[]{"NaN"} : data[4].split(",");
					Arrays.sort(cast, String.CASE_INSENSITIVE_ORDER);
					String[] listedIn = data[10].isEmpty() ? new String[]{"NaN"}  : data[10].split(",");
					Arrays.sort(listedIn, String.CASE_INSENSITIVE_ORDER);



					// Criar objeto Show
					Show show = new Show(
							data[0],          // show_id
							data[1],          // type
							data[2],          // title
							director,         // director
							cast,            // cast
							country,          // country
							data[6],         // date_added
							Integer.parseInt(data[7]), // release_year
							data[8],         // rating
							data[9],         // duration
							listedIn        // listed_in
					);

					shows.add(show);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

			return shows;
		}

		private static String[] parseCSVLine(String line) {
			List<String> fields = new ArrayList<>();
			StringBuilder currentField = new StringBuilder();
			boolean inQuotes = false;

			for (char c : line.toCharArray()) {
				if (c == '"') {
					inQuotes = !inQuotes;
				} else if (c == ',' && !inQuotes) {
					fields.add(currentField.toString().trim());
					currentField = new StringBuilder();
				} else {
					currentField.append(c);
				}
			}
			fields.add(currentField.toString().trim());

			return fields.toArray(new String[0]);
		}

		public static void main(String[] args) {
			List<Show> shows = Show.loadFromCSV("/tmp/disneyplus.csv");

			HashMap<String,String> hash = new HashMap<>();

			for(Show show : shows) {
				hash.put(show.getShow_id(),show.toString());
			}

			Scanner input = new Scanner(System.in);

			while(input.hasNext()){
				String chave = input.nextLine();
				if(!chave.equals("FIM")) {
					System.out.println(hash.get(chave));
				}
			}


		}

	}
