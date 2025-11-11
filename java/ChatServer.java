
import java.io.*;
import java.net.*;
import java.util.*;

public class ChatServer {

    public final static int PORT = 1337;

    public static void main(String[] args) throws IOException {
        List<Socket> clients = new ArrayList<>();

        ServerSocket server = new ServerSocket(PORT);
        System.out.println("In ascolto sulla porta " + PORT);

        // thread principale che accetta connessioni TCP
        new Thread(() -> {
            while (true) {
                try {
                    Socket sock = server.accept();
                    synchronized (clients) { clients.add(sock); }
                    // per ogni client che si connette faccio partire un thread
                    startClientThread(sock, clients);
                    System.out.println("Nuovo client: " + sock.getRemoteSocketAddress());
                } catch (IOException e) {
                    System.out.println("Errore accept: " + e.getMessage());
                }
            }
        }).start();
    }

    private static void startClientThread(Socket sock, List<Socket> clients) {
        new Thread(() -> {
            try (
                    BufferedReader in = new BufferedReader(
                            new InputStreamReader(sock.getInputStream()));
                    DataOutputStream out = new DataOutputStream(
                            sock.getOutputStream());
            ) {
                // chiedo al client il suo nome
                out.writeBytes("Come ti chiami?\n");
                String name = in.readLine();
                out.writeBytes("Ciao " + name + ", benvenuto nella chat!\n");

                // ogni volta che ricevo un messaggio da un client
                // lo inoltro a tutti gli altri client
                while (true) {
                    String msg = in.readLine();
                    if (msg == null) break; // connessione chiusa dal client
                    broadcastMessage(name + "> " + msg, sock, clients);
                }
            } catch (IOException | NoSuchElementException e) {
                System.out.println("Errore client: " + e.getMessage());
            } finally {
                System.out.println("Client uscito: " + sock.getRemoteSocketAddress());
                synchronized (clients) { clients.remove(sock); }
            }
        }).start();
    }

    private static void broadcastMessage(String msg, Socket sender, List<Socket> clients) throws IOException {
        synchronized (clients) {
            for (Socket receiver : clients) {
                if (!receiver.equals(sender)) {
                    DataOutputStream out = new DataOutputStream(receiver.getOutputStream());
                    out.writeBytes(msg + "\n");
                }
            }
        }
    }
}
