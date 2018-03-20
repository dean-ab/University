#include <stdlib.h>
#include "../include/connectionHandler.h"
#include "../include/Tasks.h"

/**
* This code assumes that the server replies the exact text the client sent it (as opposed to the practical session example)
*/
int main (int argc, char *argv[]) {
    if (argc < 3) {
        std::cerr << "Usage: " << argv[0] << " host port" << std::endl << std::endl;
        return -1;
    }
    std::string host = argv[1];
    short port = atoi(argv[2]);

    // Create new connection handler
    ConnectionHandler connectionHandler(host, port);
    if (!connectionHandler.connect()) {
        std::cerr << "Cannot connect to " << host << ":" << port << std::endl;
        return 1;
    }
    // Create a new Read Task (From user's screen) and run it
    Read read1(1, connectionHandler);
    boost::thread th1(read1);

    // Create a new Write Task (to the screen), with an access to Read Task
    // and run it
    Write write1(1, connectionHandler, th1);
    boost::thread th2(write1);

    // Join two tasks for holding main from ending client
    th1.join();
    th2.join();
    



    return 0;
}
