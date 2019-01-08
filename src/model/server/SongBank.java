package model.server;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Used to simulate the server's responses
 * @author Zvi
 *
 */
public class SongBank {
	private static List<String> songBank;
	private static Random rand;

	public static String getRandomSong() {

		if (songBank == null)
			initialize();

		int i = rand.nextInt(songBank.size());
		return songBank.get(i) + getDur();
	}

	private static void initialize() {
		rand = new Random();
		songBank = new ArrayList<>();
		songBank.add("Tubthumping;Chumbawamba;");
		songBank.add("Jump;Kris Kross;");
		songBank.add("Celebration;Kool & The Gang;");
		//songBank.add("Really really long song name with many chars;Short Artist;");
	}

	private static double getDur() {
		return rand.nextInt(700) + rand.nextDouble();
	}
}
