#include "Commands.h"

BaseCommand::BaseCommand(string args) : args(args){}

string BaseCommand::getArgs() {
    return args;
}

string BaseCommand::toString() {
    return args;
}

//returns a pointer to the last directory in the given path(relative or absolute). The path has to be a path to a directory and not a file.
//in case the directory wasnt found, nullptr will be returned.
Directory* BaseCommand::findDirectory(string path, FileSystem &fs) {
    Directory* curr;
    vector <string> dirs;
    if(path.at(0) == '/')  {
        curr = &(fs.getRootDirectory());
        path = path.substr(1, path.length());
    }
    else {
        curr = &(fs.getWorkingDirectory());
        if (curr->getName() == path) { return curr; }
    }
    bool dirFound = false;
    dirs = splitPath(path);
    unsigned int i;
    for (i = 0; i < dirs.size(); i++) {
        if(dirs[i] == "..") {
            if(curr->getParent() == nullptr)
                return nullptr;
            curr = curr->getParent();
            dirFound = true;
        }
        unsigned int j;
        for (j = 0; (j < curr->getChildren().size()) & !dirFound; j++) {
            if (curr->getChildren().at(j)->getName() == dirs.at(i)) {
                if(typeid(*(curr->getChildren().at(j))) == typeid(*curr)) {
                    curr = (Directory*)(curr->getChildren().at(j));
                    dirFound = true;
                }
            }
        }
        if (!dirFound) {
            return nullptr;
        }
        dirFound = false;
    }
    return curr;
}

BaseFile* BaseCommand::findFile(string path, FileSystem &fs) {
    Directory* curr;
    vector <string> dirs;
    if(path.at(0) == '/')  {
        curr = &(fs.getRootDirectory());
        path = path.substr(1, path.length());
    }
    else {
        curr = &(fs.getWorkingDirectory());
        if (curr->getName() == path) { return curr; }
    }
    bool dirFound = false;
    dirs = splitPath(path);
    unsigned int i;
    for (i = 0; i < dirs.size(); i++) {
        if(dirs[i] == "..") {
            if(curr->getParent() == nullptr)
                return nullptr;
            curr = curr->getParent();
            dirFound = true;
        }
        unsigned int j;
        for (j = 0; (j < curr->getChildren().size()) & !dirFound; j++) {
            if (curr->getChildren().at(j)->getName() == dirs.at(i)) {
                if(typeid(*(curr->getChildren().at(j))) == typeid(*curr)) {
                    if (i == dirs.size()-1) {
                        return (Directory*)(curr->getChildren().at(j));
                    }
                    curr = (Directory*)(curr->getChildren().at(j));
                    dirFound = true;
                } else {
                    if (i == dirs.size()-1) {
                        return (File*)(curr->getChildren().at(j));
                    }
                }
            }
        }
        if (!dirFound) {
            return nullptr;
        }
        dirFound = false;
    }
    return nullptr;
}

vector<string> BaseCommand::splitPath(string path) {
    stringstream path1(path);
    vector<string> output = {};
    string segment;
    while (getline(path1, segment, '/')) {
        output.push_back(segment);
    }
    return output;
}

vector<string> BaseCommand::splitBySpace(string st) {
    stringstream path1(st);
    vector<string> output = {};
    string segment;
    while (getline(path1, segment, ' ')) {
        output.push_back(segment);
    }
    return output;
}

PwdCommand::PwdCommand(string args) : BaseCommand(args) {}

void PwdCommand::execute(FileSystem &fs) {
    cout << fs.getWorkingDirectory().getAbsolutePath() << endl;
}

string PwdCommand::toString() {
    return "pwd";
}

//-----

CdCommand::CdCommand(string args) : BaseCommand(args) {}

void CdCommand::execute(FileSystem &fs) {
    string input = getArgs();
    input = input.substr(3, input.length());
    Directory* found = findDirectory(input, fs);
    if(found == nullptr) {
        cout << "The system cannot find the path specified" << endl;
        return;
    }
    fs.setWorkingDirectory(found);
}

string CdCommand::toString() {
    return "cd";
}

//-----

LsCommand::LsCommand(string args) : BaseCommand(args) {}

void LsCommand::execute(FileSystem &fs) {
    string path = getArgs();
    vector<string> input = splitBySpace(path);
    Directory* curr;
    if(input.size() == 1) {
        curr = &fs.getWorkingDirectory();
        curr->sortByName();
    } else {
        if(input[1].at(0) == '-') {
            if(input.size() == 2) {
                curr = &fs.getWorkingDirectory();
                curr->sortByName();
                curr->sortBySize();
            } else {
                path = input[2];
                curr = findDirectory(path, fs);
                if(curr != nullptr) {
                    curr->sortByName();
                    curr->sortBySize();
                }
                else {
                    cout << "The system cannot find the path specified" << endl;
                    return;
                }
            }
        } else {
            path = input[1];
            curr = findDirectory(path, fs);
            if(curr != nullptr){
                curr->sortByName();
            } else {
                cout << "The system cannot find the path specified" << endl;
                return;
            }
        }
    }
    for(unsigned int i=0; i<curr->getChildren().size(); i++) {
        string typeF = "FILE";
        if(typeid(*(curr->getChildren()[i])) == typeid(Directory)) {
            typeF = "DIR";
        }
        cout << typeF << "\t" << (curr->getChildren()[i])->getName() << "\t" << (curr->getChildren()[i])->getSize() << endl;
    }
}

string LsCommand::toString() {
    return "ls";
}

MkdirCommand::MkdirCommand(string args) : BaseCommand(args) {}

void MkdirCommand::execute(FileSystem &fs) {
    string path = getArgs();
    path = path.substr(6, path.length());
    buildDir(path, fs);
}

void MkdirCommand::buildDir(string path, FileSystem &fs) {
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
    bool exist = true;
    for (unsigned int i = 0; i < dirs.size(); i++) {
        if(dirs[i] == "..") {
            if(curr->getParent() == nullptr)
                return;
            curr = curr->getParent();
            dirFound = true;
        }
        for (unsigned int j = 0; (j < curr->getChildren().size()) & !dirFound; j++) {
            if (curr->getChildren().at(j)->getName() == dirs.at(i)) {
                if(typeid(*(curr->getChildren().at(j))) == typeid(*curr)) {
                    curr = (Directory*)((curr->getChildren()).at(j));
                    dirFound = true;
                } else {
                    cout << "The directory already exists" << endl;
                    return;;
                }
            }
        }
        if (!dirFound) {
            Directory* dir = new Directory(dirs.at(i), curr);
            curr = dir;
            exist = false;
        }
        dirFound = false;
    }
    if(exist) {
        cout << "The directory already exists" << endl;
    }
}

string MkdirCommand::toString() {
    return "mkdir";
}

MkfileCommand::MkfileCommand(string args) : BaseCommand(args) {}

void MkfileCommand::execute(FileSystem &fs) {
    string path = getArgs();
    path = path.substr(7);
    vector<string> tmp = splitBySpace(path);
    path = tmp[0];
    stringstream conv(tmp[1]);
    unsigned int size = 0;
    conv >> size;
    tmp = splitPath(path);
    string fileName = tmp[tmp.size()-1];
    Directory* curr;
    if(tmp.size() == 1) {
        curr = &fs.getWorkingDirectory();
    } else {
        path = path.substr(0, path.length() - fileName.length());
        curr = findDirectory(path, fs);
    }
    if(curr == nullptr) {
        cout << "The system cannot find the path specified" << endl;
        return;
    }
    for (unsigned int j = 0; j < (curr->getChildren()).size(); j++) {
        if (curr->getChildren()[j]->getName() == fileName) {
            cout << "File already exists" << endl;
            return;
        }
    }
    curr->addFile(new File(fileName, size));
}

string MkfileCommand::toString() {
    return "mkfile";
}

CpCommand::CpCommand(string args) : BaseCommand(args) {}

void CpCommand::execute(FileSystem &fs) {
    string command = getArgs().substr(3);
    vector<string> dirs = splitBySpace(command);
    Directory *destination = findDirectory(dirs[1], fs);
    BaseFile *source = findFile(dirs[0], fs);
    if ((source == nullptr) || (destination == nullptr)) {
        cout << "No such file or directory" << endl;
        return;
    } else {
        BaseFile *toCopy;
        if (typeid(*source) == typeid(Directory)) {
            toCopy = new Directory((Directory &) (*source));
            destination->addFile(toCopy);
            return;
        } else {
            if(destination->findInChildren(source->getName()) == nullptr) {
                toCopy = new File(source->getName(), source->getSize());
                destination->addFile(toCopy);
            }
            return;
        }
    }
}

string CpCommand::toString() {
    return "cp";
}

BaseCommand::~BaseCommand() {

}

MvCommand::MvCommand(string args): BaseCommand(args) {}

void MvCommand::execute(FileSystem &fs) {
    string command = getArgs().substr(3);
    vector<string> dirs = splitBySpace(command);
    vector<string> tmp = splitPath(dirs[0]);
    string fileName = tmp[tmp.size()-1];
    if(fileName == "..") {
        cout << "Can't move directory" << endl;
        return;
    }
    Directory* source;
    if(tmp.size() == 1) {
        source = &fs.getWorkingDirectory();
    } else {
        dirs[0] = dirs[0].substr(0, dirs[0].length() - fileName.length());
        source = findDirectory(dirs[0], fs);
    }
    Directory* destination = findDirectory(dirs[1], fs);
    if ((source == nullptr) || (destination == nullptr)) {
        cout << "No such file or directory" << endl;
        return;
    }
    BaseFile* toMove = source->removeFromChildren(fileName);
    if(toMove == nullptr) {
        cout << "No such file or directory" << endl;
        return;
    }
    if(typeid(*toMove) == typeid(Directory) && !validDirectory(fileName, fs)) {
        source->addFile(toMove);
        cout << "Can't move directory" << endl;
        return;
    }
    destination->addFile(toMove);

}

string MvCommand::toString() {
    return "mv";
}

bool BaseCommand::validDirectory(string name, FileSystem fs) {
    Directory* curr = &fs.getWorkingDirectory();
    while (curr != nullptr) {
        if (curr->getName() == name) {

            return false;
        }
        curr = curr->getParent();
    }
    return true;
}

RmCommand::RmCommand(string args): BaseCommand(args) {}

void RmCommand::execute(FileSystem &fs) {
    string command = getArgs().substr(3);
    if(command == "/") {
        cout << "Can't remove directory" << endl;
        return;
    }
    vector<string> tmp = splitPath(command);
    string fileName = tmp[tmp.size()-1];
    Directory* source;
    if(tmp.size() == 1) {
        source = &fs.getWorkingDirectory();
    } else {
        command = command.substr(0, command.length() - fileName.length());
        source = findDirectory(command, fs);
    }
    if ((source == nullptr)) {
        cout << "No such file or directory" << endl;
        return;
    }
    vector<BaseFile*> vec = source->getChildren();
    bool found = false;
    for (vector<BaseFile *>::iterator iter = vec.begin(); iter != vec.end(); iter++) {
        if((*iter)->getName() == fileName) {
            found = true;
            if(typeid(**iter) == typeid(Directory)) {
                if((*iter) == &fs.getWorkingDirectory()) {
                    cout << "Can't remove directory" << endl;
                    return;
                }
            }
        }
    }
    if(!found) {
        cout << "No such file or directory" << endl;
    }
    source->removeFile(fileName);
}

string RmCommand::toString() {
    return "rm";
}

RenameCommand::RenameCommand(string args) : BaseCommand(args) {}


void RenameCommand::execute(FileSystem &fs) {
    string command = getArgs().substr(7);
    vector <string> tmp = splitBySpace(command);
    string path = tmp[0];
    string newName = tmp[1];
    if(path == "/") {
        if(&fs.getRootDirectory() == &fs.getWorkingDirectory()) {
            cout << "Can't rename the working directory" << endl;
            return;
        }
        fs.getRootDirectory().setName(newName);
        return;
    }
    Directory* source;
    vector<string> splitThePath = splitPath(path);
    string oldName = splitThePath[splitThePath.size()-1];
    if(splitThePath.size() == 1) {
        source = &fs.getWorkingDirectory();
    } else {
        path = path.substr(0, path.length() - oldName.length());
        source = findDirectory(path, fs);
    }
    if(source == nullptr) {
        cout << "No such file or directory" << endl;
        return;
    }
    if(source->findInChildren(newName) != nullptr) {
        return;
    }
    BaseFile* theFile = source->findInChildren(oldName);
    if(theFile == nullptr) {
        cout << "No such file or directory" << endl;
        return;
    }
    if(typeid(*theFile) == typeid(Directory)) {
        if(theFile == &fs.getWorkingDirectory()) {
            cout << "Can't rename the working directory" << endl;
            return;
        }
    }
    theFile->setName(newName);

}

string RenameCommand::toString() {
    return "rename";
}

HistoryCommand::HistoryCommand(string args, const vector<BaseCommand *> &history) : BaseCommand(args), history(history) {}

void HistoryCommand::execute(FileSystem &fs) {

    for (unsigned int i = 0; i < history.size(); i++) {
        cout << i << "\t" + history.at(i)->getArgs() << endl;
    }
}

string HistoryCommand::toString() {
    return "history";
}

ExecCommand::ExecCommand(string args, const vector<BaseCommand *> &history) : BaseCommand(args), history(history) {}

void ExecCommand::execute(FileSystem &fs) {
    string command = getArgs().substr(5);
    stringstream conv(command);
    unsigned int comNum = 0;
    conv >> comNum;
    if ((comNum < 0) || (comNum > history.size())) {
        cout << "Command not found" << endl;
    }
    else history.at(comNum)->execute(fs);
}

string ExecCommand::toString() {
    return "exec";
}

ErrorCommand::ErrorCommand(string args): BaseCommand(args) {}

void ErrorCommand::execute(FileSystem &fs) {
    cout << splitBySpace(getArgs())[0] << ": Unknown command" << endl;
}

string ErrorCommand::toString() {
    return "error";
}

VerboseCommand::VerboseCommand(string args) : BaseCommand(args) {}

void VerboseCommand::execute(FileSystem &fs) {
    unsigned int command = stoi(getArgs().substr(8));
    if (command == 0) {
        verbose = 0;
    } else if (command == 1) {
        verbose = 1;
    } else if (command == 2) {
        verbose = 2;
    } else if (command == 3) {
        verbose = 3;
    } else cout << "Wrong verbose input" << endl;
}

string VerboseCommand::toString() {
    return "verbose";
}

