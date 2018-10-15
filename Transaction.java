public class Transaction {

  private String sender;
  private String receiver;
  private int amount;

  public String toString() {

   return sender + ":" + receiver + "=" + amount;

  }

}
