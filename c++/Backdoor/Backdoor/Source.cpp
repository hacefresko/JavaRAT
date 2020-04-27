#include <iostream>
using namespace std;

#include "Client.h"

int main() {
	Client client = Client("192.168.0.162", 5123);
	while(!client.initConnection());

	return 0;
}