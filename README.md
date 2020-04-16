# Fake youtube to mp3 converter

Usage:
  1. Server (attacker) is executed and starts to listen on port 5123 (can be changed)
  2. Victim executes the client, thinking it's a YoutubeMP3 converter to download music. 
     The client connects to the server (server's ip must be changed depending on the user) 
     and a PowerShell session is opened on the victim's machine
  3. The attacker can interact with the PowerShell session as a regular PowerShell terminal
     and use extra commands (are being implemented right now, type help for more info).
     
Note:
  Use ngrok or another secure port forwarding service to run attacks accross the Internet
