# Assignment 11
Create server and client programs that allows any kind of file to be uploaded and downloaded to the server.

* The server should be multi-threaded, and have one thread per connection.  (12 marks)
* Include appropriate output to the console window, e.g. "client connected" (1 mark)
* The client should have a simple GUI that allows the user to specify whether to upload or download a file, as well as the file name (or file directory).
* Include a pdf file showing the operation of the GUI (1 mark)
* Comment your code appropriately, including your details (name, ID number) in each java file.  Submit your assignment in the form Assignment11_firstname_lastname.zip (1 mark)

15 marks are awarded for this assignment, instead of the normal 10.

__Note that due to the two-week timeframe, this assignment is notably longer than the other assignments - it is strongly recommended that you do not leave it to the last week. Also that an IP address may be used instead of a host name (and 127.0.0.1 is to connect to the same computer), and remember that FileInputStream and FileOutputStream are used to convert bytes into a file.__

## Protocol
To design the client-server conversation, we need some sort of protocol. I don't plan on implementing HTTP/1.1 or anything of the sort, only a protocol which meets the requirements of uploading a file, list the file directory and downloading a file or directory (Hi FTP). This protocol will consist of a single command header followed by the required arguments (broken up by spaces).

#### Input
To run a command `[command] [..args] >>>[bytes]`.

#### Output
Command was ok: `OK`
Command failed: `ERROR [message]`
File list: `FILE [filename]; DIR [dirname];`. Separated by semicolon. Use `CHILD` keyword before `DIR|FILE` to make child of preceding file entry.

### Commands
* `UPLOAD [filename] >>>[bytes]` - Upload a file to the server.
* `LIST` - Returns the file list from the server.
* `DOWNLOAD [filename]` - Returns the file.