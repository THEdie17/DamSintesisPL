package reader_app;

import javafx.scene.control.Hyperlink;
import javafx.scene.image.ImageView;

/**
 * Book class used for storing the user selected book's various metadata
 * 
 * @author Pol Renalias
 *
 */
public class Book {
	/**
	 * Book's thumb nail
	 */
	private ImageView thumbnail;
	/**
	 * Book's metadata
	 */
	private String name, author, genre, year;
	/**
	 * Book link property to set the reader starting event
	 */
	private Hyperlink link;
	/**
	 * Book's file path
	 */
	private String path;
	/**
	 * Thumb nail's file path
	 */
	private String thumbnailPath;

	/**
	 * Default parameterized constructor
	 * @param t Thumb nail
	 * @param n Name
	 * @param a Author
	 * @param g Genre
	 * @param y Year
	 */
	public Book(ImageView t, String n, String a, String g, String y, String p) {
		thumbnail = t;
		name = n;
		author = a;
		genre = g;
		year = y;
		link = new Hyperlink("Read now");
		path = p;
	}

	/**
	 * @return the thumb nail
	 */
	public ImageView getThumbnail() {
		return thumbnail;
	}

	/**
	 * @param thumbnail the thumb nail to set
	 */
	public void setThumbnail(ImageView thumbnail) {
		this.thumbnail = thumbnail;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the author
	 */
	public String getAuthor() {
		return author;
	}

	/**
	 * @param author the author to set
	 */
	public void setAuthor(String author) {
		this.author = author;
	}

	/**
	 * @return the genre
	 */
	public String getGenre() {
		return genre;
	}

	/**
	 * @param genre the genre to set
	 */
	public void setGenre(String genre) {
		this.genre = genre;
	}

	/**
	 * @return the year
	 */
	public String getYear() {
		return year;
	}

	/**
	 * @param year the year to set
	 */
	public void setYear(String year) {
		this.year = year;
	}

	/**
	 * @return the link
	 */
	public Hyperlink getLink() {
		return link;
	}

	/**
	 * 
	 * @param message
	 */
	public void changeLink(String message) {
		link = new Hyperlink(message);
	}

	/**
	 * @return the path
	 */
	public String getPath() {
		return path;
	}

	/**
	 * @param path the path to set
	 */
	public void setPath(String path) {
		this.path = path;
	}

	/**
	 * @return the thumbnailPath
	 */
	public String getThumbnailPath() {
		return thumbnailPath;
	}

	/**
	 * @param thumbnailPath the thumbnailPath to set
	 */
	public void setThumbnailPath(String thumbnailPath) {
		this.thumbnailPath = thumbnailPath;
	}
}