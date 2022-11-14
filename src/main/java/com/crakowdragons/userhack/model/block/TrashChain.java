package com.crakowdragons.userhack.model.block;

import com.crakowdragons.userhack.utility.StringUtil;
import com.google.gson.GsonBuilder;

import java.security.Security;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TrashChain {

    public static List<Block> trashChain = new ArrayList<>();

    public static float minimumTransaction = 0.1f;
    public static HashMap<String, TransactionOutput> UTXOs = new HashMap<String, TransactionOutput>();

    public static int difficulty = 3;

    public static Wallet walletA;
    public static Wallet walletB;

    public static Transaction genesisTransaction;

    public static void main(String[] args) {

        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());

        walletA = new Wallet();
        walletB = new Wallet();

        Wallet mrbin = new Wallet();

        genesisTransaction = new Transaction(mrbin.publicKey, walletA.publicKey, 100f, null);
        genesisTransaction.generateSignature(mrbin.privateKey);
        genesisTransaction.transactionId = "0";
        genesisTransaction.outputs.add(new TransactionOutput(genesisTransaction.reciepient, genesisTransaction.value, genesisTransaction.transactionId));
        UTXOs.put(genesisTransaction.outputs.get(0).id, genesisTransaction.outputs.get(0));

        System.out.println("Creating and Mining genesis block..");
        Block genesis = new Block("0");
        genesis.addTransaction(genesisTransaction);
        addBlock(genesis);

        Block block1 = new Block(genesis.hash);
        System.out.println("\n WalletA's balance is: " + walletA.getBalance());
        System.out.println("\n walletA is attempting to send funds (40) to walletB.");
        block1.addTransaction(walletA.sendFunds(walletB.publicKey, 40f));
        addBlock(block1);
        System.out.println("\n WalletAs balance is :" + walletA.getBalance());
        System.out.println("\n WalletBs balance is :" + walletB.getBalance());


        Block block2 = new Block(block1.hash);
        System.out.println("\n walletA is attempting to send more funds 1000 than it has..");
        block2.addTransaction(walletA.sendFunds(walletB.publicKey, 1000f));
        addBlock(block2);
        System.out.println("\n walletA's balance is :" + walletA.getBalance());
        System.out.println("\n walletB's balance is :" + walletB.getBalance());


        Block block3 = new Block(block2.getHash());
        System.out.println("\n WalletB is attempting to send funds 20 to WalletA..");
        block3.addTransaction(walletB.sendFunds(walletA.publicKey, 20));
        System.out.println("\n walletA's balance is :" + walletA.getBalance());
        System.out.println("\n walletB's balance is :" + walletB.getBalance());

        isChainValid();


    }


    public static Boolean isChainValid() {
        Block currentBlock;
        Block previousBlock;
        String hashTarget = new String(new char[difficulty]).replace('\0', '0');
        HashMap<String,TransactionOutput> tempUTXOs = new HashMap<String,TransactionOutput>(); //a temporary working list of unspent transactions at a given block state.
        tempUTXOs.put(genesisTransaction.outputs.get(0).id, genesisTransaction.outputs.get(0));

        //loop through blockchain to check hashes:
        for(int i=1; i < trashChain.size(); i++) {

            currentBlock = trashChain.get(i);
            previousBlock = trashChain.get(i-1);
            //compare registered hash and calculated hash:
            if(!currentBlock.hash.equals(currentBlock.calculateHash()) ){
                System.out.println("#Current Hashes not equal");
                return false;
            }
            //compare previous hash and registered previous hash
            if(!previousBlock.hash.equals(currentBlock.previousHash) ) {
                System.out.println("#Previous Hashes not equal");
                return false;
            }
            //check if hash is solved
            if(!currentBlock.hash.substring( 0, difficulty).equals(hashTarget)) {
                System.out.println("#This block hasn't been mined");
                return false;
            }

            //loop thru blockchains transactions:
            TransactionOutput tempOutput;
            for(int t=0; t <currentBlock.transactions.size(); t++) {
                Transaction currentTransaction = currentBlock.transactions.get(t);

                if(!currentTransaction.verifySignature()) {
                    System.out.println("#Signature on Transaction(" + t + ") is Invalid");
                    return false;
                }
                if(currentTransaction.getInputsValue() != currentTransaction.getOutPutsValue()) {
                    System.out.println("#Inputs are note equal to outputs on Transaction(" + t + ")");
                    return false;
                }

                for(TransactionInput input: currentTransaction.inputs) {
                    tempOutput = tempUTXOs.get(input.transactionOutputId);

                    if(tempOutput == null) {
                        System.out.println("#Referenced input on Transaction(" + t + ") is Missing");
                        return false;
                    }

                    if(input.UTXO.value != tempOutput.value) {
                        System.out.println("#Referenced input Transaction(" + t + ") value is Invalid");
                        return false;
                    }

                    tempUTXOs.remove(input.transactionOutputId);
                }

                for(TransactionOutput output: currentTransaction.outputs) {
                    tempUTXOs.put(output.id, output);
                }

                if( currentTransaction.outputs.get(0).reciepient != currentTransaction.reciepient) {
                    System.out.println("#Transaction(" + t + ") output reciepient is not who it should be");
                    return false;
                }
                if( currentTransaction.outputs.get(1).reciepient != currentTransaction.sender) {
                    System.out.println("#Transaction(" + t + ") output 'change' is not sender.");
                    return false;
                }

            }

        }
        System.out.println("Blockchain is valid");
        return true;
    }
    public static void addBlock(Block block){
        block.mineBlock(difficulty);
        trashChain.add(block);
    }

}
