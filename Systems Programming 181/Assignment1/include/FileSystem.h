#ifndef FILESYSTEM_H_
#define FILESYSTEM_H_

#include "Files.h"
#include <sstream>


class FileSystem {
private:
	Directory* rootDirectory;
	Directory* workingDirectory;
    Directory* findDirectory(string path, FileSystem &fs);
    vector<string> splitPath(string path);

    void verboseHandler(string sign);

public:
	FileSystem();
    FileSystem(const FileSystem &old);
    FileSystem& operator=(const FileSystem& other);
    FileSystem(FileSystem &&old);
    FileSystem& operator=(FileSystem&& other);
    virtual ~FileSystem();
	Directory& getRootDirectory() const; // Return reference to the root directory
	Directory& getWorkingDirectory() const; // Return reference to the working directory
	void setWorkingDirectory(Directory *newWorkingDirectory); // Change the working directory of the file system


};


#endif