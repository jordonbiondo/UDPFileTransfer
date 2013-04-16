client-dir=org/DataCom/Client
utility-dir=org/DataCom/Utility
server-dir=org/DataCom/Server
testing-dir=org/DataCom/Testing
server-exec=$(server-dir)/UDPFileTransferServer.class
server-jar=UFTServer.jar
client-exec=$(client-dir)/UDPFileTransferClient.class
client-jar=UFTClient.jar



default: server-files


## #################################################################
## Clean
## #################################################################
clean:
	rm -f $(server-dir)/*.class $(client-dir)/*.class $(utility-dir)/*.class $(client-jar) $(server-jar)


## #################################################################
## Server
## #################################################################
server-files: $(server-exec)


$(server-exec): $(server-dir)/*.java $(utility-dir)/*.java
	javac $^


$(server-jar): server-files
	jar cfe $(server-jar) org.DataCom.Server.UDPFileTransferServer *

server: $(server-jar)


## #################################################################
## Utility
## #################################################################
utility: $(utility-dir)/*.java
	javac $^


## #################################################################
## Tests
## #################################################################
tests: server-files client-files $(testing-dir)/*.java
	javac $(testing-dir)/*.java


## #################################################################
## Client
## #################################################################
client-files: $(client-exec)

$(client-exec): $(client-dir)/*.java $(utility-dir)/*.java
	javac $^

$(client-jar): client-files
	jar cfe $(client-jar) org.DataCom.Client.UDPFileTransferClient *

client: $(client-jar)

## #################################################################
## Build the whole project
## #################################################################
project: clean server-files server client-files client
