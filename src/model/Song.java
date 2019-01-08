package model;

/**
 * The song struct.
 * 
 * @author Zvi
 *
 */
public class Song {
	public final String name;
	public final String artist;
	public final Duration duration;

	public Song(String name, String artist, double seconds) {
		this.name = name;
		this.artist = artist;
		duration = new Duration((int) seconds);
	}

}
