#ifndef CONNECTION_H
#define CONNECTION_H

#define _WINSOCK_DEPRECATED_NO_WARNINGS
#include <string>
#include <WinSock2.h>
#include <iostream>
using namespace std;

#pragma comment(lib,"ws2_32.lib")

class Connection {
public:
	Connection(SOCKET s) {
		this->s = s;
	}

private:
	SOCKET s;
};

#endif