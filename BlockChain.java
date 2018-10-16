import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class BlockChain {

  private ArrayList<Block> blockChain;

  public BlockChain() {

    blockChain = new ArrayList<Block>();

  }

  public static BlockChain fromFile(String fileName) {

    BlockChain blocks = new BlockChain();

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
          previousHash = blocks.blockChain.get(index-1).getHash();
        }
        timestamp = new java.sql.Timestamp(Long.valueOf(bufferedReader.readLine()));
        sender = bufferedReader.readLine();
        receiver = bufferedReader.readLine();
        amount = Integer.parseInt(bufferedReader.readLine());
        nonce = bufferedReader.readLine();
        hash = bufferedReader.readLine();

        blocks.blockChain.add(new Block(index, timestamp, sender, receiver, amount, nonce, previousHash, hash));
      }

      bufferedReader.close();

    } catch (IOException e) {
      System.out.print("Error while reading the file");
    }

    for (int i = 0; i < blocks.blockChain.size(); i++) {
			System.out.println(blocks.blockChain.get(i));
		}

    return blocks;

  }


  public void toFile(String fileName) {

    System.out.println("COMPLETE toFile METHOD!!");
  }

  public static void main(String[] args) {

    BlockChain blockchain = fromFile("bitcoinBank.txt");


  }
}
