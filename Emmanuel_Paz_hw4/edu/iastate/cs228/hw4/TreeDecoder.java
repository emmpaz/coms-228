package edu.iastate.cs228.hw4;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * This is just the interface for asking user input
 */
public class TreeDecoder {
    /**
     * the main method for interface
     * @param args
     */
    public static void main(String[] args) {

        System.out.println("Please enter filename to decode: ");
        try{
            Scanner scan = new Scanner(System.in);
            File file = new File(scan.next());
            Scanner howManyLines = new Scanner(file);
            int howMany = 0;
            while(howManyLines.hasNextLine()){
                String temp = howManyLines.nextLine();
                howMany++;
            }
            Scanner scanFile = new Scanner(file);
            if(howMany > 2){
                String msgTreePart1 = scanFile.nextLine() + "\n";
                String msgTreePart2 = scanFile.nextLine();
                String totalMessage = msgTreePart1 + msgTreePart2;
                double chars = 0;
                for(int i = 0; i < totalMessage.length(); i++){
                    if(totalMessage.charAt(i) != '^')
                        chars++;
                }
                MsgTree tree = new MsgTree(totalMessage);
                String code = scanFile.nextLine();
                System.out.println();
                System.out.println("character  code");
                System.out.println("---------------------");
                MsgTree.printCodes(tree, "");
                System.out.println();
                System.out.println("MESSAGE:");
                tree.decode(tree, code);
                System.out.println();
                System.out.println();
                System.out.println("STATISTICS:");
                System.out.println("Avg bits/char:      " + String.format("%.1f", code.length()/chars));
                System.out.println("Total characters:   " + (int) chars);
                System.out.println("Space savings:      " + String.format("%.1f", (1-(code.length()/(16*chars)))*100)+"%");
            }
            else {
                String msgTree = scanFile.nextLine();
                double chars = 0;
                for(int i = 0; i < msgTree.length(); i++){
                    if(msgTree.charAt(i) != '^')
                        chars++;
                }
                MsgTree tree = new MsgTree(msgTree);
                String code = scanFile.next();
                System.out.println();
                System.out.println("character  code");
                System.out.println("---------------------");
                MsgTree.printCodes(tree, "");
                System.out.println();
                System.out.println("MESSAGE:");
                tree.decode(tree, code);
                System.out.println();
                System.out.println();
                System.out.println("STATISTICS:");
                System.out.println("Avg bits/char:      " + String.format("%.1f", code.length()/chars));
                System.out.println("Total characters:   " + (int) chars);
                System.out.println("Space savings:      " + String.format("%.1f", (1-(code.length()/(16*chars)))*100)+"%");
            }
            scan.close();
            scanFile.close();
        }catch(FileNotFoundException e){
            System.out.println("File was not found");
        }
    }

}
