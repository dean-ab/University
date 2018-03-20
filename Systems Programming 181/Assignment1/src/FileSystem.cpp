#include "FileSystem.h"

//constructor
FileSystem::FileSystem() : rootDirectory(new Directory("/", nullptr)), workingDirectory(rootDirectory) {}

Directory& FileSystem::getRootDirectory() const {
    return *rootDirectory;
}

Directory& FileSystem::getWorkingDirectory() const {
    return *workingDirectory;
}

void FileSystem::setWorkingDirectory(Directory *newWorkingDirectory) {
    workingDirectory = newWorkingDirectory;
}


Directory* FileSystem::findDirectory(string path, FileSystem &fs) {

    Directory* curr;
    vector <string> dirs;
    if(path.at(0) == '/')  {
        curr = &(fs.getRootDirectory());
        path = path.substr(1, path.length());
    }
    else
        curr = &(fs.getWorkingDirectory());
    bool dirFound = false;
    dirs = splitPath(path);
    for (unsigned int i = 0; i < dirs.size(); i++) {
        for (unsigned int j = 0; (j < (curr->getChildren()).size()) & !dirFound; j++) {
            if (curr->getChildren().at(j)->getName() == dirs.at(i)) {
                if(typeid(*(curr->getChildren().at(j))) == typeid(*curr)) {
                    curr = (Directory*)(curr->getChildren().at(j));
                    dirFound = true;
                }
            }
        }
        if (!dirFound) {
            cout << "The system cannot find the path specified" << endl;
            return nullptr;
        }
        dirFound = false;
    }
    return curr;
}

vector<string> FileSystem::splitPath(string path) {
    stringstream path1(path);
    vector<string> output = {};
    string segment;
    while (getline(path1, segment, '/')) {
        output.push_back(segment);
    }
    return output;
}

// Rule Of 5 Functions //

// Destructor
FileSystem::~FileSystem() {
    delete rootDirectory;
    rootDirectory = nullptr;
    workingDirectory = nullptr;

    string sign = "FileSystem::~FileSystem()";
    verboseHandler(sign);
}

// Copy Constructor
FileSystem::FileSystem(const FileSystem &old) : rootDirectory(new Directory(*old.rootDirectory)), workingDirectory(old.workingDirectory){
    string sign = "FileSystem::FileSystem(const FileSystem &old) : rootDirectory(new Directory(*old.rootDirectory)), workingDirectory(old.workingDirectory)";
    verboseHandler(sign);
}

// Assignment Operator
FileSystem& FileSystem::operator=(const FileSystem &other) {
    delete rootDirectory;
    rootDirectory = new Directory(*other.rootDirectory);
    string path = other.getWorkingDirectory().getAbsolutePath();
    Directory* dir = findDirectory(path, *this);
    workingDirectory = dir;

    string sign = "FileSystem& FileSystem::operator=(const FileSystem &other)";
    verboseHandler(sign);
    return *this;
}

// Move Constructor
FileSystem::FileSystem(FileSystem &&old) : rootDirectory(old.rootDirectory), workingDirectory(old.workingDirectory){
    old.rootDirectory = nullptr;
    old.workingDirectory = nullptr;

    string sign = "FileSystem::FileSystem(FileSystem &&old) : rootDirectory(old.rootDirectory), workingDirectory(old.workingDirectory)";
    verboseHandler(sign);
}

// Move Assignment
FileSystem& FileSystem::operator=(FileSystem &&other) {
    delete rootDirectory;
    rootDirectory = other.rootDirectory;
    workingDirectory = other.workingDirectory;
    other.rootDirectory = nullptr;
    other.workingDirectory = nullptr;

    string sign = "FileSystem& FileSystem::operator=(FileSystem &&other)";
    verboseHandler(sign);
    return *this;
}

void FileSystem::verboseHandler(string sign) {
    if ((verbose == 1) | (verbose == 3)) {
        cout << sign << endl;
    }
}