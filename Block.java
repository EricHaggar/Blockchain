public class Block {

  private int index;                // the index of the block in the list
  private java.sql.Timestamp timestamp;  // time at which transaction
  private Transaction transaction;  // the transaction object
  private String nonce;             // random string (for proof of work)
  private String previousHash;   // previous hash (set to "" in first block)
  private String hash;  // hash of the block (hash of string obtained



  public String toString() {

    return timestamp.toString() + ":" + transaction.toString() + "." + nonce+ previousHash;

  }

}
