package courseProject;

import java.time.LocalDate;
public class Product implements Comparable<Product> {
	private String key;
	private String title;
	private String artist;
	private double price;
	private int NumberOfSongs;
	private List<String> SongList;
	private LocalDate ReleaseDate;
	private String genre;


	public Product (String key, String title, String artist, double price,
					int NumberOfSongs, List<String> SongList, LocalDate ReleaseDate, String genre) {
		this.key = key;
		this.title = title;
		this.artist =artist;
		this.price = price;
		this.NumberOfSongs = NumberOfSongs;
		this.SongList = SongList;
		this.ReleaseDate = ReleaseDate;
		this.genre = genre;
	}

	public String getKey() {
		return key;
	}

	public String getTitle() {
		return title;
	}

	public String getArtist() {
		return artist;
	}

	public double getPrice() {
		return price;
	}

	public int getNumberOfSongs(){
		return NumberOfSongs;
	}

	public List<String> getSongList(){
		return SongList;
	}

	public LocalDate getReleaseDate() {
		return ReleaseDate;
	}

	public String getGenre() {
		return genre;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setArtist(String artist) {
		this.artist = artist;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public void setNumberOfSongs(int NumberOfSongs) {
		this.NumberOfSongs = NumberOfSongs;
	}

	public void setSongList(List<String> SongList) {
		this.SongList = SongList;
	}

	public void setLocalDate(LocalDate ReleaseDate) {
		this.ReleaseDate = ReleaseDate;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	@Override public String toString() {
		String result = "Album Title: " + title
				+ "\nArtist: " + artist
				+ "\nPrice: " + price
				+ "\nNumber of Songs: " + NumberOfSongs
				+ "\nSong List: " + SongList.toString()
				+ "\nReleaseDate: " + ReleaseDate
				+ "\nGenre: " + genre + "\n";
		return result;
	}

	public int compareTo(Product otherProduct) {
		if(this.key.equals(otherProduct.key)) {
			return 0;
		}else if(this.key.compareTo(otherProduct.key) < 0) {
			return -1;
		}else {
			return 1;
		}
	}
}
