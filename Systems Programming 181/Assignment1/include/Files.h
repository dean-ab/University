#ifndef FILES_H_
#define FILES_H_

#include "GlobalVariables.h"
#include <string>
#include <vector>
#include <algorithm>
#include <typeinfo>
#include <iostream>


using namespace std;

class BaseFile {
private:
	string name;
	
public:
	BaseFile(string name);
    virtual ~BaseFile();
	string getName() const;
	void setName(string newName);
	virtual int getSize() = 0;
	
};

class File : public BaseFile {
private:
	int size;
		
public:
    ~File();
	File(string name, int size); // Constructor
	int getSize(); // Return the size of the file
	
};

class Directory : public BaseFile {
private:
	vector<BaseFile*> children;
	Directory *parent;
    static bool sizeComp(BaseFile* a, BaseFile* b);
    static bool nameComp(BaseFile* a, BaseFile* b);
    string helpAbsolutePath();

    void verboseHandler(string sign);


public:
    Directory(string name, Directory *parent); // Constructor
    Directory(const Directory &dir);
    Directory& operator=(const Directory& other);
    ~Directory();
    Directory(Directory &&dir);
    Directory& operator=(Directory&& other);
    Directory *getParent() const; // Return a pointer to the parent of this directory
    void setParent(Directory *newParent); // Change the parent of this directory
    void addFile(BaseFile* file); // Add the file to children
    void removeFile(string name); // Remove the file with the specified name from children
    void removeFile(BaseFile* file); // Remove the file from children
    void sortByName(); // Sort children by name alphabetically (not recursively)
    void sortBySize(); // Sort children by size (not recursively)
    vector<BaseFile*> getChildren(); // Return children
    int getSize(); // Return the size of the directory (recursively)
    string getAbsolutePath();  //Return the path from the root to this
	BaseFile* removeFromChildren(string name);
	BaseFile* findInChildren(string name);
};

#endif