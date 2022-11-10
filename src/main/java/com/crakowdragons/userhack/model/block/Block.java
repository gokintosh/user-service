package com.crakowdragons.userhack.model.block;


import com.crakowdragons.userhack.utility.StringUtil;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

@Getter
@Setter
@Slf4j
public class Block {

    private String hash;
    private String previousHash;
    private String data;
    private long timeStamp;

    private int nonce;



    public Block(String data,String previousHash){
        this.data=data;
        this.previousHash=previousHash;
        this.timeStamp=new Date().getTime();
        this.hash=calculateHash();
    }

    public String calculateHash(){
        String calculatedHash= StringUtil.applySha256(
                previousHash+Long.toString(timeStamp)+Integer.toString(nonce)+
                        data
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


}
