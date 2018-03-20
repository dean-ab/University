#include "Files.h"

BaseFile::BaseFile(string name) : name(name){}

BaseFile::~BaseFile() {}

void BaseFile::setName(string newName) {
    name = newName;
}

string BaseFile::getName() const {
    return name;
}

File::File(string name, int size) : BaseFile(name), size(size){}

File::~File() {}

int File::getSize() {
    return size;
}

//constructor
Directory::Directory(string name, Directory *parent) : BaseFile(name), children(), parent(parent){
    if(parent != nullptr) {
        (*parent).addFile(this);
    }
}

int Directory::getSize() {
    int size = 0;
    if(children.empty())
        return size;
    for (vector<BaseFile *>::iterator iter = children.begin(); iter != children.end(); iter++) {
        size += (**iter).getSize();
    }
    return size;
}

Directory* Directory::getParent() const {
    return parent;
}

void Directory::setParent(Directory *newParent) {
    if(parent != nullptr) {
        parent->removeFromChildren(this->getName());
    }
    parent = newParent;
}

vector<BaseFile*> Directory::getChildren() {
    return children;
}

void Directory::addFile(BaseFile *file) {
    if(file != nullptr) {
        if(typeid(*file) == typeid(Directory)) {
            ((Directory*)file)->setParent(this);
        }
    }
    children.push_back(file);
}

void Directory::removeFile(string name) {
    for (vector<BaseFile *>::iterator iter = children.begin(); iter != children.end(); iter++) {
        if((**iter).getName() == name) {
            removeFile(*iter);
            children.erase(iter);
            return;
        }
    }
}

void Directory::removeFile(BaseFile *file) {
    delete file;
}

bool Directory::sizeComp(BaseFile* a, BaseFile* b) {
    return (*a).getSize() < (*b).getSize();
}

bool Directory::nameComp(BaseFile *a, BaseFile *b) {
    return (*a).getName() < (*b).getName();
}

void Directory::sortBySize() {
    sort(children.begin(), children.end(), sizeComp);
}

void Directory::sortByName() {
    sort(children.begin(), children.end(), nameComp);
}

string Directory::getAbsolutePath() {
    if(getParent() != nullptr) {
        return (helpAbsolutePath()).substr(1);
    }
    return "/";
}

string Directory::helpAbsolutePath() {
    if(getParent() == nullptr) {
        return "/";
    }
    return getParent()->helpAbsolutePath() + "/" + getName();
}

// Rule of 5 Functions //

// Destructor ----------------------- MEMORY LEAK!!!!!!!
Directory::~Directory() {
    for (vector<BaseFile *>::iterator iter = children.begin(); iter != children.end(); iter++) {
        delete *iter;
        *iter = nullptr;
    }

    string sign = "Directory::~Directory()";
    verboseHandler(sign);
}

// Copy Constructor
Directory::Directory(const Directory &dir) : BaseFile(dir.getName()), children(), parent(dir.getParent()){
    vector<BaseFile *> oldChildren = dir.children;
    for (vector<BaseFile *>::iterator iter = oldChildren.begin(); iter != oldChildren.end(); iter++) {
        BaseFile* child;
        if(typeid(**iter) == typeid(this)) {
            child = new Directory((Directory&)**iter);
        } else {
            child = new File((**iter).getName(), (**iter).getSize());
        }
        children.push_back(child);
    }

    string sign = "Directory::Directory(const Directory &dir) : BaseFile(dir.getName()), children(), parent(dir.getParent())";
    verboseHandler(sign);
}

// Assignment Operator
Directory& Directory::operator=(const Directory &other) {
    if(&other == this) {
        return *this;
    }
    setName(other.getName());
    setParent(other.getParent());
    for (vector<BaseFile *>::iterator iter = children.begin(); iter != children.end(); iter++) {
        delete *iter;
    }
    children = {};
    vector<BaseFile *> oldChildren = other.children;
    for (vector<BaseFile *>::iterator iter = oldChildren.begin(); iter != oldChildren.end(); iter++) {
        BaseFile* child;
        if(typeid(**iter) == typeid(this)) {
            child = new Directory((Directory&)**iter);
        } else {
            child = new File((**iter).getName(), (**iter).getSize());
        }
        children.push_back(child);
    }

    string sign = "Directory& Directory::operator=(const Directory &other)";
    verboseHandler(sign);

    return *this;
}

// Move Constructor
Directory::Directory(Directory &&dir): BaseFile(dir.getName()),children(), parent(dir.getParent()) {
    for (vector<BaseFile *>::iterator iter = dir.getChildren().begin(); iter != dir.getChildren().end(); iter++) {
        children.push_back(*iter);
        if(typeid(**iter) == typeid(this)) {
            ((Directory*)(*iter))->parent = this;
        }
        *iter = nullptr;
    }
    dir.children = {};
    string sign = "Directory::Directory(Directory &&dir): BaseFile(dir.getName()),children(), parent(dir.getParent())";
    verboseHandler(sign);
}

// Move Assignment
Directory& Directory::operator=(Directory &&other) {
    setName(other.getName());
    setParent(other.getParent());
    for (vector<BaseFile *>::iterator iter = children.begin(); iter != children.end(); iter++) {
        delete *iter;
    }
    children = {};
    for (vector<BaseFile *>::iterator iter = other.getChildren().begin(); iter != other.getChildren().end(); iter++) {
        children.push_back(*iter);
        (*iter) = nullptr;
    }
    other.children = {};
    string sign = "Directory& Directory::operator=(Directory &&other)";
    verboseHandler(sign);


    return *this;
}

void Directory::verboseHandler(string sign) {
    if ((verbose == 1) | (verbose == 3)) {
        cout << sign << endl;
    }
}

BaseFile* Directory::removeFromChildren(string name) {
    for (vector<BaseFile *>::iterator iter = children.begin(); iter != children.end(); iter++) {
        if((**iter).getName() == name) {
            BaseFile* toRm = *iter;
            children.erase(iter);
            return toRm;
        }
    }
    return nullptr;
}

BaseFile* Directory::findInChildren(string name) {
    for (vector<BaseFile *>::iterator iter = children.begin(); iter != children.end(); iter++) {
        if((**iter).getName() == name) {
            BaseFile* toRm = *iter;
            return toRm;
        }
    }
    return nullptr;
}