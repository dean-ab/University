#ifndef ENVIRONMENT_H_
#define ENVIRONMENT_H_

#include "Files.h"
#include "Commands.h"

#include <string>
#include <vector>
#include <iostream>


using namespace std;

class Environment {
private:
	vector<BaseCommand*> commandsHistory;
	FileSystem fs;
    void inputHandler(string input);
	void verboseHandler(BaseCommand* command);
    vector<string> splitBySpace(string st);
	void verboseHandler(string sign);

public:
	Environment();
    virtual ~Environment();
    Environment(const Environment &old);
    Environment& operator=(const Environment &other);
	Environment(Environment &&other);
	Environment& operator=(Environment&& other);
	void start();
	FileSystem& getFileSystem(); // Get a reference to the file system
	void addToHistory(BaseCommand *command); // Add a new command to the history
	const vector<BaseCommand*>& getHistory() const; // Return a reference to the history of commands
};

#endif