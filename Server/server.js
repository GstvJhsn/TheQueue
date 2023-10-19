/**
 * This module represents the server
 * Enter a port number and server name when as startup arguments
 * etc. node index.js 1337 coolserver 
 * @author gustavjohansson
 *
 */
const zmq = require('zeromq');
const Student = require('./Student');
const Supervisor = require('./Supervisor');

//  queueIndex represents the position in the queue array.
var queueIndex = 0;
//  Arrays for holding student objects.
let students = [];
let teachers = [];
let subscribers = [];
//  Arrays for holding JSON strings.
let supervisors = [];
let queue = [];

//  port represents the port number and address the server is connected to. 
//  The port number is set buy startup argument 
var port = "tcp://127.0.0.1:" + process.argv[2];
var serverId = '"serverId":"' + process.argv[3] + '"';
var sId = process.argv[3];

//  Calls the mighty run function
run(port, serverId);

//  Sends the vital information to the client
function sendArrays(address, sock) {
    sock.send([address, '{"queue":[' + queue + '],' + '"supervisors":[' + supervisors + '],' + serverId + '}']);
}

//  Sends heartbeat to client
function sendHeartbeat(sock, addresses) {
    for (var i = 0; i < addresses.length; i++) {
        var adr = addresses[i].address;
        sock.send([adr, '{}']);
    }
}

// Adds the a JSON string to queue && creates and adds the student to the student array.
function enterQueue(obj, address, sock) {
    if (obj.enterQueue == true) {
        queueIndex = queueIndex + 1;
        var student = new Student(obj.name, queueIndex, address, new Date());
        students.push(student);
        var ticket = '{"name":"' + student.name + '","ticket":' + student.ticket + '}';
        queue.push(ticket);
        sendAll(sock, subscribers);
    }

}

// Adds the a JSON string to supervisors && creates and adds the supervisor to the supervisors array.
function enterSupervisor(obj, address, sock) {
    if (obj.enterSupervisor == true) {
        var supervisor = new Supervisor(obj.name, '"available"', null, '""', address, new Date());
        teachers.push(supervisor);
        var ticket = '{' + '"name":"' + supervisor.name + '","status":' + supervisor.status + ',"client":' + supervisor.client + ',"clientMessage":' + supervisor.clientMeassage + '}';
        supervisors.push(ticket);
        sendAll(sock, subscribers);
    }

}

// Removes the first student from queue and students && sets the supervisor status to occupied and && the student ticket to the supervisor
function helpNext(obj, address, sock) {
    if (obj.helpNext == true) {
        var client = queue[0];
        queue.shift();
        for (var i = 0; i < supervisors.length; i++) {
            if (supervisors[i].includes(obj.name)) {
                var supervisor = new Supervisor(obj.name, '"occupied"', client, obj.clientMessage, address, new Date());
                teachers[i] = supervisor;
                var ticket = '{' + '"name":"' + teachers[i].name + '","status":' + teachers[i].status + ',"client":' + teachers[i].client + ',"clientMessage":' + teachers[i].clientMessage + '}';
                supervisors[i] = ticket;
            }
        }
        sendAll(sock, subscribers);
    }
}

// Checks if the student already exists, if not calls: enterQueue(obj, address, sock);
function studentCheck(obj, address, sock) {
    var notInList = true;
    for (var i = 0; i < students.length; i++) {
        if (students[i].name == obj.name) {
            notInList = false;
        }
    }

    if (notInList == true) {
        enterQueue(obj, address, sock);
    }
}

// Checks if the supervisor already exists, if not calls: enterSupervisor(obj, address, sock)
function supervisorCheck(obj, address, sock) {
    var notInList = true;
    for (var i = 0; i < supervisors.length; i++) {
        if (teachers[i].name == obj.name) {
            notInList = false;
        }
    }

    if (notInList == true) {
        enterSupervisor(obj, address, sock);
    }
}

// Updates the lastReponse of the supervisor or student in the the arrays.
function updateLastResponse(adr) {
    for (var i = 0; i < students.length; i++) {
        if (String(students[i].address) == String(adr)) {
            var student = new Student(students[i].name, students[i].ticket, students[i].address, new Date());
            students[i] = student;
        }
    }
    for (var i = 0; i < teachers.length; i++) {
        if (String(teachers[i].address) == String(adr)) {
            var supervisor = new Supervisor(teachers[i].name, teachers[i].status, teachers[i].client, teachers[i].clientMessage, teachers[i].address, new Date());
            teachers[i] = supervisor;
        }
    }
}

// Compares the time of the last respons with current time. 
// If more than t seconds have passed the student/supervisor is removed.
function compareLastResponse(t, sock) {
    for (var i = 0; i < students.length; i++) {
        var curdate = new Date();
        var dif = parseInt((curdate - students[i].lastResponse) / 1000);
        if (dif > t) {
            console.log('\x1b[31m%s\x1b[0m', "Client did not respond in " + dif + " seconds. Removing student: " + students[i].name);
            queue.splice(i, 1);
            students.splice(i, 1);
            sendAll(sock, subscribers);
        }
    }

    for (var i = 0; i < teachers.length; i++) {
        var curdate = new Date();
        var dif = parseInt((curdate - teachers[i].lastResponse) / 1000);
        if (dif > t) {
            console.log('\x1b[31m%s\x1b[0m', "Client did not respond in " + dif + " seconds. Removing supervisor: " + teachers[i].name);
            supervisors.splice(i, 1);
            teachers.splice(i, 1);
            sendAll(sock, subscribers);
        }
    }
}

//  Sends arrays to all clients.
function sendAll(sock, list) {
    for (var i = 0; i < list.length; i++) {
        sock.send([list[i], '{"queue":[' + queue + '],' + '"supervisors":[' + supervisors + '],' + serverId + '}']);
    }
}

// The function that runs the server. Listening to messages and sending heartbeats.
// Depending on the recieved messages different functions are called.
async function run(url, serverId) {
    const sock = new zmq.Router();
    await sock.bind(url);
    //                                           ↓ = t = seconds allowed without response from clients 
    setInterval(() => { compareLastResponse(15, sock) }, 2000);
    setInterval(() => { sendHeartbeat(sock, students) }, 2000);
    setInterval(() => { sendHeartbeat(sock, teachers) }, 2000);

    console.log('\x1b[36m%s\x1b[0m', sId + " is running...");

    for await (const msg of sock) {
        var message;
        message = msg.pop();
        address = msg.pop();
        var obj = JSON.parse(message);

        if (String(message) != '{}') {
            console.log('\x1b[32m%s\x1b[0m', "Recieved message : " + String(message));
        }

        if (obj.hasOwnProperty('subscribe')) {
            subscribers.push(address);
            sendArrays(address, sock);
        }

        if (obj.hasOwnProperty('enterQueue' && 'name')) {
            studentCheck(obj, address, sock);
        }
        if (obj.hasOwnProperty('enterSupervisor' && 'name')) {
            supervisorCheck(obj, address, sock);
        }
        if (obj.hasOwnProperty('helpNext' && 'name' && 'clientMessage')) {
            helpNext(obj, address, sock);
        }

        if (String(message) == "{}") {
            updateLastResponse(address);
        }
    }
}