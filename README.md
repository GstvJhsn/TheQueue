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

## The Server

### JavaScript
	
	Node.js
	16.17.1
		
	Visual Studio Code
	1.71.2

## Freelance Pattern

We choose the "Brutal Shotgun Massacre" pattern. This basically means that the clients will send every message as many times as there are servers. In our case these servers are set as command line arguments, and can therefore be easily changed to your liking. ZeroMQ will then send these messages, one to each server. If one server is down, another one can receive multiple messages. Finally ZeroMQ will then send all the replies back to the client.

In our solution we use this method to achieve some reliability in terms of redundancy; hence, if one server goes down we can still maintain the flow of information. The great drawback here is that we do not sync the servers in any way. We just hope that all these messages (1) will arrive (2) in the correct order, and then update these queues accordingly. To really be sure that all clients would receive the right information at all times, some syncing between servers would have to occur. In the end we only want one queue even if we are asking multiple servers to help us.
