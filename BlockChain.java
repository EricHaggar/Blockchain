import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileWriter;
import java.io.PrintWriter;

public class BlockChain {

  private static ArrayList<Block> blocks;

  public BlockChain() {

    blocks = new ArrayList<Block>();

  }

  public static BlockChain fromFile(String fileName) {

    BlockChain blockChain = new BlockChain();

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

      while ((line = bufferedReader.readLine()) != null) {

        index = Integer.parseInt(line);
        if (index == 0) {
          previousHash = "00000";
        } else {
          previousHash = blockChain.blocks.get(index-1).getHash();
        }
        timestamp = new java.sql.Timestamp(Long.valueOf(bufferedReader.readLine()));
        sender = bufferedReader.readLine();
        receiver = bufferedReader.readLine();
        amount = Integer.parseInt(bufferedReader.readLine());
        nonce = bufferedReader.readLine();
        hash = bufferedReader.readLine();

        blockChain.blocks.add(new Block(index, timestamp, sender, receiver, amount, nonce, previousHash, hash));
      }

      bufferedReader.close();

    } catch (IOException e) {
      System.out.print("Error while reading the file!");
    }

 /*   for (int i = 0; i < blockChain.blocks.size(); i++) {
			System.out.println(blockChain.blocks.get(i));
		}*/

    return blockChain;

  }


  public void toFile(String fileName) {

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
  			printWriter.println(blocks.get(i).getPreviousHash());
  		}

  		printWriter.close();


  	} catch (IOException e) {

  		System.out.println("Error while writing to the file!");
  	}

   }

  public static void main(String[] args) {

    BlockChain blockChain = fromFile("bitcoinBank.txt");

    blockChain.toFile("output.txt");


  }
}
