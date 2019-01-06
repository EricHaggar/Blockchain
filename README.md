# BlockChain

BlockChain is a simplified [blockchain](https://en.wikipedia.org/wiki/Blockchain) simulation tool for bitcoin transactions.  BlockChain uses the **ArrayList** data structure to store one transaction in each block. In this tool, the 40 hex-character hash code is generated using the SHA-1 algorithm.


## Table of Contents


  * [Getting Started](#gettingStarted)
    * [Transactions File](#builtWith)
    * [Prerequisites](#prerequisites)
  * [Running The Simulation](#runSimulation)
  * [Built With](#builtWith)


## Getting Started 

Clone the repository with:

```bash
git clone https://github.com/EricHaggar/BlockChain.git
```

Change your directory to the project

```bash
cd BlockChain
```

### Transactions File

The [bitcoinBank](https://github.com/EricHaggar/Blockchain/blob/master/bitcoinBank.txt) text file contains initial transactions and format that needs to be strictly followed. The order within the transaction file needs to be the following:

```
block index
Long.valueOf(java.sql.Timestamp)
sender
receiver
amount
nonce (proof of work)
hash
```

The transaction file given can be used or a new one can be created.

**Please note:** "bitcoin" needs to be the first sender in the transaction and cannot be used in any other transactions. Also, the hash code for each transaction uses the following format:

```
timestamp.toString() + ":" + transaction.toString() + "." + nonce + previousHash
```

Once all desired transactions have been added, an output log file will be generated representing the created blockchain of bitcoin transactions.  For instance, if the transaction file is `bitcoinBank` and the miner ID is `Eric`, the output file will be `bitcoinBank_Eric.txt`.

### Prerequisites

Make sure you have the Java SE Development Kit 8 or higher. If not install it from:

    https://www.oracle.com/technetwork/java/javase/downloads/jdk11-downloads-5066655.html


## Running The Simulation

Open a terminal within the directory and compile the all java files using:

```
javac *.java
```
Run the tool 

```
java BlockChain
```

## Built With

* [ArrayList](https://docs.oracle.com/javase/8/docs/api/java/util/ArrayList.html)



