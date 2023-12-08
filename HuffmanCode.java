// Jeffery Zhang
// TA: Jake Page
// CSE 123
// Due: December 6th, 2023
// P3: Huffman

// A class to represent the Huffman encoding framework, with 
// functionalities including losslessly compressing messages 
// and outputting the framework in a standardized format.

import java.util.*;
import java.io.*;

public class HuffmanCode {
    private HuffmanNode root;

    // Initializes a new Huffman framework given an int[] of freqs.
    // of ASCII codes from a given message (B, P).
    public HuffmanCode(int[] frequencies) {
        Queue<HuffmanNode> pq = new PriorityQueue<>();  
        for (int i = 0; i < frequencies.length; i++) {
            if (frequencies[i] > 0) {
                pq.add(new HuffmanNode(i, frequencies[i]));
            }
        }
        root = buildHuffmanTree(pq);
    }

    // Initializes a new Huffman framework given a Scanner input
    // of a Huffman framework blueprint from a .code file (B, P).
    public HuffmanCode(Scanner input) {
        root = new HuffmanNode();
        while (input.hasNextLine()) {
            int asciiCode = Integer.parseInt(input.nextLine());
            addToHuffmanTree(input.nextLine(), asciiCode);
        }
    }
    
    // Creates a Huffman Tree given a Queue<HuffmanNode> of
    // ASCII values and prioritized by their frequencies in
    // a message (B, P). Returns the front of the resulting queue (R).
    private HuffmanNode buildHuffmanTree(Queue<HuffmanNode> pq) {
        while (pq.size() > 1) {
            HuffmanNode left = pq.poll();
            HuffmanNode right = pq.poll();
            HuffmanNode parentNode = new HuffmanNode(-1, left.freq + right.freq);
            parentNode.left = left;
            parentNode.right = right;
            pq.add(parentNode);
        }
        return pq.poll();
    }

    // Adds a new node to an existing Huffman Tree, given a
    // String Huffman code that encodes its position in the
    // tree and an int ASCII code of its character (B, P).
    private void addToHuffmanTree(String huffmanCode, int asciiCode) {
        HuffmanNode curr = root;
        for (int i = 0; i < huffmanCode.length(); i++) {
            char bit = huffmanCode.charAt(i);
            if (bit == '0' && curr.left == null || bit == '1' && curr.right == null) {
                curr = (bit == '0') ? (curr.left = new HuffmanNode())
                                      : (curr.right = new HuffmanNode());
            } else {
                curr = (bit == '0') ? curr.left : curr.right;
            }
        }
        curr.asciiCode = asciiCode;
    }

    // Stores the Huffman code to the given output stream
    // in standardized format (B). Takes in an PrintStream 
    // input stream (P).
    public void save(PrintStream input) {
        saveHelper(root, "", input);
    }

    // Stores the Huffman code to the given output stream
    // in standardized format (B). Takes in a PrintStream input 
    // stream, the encoded String of binary values, and the 
    // current HuffmanNode to be analyzed (P).
    private void saveHelper(HuffmanNode curr, String encoded, PrintStream output) {
        if (curr.left == null && curr.right == null) {
            output.println(curr.asciiCode);
            output.println(encoded);
        } else {
            saveHelper(curr.left, encoded + "0", output);
            saveHelper(curr.right, encoded + "1", output);
        }
    }

    // Reads individual bits from the input stream and writes
    // the corresponding characters to the output stream (B). Takes
    // in both the BitInputStream input and the PrintStream output (P).
    public void translate(BitInputStream input, PrintStream output) {
        HuffmanNode curr = root;
        while (input.hasNextBit()) {
            int bit = input.nextBit();
            curr = (bit == 0) ? curr.left : curr.right;
            if (curr.left == null && curr.right == null) {
                output.write((char) curr.asciiCode);
                curr = root;
            }
        }
    }

    // A class to represent a node in the Huffman tree.
    private static class HuffmanNode implements Comparable<HuffmanNode> {
        private int asciiCode;
        private int freq;
        private HuffmanNode left;
        private HuffmanNode right;

        // A HuffmanNode constructor to represent a 
        // default node, without meaningful data (B).
        public HuffmanNode() {
            this.asciiCode = -1;
        }

        // A HuffmanNode constructor to respresent a node with a ASCII code 
        // and the frequency of the ASCII code within the file (B). Takes in 
        // both the int ASCII Code and int frequency as parameters (P).
        public HuffmanNode(int asciiCode, int freq) {
            this.asciiCode = asciiCode;
            this.freq = freq;
        }

        // A compareTo method, comparing the frequencies of this HuffmanNode's 
        // ASCII codes with other nodes (B). Takes in the other HuffmanNode as 
        // a parameter (P). Returns the diff between this frequency and the other 
        // HuffmanNode's frequency.
        @Override
        public int compareTo(HuffmanNode other) {
            return this.freq - other.freq;
        }
    }
}

