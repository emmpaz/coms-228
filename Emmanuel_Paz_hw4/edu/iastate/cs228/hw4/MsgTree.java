package edu.iastate.cs228.hw4;


/**
 * @author emmanuelpaz
 *
 * This class is the making of the tree and decoding
 */
public class MsgTree {
    /**
     * the character for the node
     */
    public char payloadChar;
    /**
     * the left side of tree
     */
    public MsgTree left;
    /**
     * the right side of tree
     */
    public MsgTree right;


    /**
     * Need static char idx to the tree string for recursive solution
     */
    private static int staticCharIdx = 0;
    /**
     * Constructor building the tree from a string
     * @param encodingString the string used to construct the tree
     */
    public MsgTree(String encodingString)
    {
        if(encodingString.charAt(staticCharIdx) == '^'){
            staticCharIdx++;
            this.left = new MsgTree(encodingString);
            this.right = new MsgTree(encodingString);
        }
        else
            payloadChar = encodingString.charAt(staticCharIdx++);
    }
    /**
     * Constructor for a single node with null children
     * @param payloadChar the character to use
     */
    public MsgTree(char payloadChar)
    {
        this.payloadChar = payloadChar;
        left = null;
        right = null;
    }

    /**
     * method to print characters and their binary codes
     * @param root the root of the tree
     * @param code the code that should be for the leafs
     */
    public static void printCodes(MsgTree root, String code)
    {
       if(root.left == null && root.right == null){
           if(root.payloadChar == 10)
                System.out.println("   "+ "\\n" + "      " + code);
           else if(root.payloadChar == 32)
                System.out.println(" "+ "space" + "      " + code);
           else
            System.out.println("   "+root.payloadChar + "      " + code);
       }
       else{
           printCodes(root.left, code+"0");
           printCodes(root.right, code+"1");
       }
    }

    /**
     * This method does the decoding and prints to console
     * @param codes the tree
     * @param msg the 1s and 0s to decode
     */
    public static void decode(MsgTree codes, String msg)
    {
        MsgTree tempTree = codes;
        int index = 0;
        while(index < msg.length())
        {
           while(tempTree.left != null && tempTree.right != null){
               char bit = msg.charAt(index++);
               if(bit == '0')
                   tempTree = tempTree.left;
               else
                   tempTree = tempTree.right;
           }
           System.out.print(tempTree.payloadChar);
           tempTree = codes;
        }
    }

}
