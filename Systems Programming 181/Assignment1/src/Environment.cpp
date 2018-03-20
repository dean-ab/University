#include "Environment.h"

//constructor
Environment::Environment() : commandsHistory(), fs(FileSystem()) {}

//destructor
Environment::~Environment() {
    for (vector<BaseCommand *>::iterator iter = commandsHistory.begin(); iter != commandsHistory.end(); iter++) {
        delete *iter;
    }

    string sign = "Environment::~Environment()";
    verboseHandler(sign);
}

//copy constructor
Environment::Environment(const Environment &old) : commandsHistory(), fs(old.fs){
    vector<BaseCommand*> oldHistory = old.commandsHistory;
    for (vector<BaseCommand *>::iterator iter = oldHistory.begin(); iter != oldHistory.end(); iter++) {
        BaseCommand* command;
        string args = (**iter).getArgs();
        if(typeid(**iter) == typeid(PwdCommand)) command = new PwdCommand(args);
        else if(typeid(**iter) == typeid(CdCommand)) command = new CdCommand(args);
        else if(typeid(**iter) == typeid(LsCommand)) command = new LsCommand(args);
        else if(typeid(**iter) == typeid(MkdirCommand)) command = new MkdirCommand(args);
        else if(typeid(**iter) == typeid(MkfileCommand)) command = new MkfileCommand(args);
        else if(typeid(**iter) == typeid(CpCommand)) command = new CpCommand(args);
        else if(typeid(**iter) == typeid(MvCommand)) command = new MvCommand(args);
        else if(typeid(**iter) == typeid(RenameCommand)) command = new RenameCommand(args);
        else if(typeid(**iter) == typeid(RmCommand)) command = new RmCommand(args);
        else if(typeid(**iter) == typeid(HistoryCommand)) command = new HistoryCommand(args, commandsHistory);
        else if(typeid(**iter) == typeid(VerboseCommand)) command = new VerboseCommand(args);
        else if(typeid(**iter) == typeid(ErrorCommand)) command = new ErrorCommand(args);
        else if(typeid(**iter) == typeid(ExecCommand)) command = new ExecCommand(args, commandsHistory);
        commandsHistory.push_back(command);
    }

    string sign = "Environment::Environment(const Environment &old) : commandsHistory(), fs(old.fs)";
    verboseHandler(sign);
}

//operator =
Environment& Environment::operator=(const Environment &other) {
    fs = other.fs;
    for (vector<BaseCommand *>::iterator iter = commandsHistory.begin(); iter != commandsHistory.end(); iter++) {
        delete *iter;
    }
    commandsHistory = {};
    vector<BaseCommand*> oldHistory = other.commandsHistory;
    for (vector<BaseCommand *>::iterator iter = oldHistory.begin(); iter != oldHistory.end(); iter++) {
        BaseCommand* command;
        string args = (**iter).getArgs();
        if(typeid(**iter) == typeid(PwdCommand)) command = new PwdCommand(args);
        else if(typeid(**iter) == typeid(CdCommand)) command = new CdCommand(args);
        else if(typeid(**iter) == typeid(LsCommand)) command = new LsCommand(args);
        else if(typeid(**iter) == typeid(MkdirCommand)) command = new MkdirCommand(args);
        else if(typeid(**iter) == typeid(MkfileCommand)) command = new MkfileCommand(args);
        else if(typeid(**iter) == typeid(CpCommand)) command = new CpCommand(args);
        else if(typeid(**iter) == typeid(MvCommand)) command = new MvCommand(args);
        else if(typeid(**iter) == typeid(RenameCommand)) command = new RenameCommand(args);
        else if(typeid(**iter) == typeid(RmCommand)) command = new RmCommand(args);
        else if(typeid(**iter) == typeid(HistoryCommand)) command = new HistoryCommand(args, commandsHistory);
        else if(typeid(**iter) == typeid(VerboseCommand)) command = new VerboseCommand(args);
        else if(typeid(**iter) == typeid(ErrorCommand)) command = new ErrorCommand(args);
        else if(typeid(**iter) == typeid(ExecCommand)) command = new ExecCommand(args, commandsHistory);
        commandsHistory.push_back(command);
    }

    string sign = "Environment& Environment::operator=(const Environment &other)";
    verboseHandler(sign);

    return *this;
}

void Environment::verboseHandler(string sign) {
    if ((verbose == 1) | (verbose == 3)) {
        cout << sign << endl;
    }
}

FileSystem& Environment::getFileSystem() {
    return fs;
}

const vector<BaseCommand*>& Environment::getHistory() const {
    return commandsHistory;
}

void Environment::addToHistory(BaseCommand *command) {
    commandsHistory.push_back(command);
}

void Environment::start() {
    string input = "";
    while (input != "exit") {
        Directory* wd = &fs.getWorkingDirectory();
        string abs = wd->getAbsolutePath();
        cout << abs << ">";
        getline(cin, input);
        inputHandler(input);
    }
}

vector<string> Environment::splitBySpace(string st) {
    stringstream path1(st);
    vector<string> output = {};
    string segment;
    while (getline(path1, segment, ' ')) {
        output.push_back(segment);
    }
    return output;
}

void Environment::inputHandler(string input) {
    if(input == "exit") {verbose = 0; return; }
    string commandName = splitBySpace(input)[0];
    BaseCommand* command = nullptr;

    if(commandName == "cd") {command = new CdCommand(input); }
    else if(commandName == "mkdir") {command = new MkdirCommand(input);  }
    else if(commandName == "mkfile") {command = new MkfileCommand(input);  }
    else if(commandName == "ls") {command = new LsCommand(input);  }
    else if(commandName == "pwd") {command = new PwdCommand(input);  }
    else if(commandName == "cp") {command = new CpCommand(input);  }
    else if(commandName == "mv") {command = new MvCommand(input);  }
    else if(commandName == "rm") {command = new RmCommand(input);  }
    else if(commandName == "rename") {command = new RenameCommand(input);  }
    else if(commandName == "history") {command = new HistoryCommand(input, commandsHistory);  }
    else if(commandName == "exec") {command = new ExecCommand(input, commandsHistory); }
    else if(commandName == "verbose") {command = new VerboseCommand(input);  }
    else command = new ErrorCommand(input);


    verboseHandler(command);
    command->execute(fs);
    addToHistory(command);
}

void Environment::verboseHandler(BaseCommand *command) {
    if ((verbose == 2) || (verbose == 3)) {
        cout << command->getArgs() << endl;
    }
}


//move constructor
Environment::Environment(Environment &&other) : commandsHistory(), fs(other.fs) {
    vector<BaseCommand*> oldHistory = other.commandsHistory;
    for (vector<BaseCommand *>::iterator iter = oldHistory.begin(); iter != oldHistory.end(); iter++) {
        commandsHistory.push_back(*iter);
        *iter = nullptr;
    }

    string sign = "Environment::Environment(Environment &&other) : commandsHistory(), fs(other.fs)";
    verboseHandler(sign);
}

//move assignment
Environment& Environment::operator=(Environment &&other) {
    fs = other.fs;
    for (vector<BaseCommand *>::iterator iter = commandsHistory.begin(); iter != commandsHistory.end(); iter++) {
        delete *iter;
    }
    vector<BaseCommand*> oldHistory = other.commandsHistory;
    for (vector<BaseCommand *>::iterator iter = oldHistory.begin(); iter != oldHistory.end(); iter++) {
        commandsHistory.push_back(*iter);
        *iter = nullptr;
    }

    string sign = "Environment& Environment::operator=(Environment &&other)";
    verboseHandler(sign);

    return *this;
}

