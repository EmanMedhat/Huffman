package huffman;


import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

// A Tree node
class Huffman_Node
{
    Character charac;
    Integer frequency;
    Huffman_Node left = null, right = null;

    Huffman_Node(Character c, Integer f)
    {
        this.charac = c;
        this.frequency = f;
    }

    public Huffman_Node(Character c, Integer f, Huffman_Node l, Huffman_Node r)
    {
        this.charac = c;
        this.frequency = f;
        this.left = l;
        this.right = r;
    }
}