import java.io.*;
import java.net.*;

public class client {
    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 12345;

    public static void main(String[] args) {
        try (
            Socket socket = new Socket(SERVER_HOST, SERVER_PORT);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in))
        ) {
            System.out.println("Connected to server.");
            System.out.println("Type your message or 'quit' to exit.");

            // Thread to listen for server messages
            new Thread(() -> {
                try {
                    String serverResponse;
                    while ((serverResponse = in.readLine()) != null) {
                        System.out.println(serverResponse);
                    }
                } catch (IOException e) {
                    System.out.println("Connection to server lost.");
                }
            }).start();

            // Main thread to send user input
            String userInput;
            while ((userInput = stdin.readLine()) != null) {
                out.println(userInput);
                if (userInput.equalsIgnoreCase("quit")) {
                    break;
                }
            }
        } catch (IOException e) {
            System.err.println("Unable to connect to server.");
            e.printStackTrace();
        }
    }
}
