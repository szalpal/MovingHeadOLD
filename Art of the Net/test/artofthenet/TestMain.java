package test.artofthenet;

public class TestMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		new Thread(new TestThread()).start();
//		new Thread(new ArtPollReplyThread()).start();
	}

}
