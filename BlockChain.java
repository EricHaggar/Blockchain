import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Random;
import java.util.Scanner;
import java.sql.Timestamp;
import java.lang.System;
import java.io.File;

public class BlockChain {

  //defining the blockchain that will store the blocks
  private ArrayList<Block> blocks;

  /*
  initializing the blockchain arraylist
  */
  public BlockChain() {
    blocks = new ArrayList<Block>();
  }

  /*
  this method reads the contents of the text file fileName and
  creates blocks from the information stored in the text file

  returns an instance BlockChain containing the blocks
  */
  public static BlockChain fromFile(String fileName) {

    //creates an instance of BlockChain
    BlockChain blockchain = new BlockChain();


    //using FileReader and BufferedReader, information is stored in blocks
    try {

      FileReader fileReader = new FileReader(fileName);
      BufferedReader bufferedReader = new BufferedReader(fileReader);

      String line;
      int index;
      java.sql.Timestamp timestamp;
      String sender;
      String receiver;
      int amount;
      String nonce;
      String previousHash;
      String hash;
      int numOfBlocks = 0;

      //while there is still more lines in the text file, it will read and create
      //blocks from this information
      while ((line = bufferedReader.readLine()) != null) {

        index = Integer.parseInt(line);

        //if the first block is being initialized, the previousHash will be
        //set to 00000.
        if (numOfBlocks == 0) {
          previousHash = "00000";
          //if any other block is being initialized, the previousHash will be taken
          //from the last block added to the blockchain
        } else {
          previousHash = blockchain.blocks.get(numOfBlocks - 1).getHash();
        }
        timestamp = new java.sql.Timestamp(Long.valueOf(bufferedReader.readLine()));
        sender = bufferedReader.readLine();
        receiver = bufferedReader.readLine();
        amount = Integer.parseInt(bufferedReader.readLine());
        nonce = bufferedReader.readLine();
        hash = bufferedReader.readLine();

        //based on the information gathered from the text file, the block can be created
        blockchain.blocks.add(new Block(index, timestamp, new Transaction(sender,receiver,amount), nonce, previousHash, hash));
        numOfBlocks++;
      }

      bufferedReader.close();

    } catch (IOException e) {
      System.out.print("Error while reading the file!");
    }

    return blockchain;

  }

  /*
  this method outputs the contents of the blockchain and stores
  the blocks in the fileName.txt text file.
  */
  public void toFile(String fileName) {

    //using FileWriter and PrintWriter, the information from the blocks
    //in the blockchain can be printed to the text file

    try {

      FileWriter fileReader = new FileWriter(fileName);
      PrintWriter printWriter = new PrintWriter(fileReader);

      for (int i = 0; i < blocks.size(); i++) {

        printWriter.println(blocks.get(i).getIndex());
        printWriter.println(blocks.get(i).getTimestamp().getTime());
        printWriter.println(blocks.get(i).getTransaction().getSender());
        printWriter.println(blocks.get(i).getTransaction().getReceiver());
        printWriter.println(blocks.get(i).getTransaction().getAmount());
        printWriter.println(blocks.get(i).getNonce());
        printWriter.println(blocks.get(i).getHash());
      }

      printWriter.close();


    } catch (IOException e) {
      System.out.println("Error while writing to the file!");
    }
  }

  /*
  this method validates the blockchain given in a text file.

  returns true if the blockchain is valid, and false otherwise.
  */
  public boolean validateBlockchain() {

    //This will allow to keep track of the miners in the blockchain and
    //to get their balance
    ArrayList<String> miners = new ArrayList<String>();

    //looping through all blocks in the blockchain
    for (int i = 0; i < blocks.size(); i++) {

      //checks if indexes are consistent
      if ((blocks.get(i).getIndex() != i)) {
        return false;
      }

      //calculates hash using Sha1 algorithm with attributes of the given block
      String currentVerifiedHash = generateHash(blocks.get(i));

      //checks if calculated hash is the same as the hash in the block
      if (!blocks.get(i).getHash().equals(currentVerifiedHash)) {
        return false;
      }

      //if we're in the first block
      if (i == 0) {

        //checks that the first sender is bitcoin
        if(!blocks.get(i).getTransaction().getSender().equals("bitcoin")) {
          return false;
        }

        //checks that the previousHash of the first block is 00000
        if (!blocks.get(i).getPreviousHash().equals("00000")) {
          return false;
        }
      } else { //if not the first blocks
        //checks that the previous hash of the next block is the same as the current block's hash
        if (!blocks.get(i).getPreviousHash().equals(blocks.get(i-1).getHash())) {
          return false;
        }
      }

      //checks to see if the senders are in the miners arraylist
      if (!miners.contains(blocks.get(i).getTransaction().getSender())) {
        //if they aren't in the miners arraylist, they are added
        miners.add(blocks.get(i).getTransaction().getSender());
      }

      //checks to see if the receivers are in the miners arraylist
      if (!miners.contains(blocks.get(i).getTransaction().getReceiver())) {
        //if they aren't in the miners arraylist, they are added
        miners.add(blocks.get(i).getTransaction().getReceiver());

      }


      //This will check the balances of the miners in the blockchain
      //Does not take in consideration the first miner since it's bitcoin
      for (int j = 1; j < miners.size(); j++) {

        if (getBalance(miners.get(j)) < 0) {
          return false;
        }
      }

    }

    //returns true if none of the failing criteria passed
    return true;

  }

  /*
  This method returns the username's balance

  It is used to verify the validity of a transaction
  */
  public int getBalance(String username) {

    //initializes the user's balance to 0
    int userBalance = 0;

    for (int i = 0; i < blocks.size(); i++) {

      //if the user is a receiver, the amount is added to their balance
      if (blocks.get(i).getTransaction().getReceiver().equals(username)) {

        userBalance += blocks.get(i).getTransaction().getAmount();

        //if the user is a sender, the amount is deducted from their balance
      } else if (blocks.get(i).getTransaction().getSender().equals(username)) {

        userBalance -= blocks.get(i).getTransaction().getAmount();
      }
    }

    return userBalance;
  }

  /*
  This method adds block to the blockchain
  */
  public void add(Block block) {

    //generates a nonce for this block before adding
    //it to the blockchain
    generateNonce(block);
    blocks.add(block);

  }

  /*
  This method takes a block and finds the hash corresponding
  to its toString method

  returns hash as a String
  */
  public String generateHash(Block block) {

    String hash = "";

    //calls Sha1 algorithm given and generates a 40 character hash code
    try {

      hash = Sha1.hash(block.toString());

    } catch (UnsupportedEncodingException e) {

      System.out.println("EncodingError");

    }

    return hash;

  }

  /*
  This method takes a block and finds the nonce that satisfies
  the condition of a hash starting with five 0s.

  returns nonce as a String
  */
  public void generateNonce(Block block) {


    int numOfTrials = 0; //keeps track of the number of trials to generate correct hash for each block
    boolean invalidNonce = true; //false when condition of hash code will be met
    String nonce = "";
    //nonce has to be an ASCII code of integers between 33 and 126 inclusively
    int min = 33;
    int max = 126;
    Random random = new Random();
    int nonceLength = 0;

    //while the condition for the hash code has not been met
    while (invalidNonce) {

      //generate a random integer for the nonce's length in range [3,7]
      nonceLength = random.nextInt(10) + 3;
      //create a character array of random ascii integers of the randomized nonceLength
      char[] ascii = new char[nonceLength];

      //populate the ascii character array
      for (int i = 0; i < nonceLength; i++) {

        int randomInt =  min + (int) (random.nextFloat() * (max - min + 1));
        ascii[i] = (char) randomInt;
      }

      //create String from random character array
      nonce = new String(ascii);

      //set the nonce of the block to the randomized string and generate a hash code from it
      block.setNonce(nonce);
      String hash = generateHash(block);
      numOfTrials++;

      //if the hash condition is met, the invalidNonce will be set to false and the loop will cease
      if (hash.substring(0,5).equals("00000")) {

        block.setHash(hash);
        invalidNonce = false;

      }

    }

    System.out.println("The hash was found in: " + Integer.toString(numOfTrials) + " trials");

  }

  /*
  Main method of the blockchain
  */
  public static void main(String[] args) {

    System.out.println();
    System.out.println("************************************************************");
    System.out.println("*           Welcome to the Blockchain simulator            *");
    System.out.println("*               Developped by: Eric Haggar                 *");
    System.out.println("************************************************************");
    System.out.println();



    Scanner reader = new Scanner(System.in);

    System.out.print("Please enter your username: ");
    String username = reader.nextLine().replaceAll("\\s","");
    
    //asks the user to input a text file name containing a blockchain
    System.out.print("Hi " + username + ", please enter the name of the blockchain file you'd like to validate without the .txt extension (ex: blockchain): ");
    String fileName = reader.nextLine().replaceAll("\\s","");

    //checks if the file exists in the path
    BlockChain blockchain = fromFile(fileName+".txt");
    File temp = new File(fileName+".txt");
    boolean exists = temp.exists();

    if (exists) {

      //if the blockchain read from the file is valid, will proceed
      if(blockchain.validateBlockchain()) {

        System.out.println("The entered blockchain is Valid!");

        while(true) {

          //asks the user to enter another transaction so long as the inputted transactions are valid
          System.out.print("Would you like to add another transaction? (yes/no) ");
          String add = reader.nextLine();

          if (add.toLowerCase().replaceAll("\\s","").equals("yes")) {

            //asks the user for a sender, a receiver and an amount
            System.out.println("Please enter a new transaction:");
            System.out.print("Sender: ");
            String sender = reader.nextLine().replaceAll("\\s","");
            System.out.print("Receiver: ");
            String receiver = reader.nextLine().replaceAll("\\s","");
            System.out.print("Amount: ");
            int amount = Integer.valueOf(reader.nextLine().replaceAll("\\s",""));

            //checks if the sender has enough bitcoins to send to the receiver
            if (blockchain.getBalance(sender) >= amount) {

              System.out.println("Valid Transaction! Generating Hash...");

              //once the transaction is validated, all block attributes are added except the nonce and hash
              int index = blockchain.blocks.size();
              Timestamp timestamp = new Timestamp(System.currentTimeMillis());
              Transaction transaction = new Transaction(sender, receiver, amount);
              String previousHash = blockchain.blocks.get(blockchain.blocks.size()-1).getHash();
              //uses second constructor of Block class
              Block block = new Block (index, timestamp, transaction, previousHash);
              //generates nonce and adds block to the blockchain
              blockchain.add(block);
            } else { //if sender does not have enough bitcoins, the transaction is invalid and program is terminated
              System.out.println("Invalid Transaction!");
              break;
            }
          } else { //if the user enters anything but yes, the blockchain is printed to a text file
            blockchain.toFile(fileName+"_" + username + ".txt");
            break;
          }

        }

      } else { //if the blockchain does not pass validation, the program terminates

        System.out.println("The entered blockchain is Invalid!");

      }

    } else { //if the file entered does not exist, the program terminates

      System.out.println("The entered file name does not exist!");
    }


  }

}
