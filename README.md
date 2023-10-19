# The Queue
Assignment 1 in the Course Distributed Systems @ Högskolan i Skövde 03-10-2022

Jacob Milton  & Gustav Johansson 

### Guide
In this folder you have the Client and the Server. Read ahead to see what programs and procedures are needed to use them.

## The Clients

### Java

	Eclipse IDE for Java Developers (includes Incubating components)
	
	Version: 2021-12 (4.22.0)
	Build id: 20211202-1639
	
	JDK: 17
	
### Dependencies

	org.zeromq
	jeromq
	0.5.2

	org.json
	json
	20180130

### Guide

The easiest way is just to download Java and open the "Client.jar", to start the client side of the program. Make sure that you are in the same folder as the file. Initiate several instances to simulate students and supervisors. The application requires argruments in form of sockets. Type like this:

	java -jar Client.jar tcp://localhost:7000 tcp://localhost:7001

This is an example of a connection with two servers that runs on your machine (you will learn how to start servers further down under JavaScript). The two tcp adresses are of course an example.

Read further if you wish to use Eclipse, but keep in mind that you are limited to one instance of the program using this method.

After installing Eclipse and the JDK mentioned above, you start Eclipse and set Client as your workspace. Once in you should import a Maven project and pick the Client folder again. The dependencies should already be stated in your pom.xml file, located under source -> target.

Now you might have to right click on your folder and go to properties -> Java Compiler. You may change your "Compiler compliance level" to 17 if it is not already done so.

A tip, if it still messes with you, would be to rebuild the folder stack which is done under "Java Build Path". This is quite common so you can "google it" if you are clueless about this.

## The Server

### JavaScript
	
	Node.js
	16.17.1
		
	Visual Studio Code
	1.71.2

### Guide

After installing Node and Visual Studio Code, you can open the terminal and go to the Server folder. Here you can type:

	npm install

, to get everything you need. After this you may enter the following to start a server:

	node index.js 7000 server1

The port number should match the one choosen at the client side and "server1" is just an arbitrary name of your liking.

## Freelance Pattern

We choose the "Brutal Shotgun Massacre" pattern. This basically means that the clients will send every message as many times as there are servers. In our case these servers are set as command line arguments, and can therefore be easily changed to your liking. ZeroMQ will then send these messages, one to each server. If one server is down, another one can receive multiple messages. Finally ZeroMQ will then send all the replies back to the client.

In our solution we use this method to achieve some reliability in terms of redundancy; hence, if one server goes down we can still maintain the flow of information. The great drawback here is that we do not sync the servers in any way. We just hope that all these messages (1) will arrive (2) in the correct order, and then update these queues accordingly. To really be sure that all clients would receive the right information at all times, some syncing between servers would have to occur. In the end we only want one queue even if we are asking multiple servers to help us.
