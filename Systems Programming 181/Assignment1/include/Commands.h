#ifndef COMMANDS_H_
#define COMMANDS_H_

#include <string>
#include "FileSystem.h"
#include <sstream>



class BaseCommand {
private:
	string args;
public:

	BaseCommand(string args);
	virtual ~BaseCommand();
	string getArgs();
	virtual void execute(FileSystem & fs) = 0;
	virtual string toString() = 0;
	Directory* findDirectory(string path, FileSystem &fs);
	vector<string> splitPath(string path);
	vector<string> splitBySpace(string st);

	bool validDirectory(string name, FileSystem fs);
    string isDirectoryOrFile(string path, FileSystem &fs);
	BaseFile* findDirectoryOrFile(string path, FileSystem &fs);
    Directory* findDirectory2(string path, FileSystem &fs);
    BaseFile* findFile(string path, FileSystem &fs);
	Directory* findFileParent(string path, FileSystem &fs);



};

class PwdCommand : public BaseCommand {
private:
public:
	PwdCommand(string args);
	void execute(FileSystem & fs); // Every derived class should implement this function according to the document (pdf)
	virtual string toString();
};

class CdCommand : public BaseCommand {
private:
public:
	CdCommand(string args);
	void execute(FileSystem & fs);
	string toString();
};

class LsCommand : public BaseCommand {
private:
public:
	LsCommand(string args);
	void execute(FileSystem & fs);
	string toString();
};

class MkdirCommand : public BaseCommand {
private:
    void buildDir(string path, FileSystem &fs);
public:
	MkdirCommand(string args);
	void execute(FileSystem & fs);
	string toString();
};

class MkfileCommand : public BaseCommand {
private:
public:
	MkfileCommand(string args);
	void execute(FileSystem & fs);
	string toString();
};

class CpCommand : public BaseCommand {
private:
public:
	CpCommand(string args);
	void execute(FileSystem & fs);
	string toString();
};

class MvCommand : public BaseCommand {
private:
public:
	MvCommand(string args);
	void execute(FileSystem & fs);
	string toString();

};

class RenameCommand : public BaseCommand {
private:
public:
	RenameCommand(string args);
	void execute(FileSystem & fs);
	string toString();
};

class RmCommand : public BaseCommand {
private:
public:
	RmCommand(string args);
	void execute(FileSystem & fs);
	string toString();
};

class HistoryCommand : public BaseCommand {
private:
	const vector<BaseCommand *> & history;
public:
	HistoryCommand(string args, const vector<BaseCommand *> & history);
	void execute(FileSystem & fs);
	string toString();
};


class VerboseCommand : public BaseCommand {
private:
public:
	VerboseCommand(string args);
	void execute(FileSystem & fs);
	string toString();
};

class ErrorCommand : public BaseCommand {
private:
public:
	ErrorCommand(string args);
	void execute(FileSystem & fs);
	string toString();
};

class ExecCommand : public BaseCommand {
private:
	const vector<BaseCommand *> & history;
public:
	ExecCommand(string args, const vector<BaseCommand *> & history);
	void execute(FileSystem & fs);
	string toString();
};


#endif