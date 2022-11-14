package com.crakowdragons.userhack.model.block;


import com.crakowdragons.userhack.utility.StringUtil;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;

@Getter
@Setter
@Slf4j
public class Block {

    public String hash;
    public String previousHash;

    public String merkleRoot;

    public ArrayList<Transaction>transactions=new ArrayList<Transaction>();
    public long timeStamp;

    public int nonce;



    public Block(String previousHash){

        this.previousHash=previousHash;
        this.timeStamp=new Date().getTime();
        this.hash=calculateHash();
    }

    public String calculateHash(){
        String calculatedHash= StringUtil.applySha256(
                previousHash+Long.toString(timeStamp)+Integer.toString(nonce)+
                        merkleRoot
        );

        return calculatedHash;

    }

    public void mineBlock(int difficulty){
        String target=new String(new char[difficulty]).replace('\0','0');

        while(!hash.substring(0,difficulty).equals(target)){
            nonce++;
            hash=calculateHash();
        }

        System.out.println("Block Mined!! : "+hash);
    }


    public boolean addTransaction(Transaction transaction) {
        //process transaction and check if valid, unless block is genesis block then ignore.
        if(transaction == null) return false;
        if((previousHash != "0")) {
            if((!transaction.processTransaction())) {
                System.out.println("Transaction failed to process. Discarded.");
                return false;
            }
        }
        transactions.add(transaction);
        System.out.println("Transaction Successfully added to Block");
        return true;
    }


}
