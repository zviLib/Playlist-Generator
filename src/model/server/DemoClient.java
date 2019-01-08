package model.server;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import model.Song;

/**
 * Used for testing without an actual server.
 * @author Zvi
 *
 */
public class DemoClient implements Client {

	private List<String> simulatedInput;

	public DemoClient() {
	}

	@Override
	public void sendMessage(Message m, String id) {
		switch (m) {
		case categorieId:
		case refresh:
		case custom:
			simulatedInput = new ArrayList<>();
			for (int i = 0; i < 10; i++) {
				simulatedInput.add(SongBank.getRandomSong());
			}
			break;
		case songId:
			simulatedInput = Arrays.asList("Artist Similarity", "Similar Dancabilty", "Genre + Dancabilty",
					"Similar Hotness + Duration");
			Collections.shuffle(simulatedInput);
			break;
		case popular:
		case explore:
			simulatedInput = Arrays.asList("Jazz", "Alternative rock", "Song Loudness", "Hotness + Duration");
			Collections.shuffle(simulatedInput);
			break;
		default:
			simulatedInput = new ArrayList<>();
		}
	}

	@Override
	public List<String> getInfo() {
		return simulatedInput;
	}

	@Override
	public List<Song> getSongs() {
		// read strings from server
		List<String> list = getInfo();
		
		// parse songs
		List<Song> songList = new ArrayList<>();
		String[] split;
		for (String s : list) {
			split = s.split(";");
			try {
				songList.add(new Song(split[0], split[1], Double.parseDouble(split[2])));
			} catch (Exception err) {
				System.out.println("Error parsing song:" + s);
			}
		}

		return songList;
	}

}
