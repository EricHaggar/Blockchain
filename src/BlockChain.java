import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

public class BlockChain {
    
    private ArrayList<Block> blocks;
    private ArrayList<Block> validatedBlocks;
    
    public BlockChain() {
        
        blocks = new ArrayList<>();
        validatedBlocks = new ArrayList<>();
    }
    
    public static BlockChain fromFile(String fileName) {
        
        BlockChain blockchain = new BlockChain();
        
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
            
            while ((line = bufferedReader.readLine()) != null) {
                
                index = Integer.parseInt(line);
                
                if (numOfBlocks == 0) {
                    previousHash = "00000";
                } else {
                    previousHash = blockchain.blocks.get(numOfBlocks - 1).getHash();
                }
                timestamp = new java.sql.Timestamp(Long.valueOf(bufferedReader.readLine()));
                sender = bufferedReader.readLine();
                receiver = bufferedReader.readLine();
                amount = Integer.parseInt(bufferedReader.readLine());
                nonce = bufferedReader.readLine();
                hash = bufferedReader.readLine();
                
                blockchain.blocks.add(new Block(index, timestamp, sender, receiver, amount, nonce, previousHash, hash));
                numOfBlocks++;
            }
            
            bufferedReader.close();
            
        } catch (IOException e) {
            System.out.print("Error while reading the file!");
        }
        
        return blockchain;
        
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
    
    public boolean validateBlockchain() {
        
        for (int i = 0; i < blocks.size() - 1; i++) {
            
            Block currentBlock = blocks.get(i);
            Block nextBlock = blocks.get(i + 1);
            
            //checks if indexes are consistent
            if ((currentBlock.getIndex() != i) || (nextBlock.getIndex() != i + 1)) {
                return false;
            }
            
            try {
                
                //calculates hash using Sha1 algorithm with attributes of the given block
                String currentVerifiedHash = Sha1.hash(currentBlock.toString(), Sha1.OUT_HEXW).replaceAll("\\s", "");
                
                //checks if calculated hash is the same as the hash in the block
                if (!currentBlock.getHash().equals(currentVerifiedHash)) {
                    return false;
                }
                
                if (i == blocks.size() - 2) {
                    
                    String nextVerifiedHash = Sha1.hash(nextBlock.toString(), Sha1.OUT_HEXW).replaceAll("\\s", "");
                    
                    if (!nextBlock.getHash().equals(nextVerifiedHash)) {
                        return false;
                    }
                    
                }
                
            } catch (UnsupportedEncodingException e) {
                System.out.println("EncodingError");
            }
            
            //checks if the current hash is the same as the previous hash of the next block
            if (!currentBlock.getHash().equals(nextBlock.getPreviousHash())) {
                return false;
            }
            
            //Checking the validity of the transactions
            if (i == 0) { //the first sender has to be bitcoin
                
                if (!currentBlock.getTransaction().getSender().equals("bitcoin")) {
                    return false;
                    
                } else {
                    validatedBlocks.add(currentBlock);
                }
                
            } else if (!currentBlock.getTransaction().getSender().equals("bitcoin")) {
                
                if (i < blocks.size() - 2) {
                    
                    if (getBalance(currentBlock.getTransaction().getSender()) < currentBlock.getTransaction().getAmount()) {
                        return false;
                        
                    } else {
                        validatedBlocks.add(currentBlock);
                    }
                    
                } else {
                    
                    if (getBalance(currentBlock.getTransaction().getSender()) < currentBlock.getTransaction().getAmount()) {
                        return false;
                        
                    } else {
                        validatedBlocks.add(currentBlock);
                    }
                    
                    if (getBalance(nextBlock.getTransaction().getSender()) < nextBlock.getTransaction().getAmount()) {
                        return false;
                        
                    } else {
                        validatedBlocks.add(nextBlock);
                    }
                    
                }
                
            } else {
                validatedBlocks.add(currentBlock);
            }
            
        }
        
        return true;
    }
    
    public int getBalance(String username) {
        
        int userBalance = 0;
        
        for (int i = 0; i < validatedBlocks.size(); i++) {
            
            if (validatedBlocks.get(i).getTransaction().getReceiver().equals(username)) {
                
                userBalance += validatedBlocks.get(i).getTransaction().getAmount();
                
            } else if (validatedBlocks.get(i).getTransaction().getSender().equals(username)) {
                
                userBalance -= validatedBlocks.get(i).getTransaction().getAmount();
            }
        }
        
        return userBalance;
    }
    
    public static void main(String[] args) {
        
        BlockChain blockchain = fromFile("bitcoinBank.txt");
        
        blockchain.validateBlockchain();
        
        blockchain.toFile("output.txt");
    }
}

