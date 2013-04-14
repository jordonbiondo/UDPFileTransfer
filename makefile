client-dir=org/DataCom/Client
utility-dir=org/DataCom/Utility
server-dir=org/DataCom/Server
testing-dir=org/DataCom/Testing
server-exec=$(server-dir)/UDPFileTransferServer.class
client-exec=$(client-dir)/UDPFileTransferClient.class


default: server


## #################################################################
## Clean
## #################################################################
clean:
	rm -f $(server-dir)/*.class $(client-dir)/*.class $(utility-dir)/*.class


## #################################################################
## Server
## #################################################################
server: $(server-exec)


$(server-exec): $(server-dir)/*.java $(utility-dir)/*.java
	javac $^


## #################################################################
## Utility
## #################################################################
utility: $(utility-dir)/*.java
	javac $^


## #################################################################
## Tests
## #################################################################
tests: server client $(testing-dir)/*.java
	javac $(testing-dir)/*.java


## #################################################################
## Client
## #################################################################
client: $(client-exec)


$(client-exec): $(client-dir)/*.java $(utility-dir)/*.java
	javac $^
