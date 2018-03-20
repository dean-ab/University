# define some Makefile variables for the compiler and compiler flags
# to use Makefile variables later in the Makefile: $()
CC = g++
CFLAGS  = -g -Wall -Weffc++ -std=c++11
LFLAGS  = -L/usr/lib -lboost_thread -lboost_system

# All Targets
all: BBclient

# Tool invocations
# Executable "hello" depends on the files hello.o and run.o.
BBclient: bin/BBclient.o bin/connectionHandler.o
	@echo 'Building target: BBclient'
	@echo 'Invoking: C++ Linker'
	$(CC) -o bin/BBclient bin/BBclient.o bin/connectionHandler.o $(LFLAGS)
	@echo 'Finished building target: BBclient'
	@echo 'May the good grade will rest upon us'
	@echo ' '

# Depends on the source and header files
bin/connectionHandler.o: src/connectionHandler.cpp include/connectionHandler.h
	$(CC) $(CFLAGS) -c -Iinclude -o bin/connectionHandler.o src/connectionHandler.cpp

# Depends on the source and header files
bin/BBclient.o: src/BBclient.cpp include/Tasks.h include/connectionHandler.h
	$(CC) $(CFLAGS) -c -Iinclude -o bin/BBclient.o src/BBclient.cpp

#Clean the build directory
clean:
	rm -f bin/*