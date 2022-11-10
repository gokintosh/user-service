package com.crakowdragons.userhack.model.block;

public class TrashChain {

    public static void main(String[] args) {


        Block genesisBlock=new Block("Hi I am the first block","0");
        System.out.println("Hash for block 1 : "+genesisBlock.getHash());

        Block secondBlock=new Block("Hi I am the second block", genesisBlock.getHash());
        System.out.println("Hash for block 2 : "+secondBlock.getHash());

        Block thirdBlock=new Block("Hi I am the third block", secondBlock.getHash());
        System.out.println("Hash for block 3 : "+thirdBlock.getHash());


    }
}
