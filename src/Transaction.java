public class Transaction {

  //defining Transaction class variables sender, receiver and amount
  private String sender;
  private String receiver;
  private int amount;

  /*
  Transaction constructor to initialize sender, receiver and amount
  of the transaction.
  */
  public Transaction(String sender, String receiver, int amount) {

    this.sender = sender;
    this.receiver = receiver;
    this.amount = amount;

  }

  /*
  Getter method to return the sender of the transaction
  */
  public String getSender() {
    return sender;
  }

  /*
  Getter method to return the receiver of the transaction
  */
  public String getReceiver() {
    return receiver;
  }

  /*
  Getter method to return the amount of the transaction
  */
  public int getAmount() {
    return amount;
  }

  /*
  toString method to return the transaction of the block
  in a specific format
  */
  public String toString() {
    return sender + ":" + receiver + "=" + amount;
  }

}
