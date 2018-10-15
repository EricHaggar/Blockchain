import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class BlockChain {

  private ArrayList blockChain;


  public static BlockChain fromFile(String fileName) {

    try {

      FileReader fileReader = new FileReader(fileName);
      BufferedReader bufferedReader = new BufferedReader(fileReader);

      String line;

      while ((line = bufferedReader.readLine()) != null) {

        System.out.println(line);

      }


    } catch (IOException e) {
      System.out.print("The file was not found");
    }

    return null;

  }


  public void toFile(String fileName) {

    System.out.println("COMPLETE toFile METHOD!!");
  }

  public static void main(String[] args) {

    System.out.println("COMPLETE MAIN METHOD!!");
    fromFile("bitcoinBank.txt");


  }
}
