#ifndef SERVER_H
#define SERVER_H

#define _WINSOCK_DEPRECATED_NO_WARNINGS
#include <string>
#include <WinSock2.h>
#include <iostream>
using namespace std;

#pragma comment(lib,"ws2_32.lib")

class Server {
public:

	Server(string ip, int port) {
		// _port is just for displaying info to the user, as sin_port is formatted
		_port = port;
		s = NULL;
		serverInfo.sin_addr.s_addr = inet_addr(ip.c_str()); //c_str converts string to a pointer to the first char of the string, inet_addr matches ip format
		serverInfo.sin_family = AF_INET;
		serverInfo.sin_port = htons(_port); //htons matches port format
	}

	bool initServer() {
		cout << "Initializing server... ";

		// Initialize winsock library
		WSADATA wsa;
		if (WSAStartup(MAKEWORD(2, 2), &wsa) != 0) {
			cout << "Problem initializing WinSock: " << WSAGetLastError() << endl;
			return false;
		}

		// AF_INET (this is IP version 4)
		// SOCK_STREAM(this means connection oriented TCP protocol)
		if ((s = socket(AF_INET, SOCK_STREAM, IPPROTO_TCP)) == INVALID_SOCKET) {
			cout << "Could not create socket: " << WSAGetLastError() << endl;
			return false;
		}

		// struct sockaddr pointer pointing to memory address of serverInfo
		if (bind(s, (struct sockaddr*) & serverInfo, sizeof(serverInfo)) == SOCKET_ERROR){
			cout << "Bind failed with error code: " << WSAGetLastError() << endl;
			return false;
		}

		// SOMAXCONN means to set the maximum number of connection to the maximum both possible and reasonable number
		listen(s, SOMAXCONN);

		cout << "Done" << endl;

		return true;
	}

	SOCKET acceptConnection() {
		cout << "Waiting for connections on port " << _port << endl;

		int c = sizeof(struct sockaddr_in);
		SOCKET newSocket = accept(s, (struct sockaddr*) &serverInfo, &c);
		if (newSocket == INVALID_SOCKET) {
			cout << "Accept failed with error code: " << WSAGetLastError() << endl;
			return NULL;
		}

		cout << "Connected" << endl;
		return newSocket;
	}


private:
	int _port;
	SOCKET s;
	struct sockaddr_in serverInfo;
};

#endif
