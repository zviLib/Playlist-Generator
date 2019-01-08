package model.server;

import java.util.List;

import model.Song;

public interface Client {
	/**
	 * send message to the server
	 * @param m - the message enum
	 * @param id - related info
	 */
	public void sendMessage(Message m, String id);

	/**
	 * @return list of returned input from server
	 */
	public List<String> getInfo();

	/**
	 * @return list of songs received from server
	 */
	public List<Song> getSongs();
}
