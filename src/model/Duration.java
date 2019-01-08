package model;

/**
 * used to store the duration of a song
 * @author Zvi
 *
 */
public class Duration {
	public final byte minutes;
	public final byte seconds;


	public Duration(byte minutes, byte seconds) {
		this.minutes = minutes;
		this.seconds = seconds;
	}

	/**
	 * parse seconds to minutes and seconds
	 * @param seconds - the length in seconds
	 */
	public Duration(int seconds) {
		this.minutes = (byte) (seconds / 60);
		this.seconds = (byte) (seconds % 60);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		// parse minutes
		if (minutes >= 60) {
			sb.append(minutes / 60);
			sb.append(':');
			sb.append(minutes % 60);
		} else {
			sb.append(minutes);
			sb.append(':');
		}

		// parse seconds
		if (seconds == 0)
			sb.append('0');
		if (seconds < 10)
			sb.append('0');

		if (seconds != 0)
			sb.append(seconds);

		return sb.toString();
	}

}
