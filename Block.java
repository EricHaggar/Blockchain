public class Block {

  //defining Block class variables
  private int index;                // the index of the block in the list
  private java.sql.Timestamp timestamp;  // time at which transaction was created
  private Transaction transaction;  // the transaction object
  private String nonce;             // random string (for proof of work)
  private String previousHash;   // previous hash (set to "" in first block)
  private String hash;  // hash of the block (hash of string obtained

  /*
  First constructor for Block used when creating a block from a file.
  This constructor is used because all variables are already defined
  in the file.
  */
  public Block(int index, java.sql.Timestamp timestamp, Transaction transaction, String nonce, String previousHash, String hash) {

    this.index = index;
    this.timestamp = timestamp;
    this.transaction = transaction;
    this.nonce = nonce;
    this.previousHash = previousHash;
    this.hash = hash;

  }

  /*
  Second constructor for Block used when creating a new block (user input)
  This constructor is used because there are variables missing such as
  the nonce and the hash.  These variables will be initialized when the
  nonce is calculated and will be initialized by setter methods.   */
  public Block(int index, java.sql.Timestamp timestamp, Transaction transaction, String previousHash) {

    this.index = index;
    this.timestamp = timestamp;
    this.transaction = transaction;
    this.previousHash = previousHash;

  }

  /*
  Getter method to return the index of the block in the blockchain
  */
  public int getIndex() {
    return index;
  }

  /*
  Getter method to return the timestamp of the block in the blockchain
  */
  public java.sql.Timestamp getTimestamp() {
    return timestamp;
  }

  /*
  Getter method to return the transaction of the block in the blockchain
  */
  public Transaction getTransaction() {
    return transaction;
  }

  /*
  Getter method to return the nonce of the block in the blockchain
  */
  public String getNonce() {
    return nonce;
  }

  /*
  Getter method to return the previous hash of the block in the blockchain
  */
  public String getPreviousHash() {
    return previousHash;
  }

  /*
  Getter method to return the hash of the block in the blockchain
  */
  public String getHash() {
    return hash;
  }

  /*
  Setter method to set the nonce of the block in the blockchain
  */
  public void setNonce(String nonce) {
    this.nonce = nonce;
  }

  /*
  Setter method to set the hash of the block in the blockchain
  */
  public void setHash(String hash) {
    this.hash = hash;
  }

  /*
  toString method to return the string of the block to be used
  in generating the hash code
  */
  public String toString() {
    return timestamp.toString() + ":" + transaction.toString() + "." + nonce + previousHash;
  }

}
