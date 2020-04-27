#include <istream>
using namespace std;

#include "Server.h"
#include "Connection.h"

int main() {
	Server server = Server("192.168.0.162", 5123);
	if (server.initServer()) {
		Connection mainCon = server.acceptConnection();
	}
	
	return 0;
}