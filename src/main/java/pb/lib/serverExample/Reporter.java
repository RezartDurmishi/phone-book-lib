package pb.lib.serverExample;// -------- package mylib;

class Reporter implements Runnable
{
    // the pb.lib.test.serverExample.Server we are reporting on
    private Server server;

    // our background thread
    private Thread thread;

    // set this to true to tell the thread to stop accepting
    // connections
    private boolean mustQuit = false;

    Reporter( Server server ) {
        this.server = server;

        // create a background thread
        thread = new Thread( this );
        thread.start();
    }

    public void run() {
        while (!mustQuit) {
            // do the reporting
            Server.debug( "server has had "+server.numConnections+" connections" );

            // then pause a while
            try {
                Thread.sleep( 5000 );
            } catch( InterruptedException ie ) {}
        }
    }

    // tell the background thread to quit
    public void close() {
        mustQuit = true;
    }
}