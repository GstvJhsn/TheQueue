/**
 * This class represents the student. 
 * @author gustavjohansson
 *
 */

class Student {
    constructor(name, ticket, address, lastResponse) {
        this.name = name;
        this.ticket = ticket;
        this.address = address;
        this.lastResponse = new Date();
    }
}

module.exports = Student;