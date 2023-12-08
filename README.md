# Programming Assignment 3: Huffman

**Assignment Spec:**

**Creating a Huffman Code**
You will write methods to (1) create a Huffman code from an array of frequencies and (2) write out the code you’ve created in standard format.

Frequency Array Constructor
You should use the algorithm described in the “Building a Huffman Code” slide to implement this constructor. You will need to use PriorityQueue<E> in the java.util package. 

The only difference between a priority queue and a standard queue is that it uses the natural ordering of the objects to decide which object to dequeue first, with objects considered “less” returned first. You will be putting subtrees into your priority queue, which means you’ll be adding values of type HuffmanNode. 

This means that your HuffmanNode class will have to implement the Comparable<E> interface. It should use the frequency of the subtree to determine its ordering relative to other subtrees, with lower frequencies considered “less” than higher frequencies. If two frequencies are equal, the nodes are too. 

Remember that, in order to make our code more flexible we should be declaring variables with their interface types when possible. This means you should define your PriorityQueue variables with the Queue interface. 

The Huffman solution is not unique. You can obtain any one of several different equivalent trees depending upon how certain decisions are made. If you implement it as we have specified, then you should get exactly the same tree for any particular implementation of PriorityQueue. Make sure that you use the built-in PriorityQueue class and that when you are combining pairs of values taken from the priority queue, you make the first value removed from the queue the left subtree and you make the second value removed the right subtree.

Decompressing A Message
You will write methods to (1) read in a .code file created with save() and (2) translate a compressed file back into a decompressed file.

Scanner Constructor
This constructor will be given a Scanner that contains data produced by save(). In other words, the input for this constructor is the output you produced into a .code file. The goal of this constructor is to re-create the Huffman tree from your output. Note that the frequencies are irrelevant for this part, because the tree has already been constructed; so, you should set all the frequencies to some standard value (such as 0 or -1) when creating HuffmanNodes in this constructor.

Remember that the standard code file format is a series of pairs of lines where the first line has an integer representing the character’s ASCII value and the second line has the code to use for that character. You might be tempted to call nextInt() to read the integer and nextLine() to read the code, but remember that mixing token-based reading and line-based reading is not simple. Here is an alternative that uses a method called parseInt in the Integer class that allows you to use two successive calls on nextLine():

int asciiValue = Integer.parseInt(input.nextLine());
String code = input.nextLine();
Take a look at the Huffman Encoding in-class slides for a walk-through of this algorithm (particularly starting at page 55).

translate
This method takes in a BitInputStream representing a previously compressed message and outputs the original decompressed message. BitInputStream can be used in a very similar way to a Scanner; see the Specification slide.

This method reads sequences of bits that represent encoded characters to figure out what the original characters must have been. The algorithm works as follows:

Begin at the top of the tree

For each bit read in from the BitInputStream, if it’s a 0, go left, and if it’s a 1, go right.

Eventually, we will hit a leaf node. Once we do, write out the integer code for that character to the output using the following PrintStream method:

public void write(int b)
Then, go back to the top of the tree, and do the process over again.

You will have to be careful if you use recursion in your decode method. Java has a limit on the stack depth you can use. For a large message, there are hundreds of thousands of characters to decode. It would not be appropriate to write code that requires a stack that is hundreds of thousands of levels deep. You might be forced to write some or all of this using loops to make sure that you don’t exceed the stack depth.
