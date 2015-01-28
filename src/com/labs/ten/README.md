# Assignment 10
### Part 1
Implement a FIFO queue up to some bounded maximum size to hold String objects that can be accessed by multiple Producer and Consumer threads. The Producer threads produce String objects while the Consumer threads consume these objects. Every String object produced must be consumed exactly once.  Use the ReentrantLock class and Condition variables in your implementation.
Write an application class that create a random number of Producer and Consumer threads at startup to test your implementation.
Print out queue statistics e.g. number of items in the queue and thread access details as the simulation progresses.

### Part 2
Modify the code in Part 1, so that there is no bounds on the size of the stack (LIFO).  Tip: use a List instead of arrays.
Submit your code in the format assignment10_fname_lname.zip. Also include your details (name, id etc) in the java files.
