#ifndef CLIENT_H
#define CLIENT_H

#define _WINSOCK_DEPRECATED_NO_WARNINGS
#include <string>
#include <WinSock2.h>
#include <iostream>
using namespace std;

#pragma comment(lib,"ws2_32.lib")

class Client {
public:

	Client(string ip, int port) {
		s = NULL;
		serverInfo.sin_addr.s_addr = inet_addr(ip.c_str()); //c_str converts string to a pointer to the first char of the string, inet_addr matches ip format
		serverInfo.sin_family = AF_INET;
		serverInfo.sin_port = htons(port); //htons matches port format
	}

	bool initConnection() {
		// Initialize winsock library
		WSADATA wsa;
		if (WSAStartup(MAKEWORD(2, 2), &wsa) != 0) {
			cout << "Problem initializing WinSock" << WSAGetLastError() << endl;
			return false;
		}

		// AF_INET (this is IP version 4)
		// SOCK_STREAM(this means connection oriented TCP protocol)
		if ((s = socket(AF_INET, SOCK_STREAM, IPPROTO_TCP)) == INVALID_SOCKET) {
			cout << "Could not create socket : " << WSAGetLastError() << endl;
			return false;
		}

		//struct sockaddr pointer pointing to memory address of serverInfo
		if (connect(s, (struct sockaddr*) &serverInfo, sizeof(serverInfo)) < 0){
			cout << "Could not connect" << endl;
			return false;
		}

		cout << "Connected" << endl;
		return true;
	}

private:
	SOCKET s;
	struct sockaddr_in serverInfo;
};

#endif
