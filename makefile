client-dir=org/DataCom/Client
utility-dir=org/DataCom/Utility
server-dir=org/DataCom/Server
testing-dir=org/DataCom/Testing
server-exec=$(server-dir)/UDPFileTransferServer.class
client-exec=$(client-dir)/UDPFileTransferClient.class

default: server

clean:
	rm -f $(server-dir)/*.class $(client-dir)/*.class $(utility-dir)/*.class


server: $(server-exec)

$(server-exec): $(server-dir)/*.java $(utility-dir)/*.java
	javac $^


tests: server client $(testing-dir)/*.java
	javac $(testing-dir)/*.java


client: $(client-exec)

$(client-exec): $(client-dir)/*.java $(utility-dir)/*.java
	javac $^



