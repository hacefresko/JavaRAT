# Basic backdoors for Windows
Set of fake Java applications with backdoors made to learn about socket programming and reverse shells.

Each application triggers a reverse shell when executed, opening a PowerShell session. It also allows transfering files in both directions and taking screenshots (use help command for more info).

Applications:
* CSGOHacks: Fake application to gain cheats for the game "Counter Strike: Global Offensive"
* KeyGen: Fake key generator for some programs from the Adobe Suite
* RAW: Just the backdoor, without any masking application
* YoutubeMP3: Fake YouTube to mp3 converter

## Usage

* The attacker must run the Attacker project launched from launcher/main.java. It will create a server listening by default on port 5123 (this can be easily changed in main.java).
* The application for the victim must be configured by specifying the attacker's IP and port. Then it must be packed into a .jar file. I recommend using any application to convert the .jar to .exe such as Launch4j, although the vicitm still needs to have Javs installed. When the victim executes the application, it will connect to the attacker machine and open the PowerShell session.
