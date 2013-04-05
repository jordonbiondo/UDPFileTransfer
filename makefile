client-dir=org/DataCom/Client
utility-dir=org/DataCom/Utility
server-dir=org/DataCom/Server
server-exec=$(server-dir)/UDPFileTransferServer.class
client-exec=$(client-dir)/UDPFileTransferClient.class



clean:
	rm $(server-dir)/*.class $(client-dir)/*.class $(utility-dir)/*.class

default: $(server-exec)


server: $(server-exec)
$(server-exec): $(server-dir)/*.java $(utility-dir)/*.java
	javac $^



client: $(client-exec)

$(client-exec): $(client-dir)/*.java $(utility-dir)/*.java
	javac $^



