public class Block {

  private int index;                // the index of the block in the list
  private java.sql.Timestamp timestamp;  // time at which transaction
  private Transaction transaction;  // the transaction object
  private String nonce;             // random string (for proof of work)
  private String previousHash;   // previous hash (set to "" in first block)
  private String hash;  // hash of the block (hash of string obtained

  public Block(int index, java.sql.Timestamp timestamp, String sender, String receiver, int amount, String nonce, String previousHash, String hash) {

    this.index = index;
    this.timestamp = timestamp;
    transaction = new Transaction(sender, receiver, amount);
    this.nonce = nonce;
    this.previousHash = previousHash;
    this.hash = hash;

  }

  public int getIndex() {
    return index;
  }

  public java.sql.Timestamp getTimestamp() {
    return timestamp;
  }

  public String getNonce() {
    return nonce;
  }

  public String getPreviousHash() {
    return previousHash;
  }

  public String getHash() {
    return hash;
  }

  public String toString() {

    return timestamp.toString() + ":" + transaction.toString() + "." + nonce + previousHash;

  }

}
