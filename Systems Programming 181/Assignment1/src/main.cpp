#include <iostream>

#include "Environment.h"

// ... You may not change this file

unsigned int verbose = 0;

int main(int , char **) {
    Environment env = Environment();
    env.start();
    return 0;
}