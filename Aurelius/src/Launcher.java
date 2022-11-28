import java.io.IOException;

import com.ib.client.EReader;

public class Launcher {

	public static void main(String args[]) {
		
		Controller controller = new Controller();
		
		controller.eClientSocket.eConnect("127.0.0.1", 4002, 0);
		
		final EReader reader = new EReader(controller.eClientSocket, controller.eClientSocket.getSignal());
		
		reader.start();
		
		new Thread(() -> {
			while (true) {
				controller.eClientSocket.getSignal().waitForSignal();
				try {
					reader.processMsgs();
				}
				catch (IOException e) {
					System.out.println(e);
				}
			}
		}).start();
		
	}
	
}
