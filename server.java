import java.io.*;
import java.net.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class server {
    private static final int PORT = 12345;
    private static final ConcurrentHashMap<Integer, PrintWriter> clients = new ConcurrentHashMap<>();
    private static final AtomicInteger nextClientId = new AtomicInteger(1);

    public static void main(String[] args) {
        System.out.println("Server starting...");

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server started. Listening on port " + PORT);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket);
                new ClientHandler(clientSocket).start();
            }
        } catch (IOException e) {
            System.err.println("Server error:");
            e.printStackTrace();
        }
    }

    private static class ClientHandler extends Thread {
        private final Socket clientSocket;
        private final int clientId;
        private PrintWriter out;

        public ClientHandler(Socket socket) {
            this.clientSocket = socket;
            this.clientId = nextClientId.getAndIncrement();
        }

        @Override
        public void run() {
            try (
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            ) {
                out = new PrintWriter(clientSocket.getOutputStream(), true);
                clients.put(clientId, out);

                broadcast("Client " + clientId + " has joined the chat.", clientId);

                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    if (inputLine.equalsIgnoreCase("quit")) {
                        break;
                    }
                    System.out.println("Received from client " + clientId + ": " + inputLine);
                    broadcast("Client " + clientId + ": " + inputLine, clientId);
                }
            } catch (IOException e) {
                System.out.println("Connection lost with client " + clientId);
            } finally {
                clients.remove(clientId);
                broadcast("Client " + clientId + " has left the chat.", clientId);
                try {
                    clientSocket.close();
                } catch (IOException e) {
                    System.err.println("Error closing client socket for client " + clientId);
                    e.printStackTrace();
                }
                System.out.println("Client disconnected: " + clientSocket);
            }
        }

        private void broadcast(String message, int senderId) {
            for (PrintWriter clientOut : clients.values()) {
                clientOut.println(message);
            }
        }
    }
}
