package pb.lib.serverExample;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;


//   ----- import mylib.*;


public class EchoServer extends Server  //todo: EXTENDS
{
    public EchoServer(int port) {
        // The superclass knows what to do with the port number, we
        // don't have to care about it
        super(port);
    }

    // This is called by the Server class when a connection
    // comes in.  "in" and "out" come from the incoming socket
    // connection
    public void handleConnection(Socket socket) {
        try {
            InputStream in = socket.getInputStream();
            OutputStream out = socket.getOutputStream();

            // just copy the input to the output
            while (true)
                out.write(in.read());

        } catch (IOException ie) {
            System.out.println(ie);
        }
    }

    protected void cleanUp() {
        System.out.println("Cleaning up");
    }

    static public void main(String[] args) throws Exception {
        // Grab the port number from the command-line
        int port = 1;

        // Have debugging info sent to standard error stream
        Server.setDebugStream(System.err);

        // Create the server, and it's up and running
        new EchoServer(port);
    }
}