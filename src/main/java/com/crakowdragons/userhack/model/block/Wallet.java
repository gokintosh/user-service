package com.crakowdragons.userhack.model.block;

import com.crakowdragons.userhack.model.AppUser;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.security.*;
import java.security.spec.ECGenParameterSpec;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Entity
public class Wallet {

    @Id
    int id;
    public PrivateKey privateKey;
    public PublicKey publicKey;

    @OneToOne
    public AppUser user;

    @SuppressWarnings("JpaAttributeTypeInspection")
    public HashMap<String, TransactionOutput>UTOXs=new HashMap<String,TransactionOutput>();


    public Wallet(){
        generateKeyPair();
    }
    public Wallet(AppUser appUser){
        this.user=appUser;
    }

    public void generateKeyPair(){
        try{
            KeyPairGenerator keyGen=KeyPairGenerator.getInstance("ECDSA","BC");
            SecureRandom random=SecureRandom.getInstance("SHA1PRNG");
            ECGenParameterSpec ecSpec=new ECGenParameterSpec("prime192v1");

//            initialize the key generator and generate a KeyPair
            keyGen.initialize(ecSpec,random);
            KeyPair keyPair=keyGen.generateKeyPair();

            privateKey= keyPair.getPrivate();
            publicKey=keyPair.getPublic();
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    public float getBalance(){
        float total=0;

        for(Map.Entry<String,TransactionOutput>item:TrashChain.UTXOs.entrySet()){
            TransactionOutput UTXO=item.getValue();

            if(UTXO.isMine(publicKey)){
                UTOXs.put(UTXO.id,UTXO);
                total+= UTXO.value;
            }
        }
        return total;
    }

    public Transaction sendFunds(PublicKey _recipient,float value){
        if(getBalance()<value){
            System.out.println("#Not enough funds to send transaction. Transaction discarded.");
            return null;
        }

        ArrayList<TransactionInput>inputs=new ArrayList<TransactionInput>();

        float total=0;

        for(Map.Entry<String,TransactionOutput>item: UTOXs.entrySet()){
            TransactionOutput UTXO=item.getValue();
            total+= UTXO.value;
            inputs.add(new TransactionInput(UTXO.id));
            if(total>value) break;
        }

        Transaction newTransaction=new Transaction(publicKey,_recipient,value,inputs);
        newTransaction.generateSignature(privateKey);

        for(TransactionInput input:inputs){
            UTOXs.remove(input.transactionOutputId);
        }

        return newTransaction;
    }
}
