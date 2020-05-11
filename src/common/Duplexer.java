package common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Helper class from studio to simplify sending and receiving messages to and from the server
 *
 * @author Josh Greco
 */

public class Duplexer implements AutoCloseable {
    private Socket socket;
    private BufferedReader reader;
    private PrintWriter writer;

    /**
     * Creates I/O connections for each socket
     * @param socket socket that was created
     * @throws IOException
     */
    public Duplexer(Socket socket) throws IOException {
        this.socket = socket;
        this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.writer = new PrintWriter(socket.getOutputStream());
    }

    /**
     * Sends and flushes the message to the server
     * @param message message to be sent
     */
    public void send(String message){
        writer.println(message);
        writer.flush();
    }

    /**
     * gets message from the server
     * @return message from server
     * @throws IOException
     */
    public String receive() throws IOException {
        return reader.readLine();
    }

    /**
     * Closes the connection of server and client
     * @throws Exception
     */
    public void close() throws Exception {
        socket.close();
    }
}
