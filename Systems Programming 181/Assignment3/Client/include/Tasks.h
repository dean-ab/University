//
// Created by Shay Ben-simon on 07/01/2018.
//

#ifndef CLIENT_TASKS_H
#define CLIENT_TASKS_H
#include <boost/thread.hpp>
#include <iostream>
#include "../include/connectionHandler.h"

class Write {
    // This  class writes incoming messages to the screen
private:
    int _id;
    ConnectionHandler& _connectionHandler;
    boost::thread& _th1;

public:
    Write(int number, ConnectionHandler& connectionHandler, boost::thread& th1) : _id(number),
                                                                              _connectionHandler(connectionHandler),
                                                                                 _th1(th1){}

    void operator()(){
        while (1) {

            std::string answer;
            // Get back an answer: by using the expected number of bytes (len bytes + newline delimiter)
            if (!_connectionHandler.getLine(answer)) {
                break;
            }

            int len=answer.length();
            answer.resize(len-1);
            //IF message is a signout msg, INTERRUPT  the second thread, break the loop and close program
            if (answer == "ACK signout succeeded") {
                std::cout << answer << std::endl;
                std::cout << "Ready to exit. Press enter" <<std::endl;
                _th1.interrupt();
                break;
            }
            std::cout << answer << std::endl;
           
            

        }
    }
};

class Read {
    // This class is used to write outgoing messages, to the server
private:
    int _id;
    ConnectionHandler& _connectionHandler;
public:
    Read(int number, ConnectionHandler& connectionHandler) : _id(number),
                                                                             _connectionHandler(connectionHandler) {}

    void operator()() {
        // Loop as long you didn't get an EOF
        try {
            while (!std::cin.eof()) {
                const short bufsize = 1024;
                char buf[bufsize];
                std::cin.getline(buf, bufsize);
                // Catch the interruption and break the loop
                boost::this_thread::interruption_point();

                // EOF - signals that there is nothign more to read
                //    1. End of file
                //    2. End of stdin

                std::string line(buf);
                if (!_connectionHandler.sendLine(line)) {
                    break;
                }

            }

        }
        catch (boost::thread_interrupted &) {
		

        }
    }
};

#endif //CLIENT_TASKS_H
