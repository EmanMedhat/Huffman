package huffman;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.io.File;  // Import the File class
import java.io.IOException;  // Import the IOException class to handle errors

class Huffman
{
    // Huffman Tree Traversing and storing the Huffman Codes in a dictionary.
    public static void encode(Huffman_Node rootNode, String str,
                                      Map<Character, String> huffmanCode)
    {
        if (rootNode == null) {
            return;
        }

        // if the root node is a leaf node
        if (is_Leaf(rootNode)) {
            huffmanCode.put(rootNode.charac, str.length() > 0 ? str : "1");
        }

        encode(rootNode.left, str + '0', huffmanCode);
        encode(rootNode.right, str + '1', huffmanCode);
    }

    // Huffman Tree Traversing and decoding the encoded string
    public static int decode(Huffman_Node rootNode, int index, StringBuilder sb)
    {
        if (rootNode == null) {
            return index;
        }

        // if the root node is a leaf node
        if (is_Leaf(rootNode))
        {
            System.out.print(rootNode.charac);
            return index;
        }

        index++;

        rootNode = (sb.charAt(index) == '0') ? rootNode.left : rootNode.right;
        index = decode(rootNode, index, sb);
        return index;
    }

    // This function checks if Huffman Tree contains only one single node
    public static boolean is_Leaf(Huffman_Node rootNode) {
        return rootNode.left == null && rootNode.right == null;
    }

    // Main Huffman tree build function
    public static void HuffmanTree(String text)
    {
        // Base case: empty string
        if (text == null || text.length() == 0)
        {
            return;
        }

        // Calculate the frequency of each character and store it in a map of dict

        Map<Character, Integer> frequency = new HashMap<>();
        for (char c: text.toCharArray())
        {
            frequency.put(c, frequency.getOrDefault(c, 0) + 1);
        }

        // priority queue to store nodes of the Huffman tree
        // the highest priority item has the lowest frequency

        PriorityQueue<Huffman_Node> prio_queue;
        prio_queue = new PriorityQueue<>(Comparator.comparingInt(l -> l.frequency));

        // leaf node for each character, adding it to the priority queue.

        for (var entry: frequency.entrySet()) {
            prio_queue.add(new Huffman_Node(entry.getKey(), entry.getValue()));
        }

        //repeat the process till there is more than one node in the queue
        while (prio_queue.size() != 1)
        {
            // Then remove the two nodes with the highest priority and lowest frequency

            Huffman_Node left = prio_queue.poll();
            Huffman_Node right = prio_queue.poll();

            // Now create a new internal node with two children nodes, and the frequency will be the some of both nodes; add the new node to the priority queue.
            int sum = left.frequency + right.frequency;
            prio_queue.add(new Huffman_Node(null, sum, left, right));
        }

        Huffman_Node rootNode = prio_queue.peek();

        // Huffman tree Traversing and storing the Huffman codes in a dict or map
        Map<Character, String> huffmanCode = new HashMap<>();
        encode(rootNode, "", huffmanCode);

        // Display the Huffman codes
        System.out.println("The Huffman Codes for the given text are: " + huffmanCode);
        System.out.println("The original text is: " + text);

        // display the encoded string
        StringBuilder sb = new StringBuilder();
        for (char c: text.toCharArray()) {
            sb.append(huffmanCode.get(c));
        }
        StringBuilder size = sb;
        System.out.println("The encoded text is: " + sb);
        System.out.print("The decoded text is: ");

        if (is_Leaf(rootNode))
        {
            // For input like a, aa, aaa, etc.
            while (rootNode.frequency-- > 0)
            {
                System.out.print(rootNode.charac);
            }
        }
        else
        {
            // Huffman Tree traversing with decoding the encoded string
            int index = -1;
            while (index < sb.length() - 1)
            {
                index = decode(rootNode, index, sb);
            }
        }
        //calc size
        System.out.println("\n");
        System.out.println("The Original size is "+ text.length()*8);
        System.out.println("The Compressed size is "+sb.length());
    }

    // Call the Huffman code and create huffman file
    public static void main(String[] args)
    {
        String text = "aabacc";
        HuffmanTree(text);
        System.out.print("\n");
        
        try {
      File myObj = new File("Huffman.txt");
      if (myObj.createNewFile()) {
        System.out.println("File created: " + myObj.getName());
      } else {
        System.out.println("File already exists.");
      }
    } catch (IOException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }
        
}
}