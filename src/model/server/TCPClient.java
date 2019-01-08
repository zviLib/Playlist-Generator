package model.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import model.Song;

/**
 * Used to communicate with the server
 * @author Zvi
 *
 */
public class TCPClient implements Client {

	private Socket clientSocket;
	private DataOutputStream outToServer;
	private DataInputStream fromServer;

	/**
	 * create a connection to the server
	 */
	public TCPClient() {

		try {
			clientSocket = new Socket("localhost", 6789);
			outToServer = new DataOutputStream(clientSocket.getOutputStream());
			fromServer = new DataInputStream(clientSocket.getInputStream());
		} catch (IOException e) {
			System.out.println("Can't connect to server");
			clientSocket = null;
		}
	}

	/**
	 * Close the connection.
	 */
	public void close() {
		try {
			outToServer.close();
			fromServer.close();
			clientSocket.close();
		} catch (Exception e) {
		}
	}

	@Override
	public void sendMessage(Message m, String id) {

		try {
			outToServer.writeUTF(String.format("%s %s", m.name(), id));
		} catch (Exception e) {
			System.out.println("Can't send to Server");
		}
	}

	@Override
	public List<String> getInfo() {

		ArrayList<String> info = new ArrayList<>();

		/// read number of records
		try {
			int size = fromServer.readInt();
			// read records
			for (int i = 0; i < size; i++) {
				info.add(fromServer.readUTF());
			}

		} catch (Exception e) {
			System.out.println("Problem reading from server: " + e.getClass());
		}

		return info;
	}

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