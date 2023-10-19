/**
 * This class represents the supervisor. 
 * @author gustavjohansson
 *
 */

class Supervisor {
    constructor(name, status, client, clientMessage, address, lastResponse) {
        this.name = name;
        this.status = status;
        this.client = client;
        this.clientMessage = clientMessage;
        this.address = address;
        this.lastResponse = lastResponse;

    }
}
module.exports = Supervisor;