package com.crakowdragons.userhack.model.block;

import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

public class TrashChain {

    public static void main(String[] args) {


        List<Block>trashChain=new ArrayList<>();

        int difficulty=6;


        trashChain.add(new Block("This is block 1","0"));
        System.out.println("Mining block 1");
        trashChain.get(0).mineBlock(difficulty);

        trashChain.add(new Block("This is block 2",trashChain.get((trashChain.size())-1).getHash()));
        System.out.println("Mining block 2");
        trashChain.get((trashChain.size())-1).mineBlock(difficulty);

        trashChain.add(new Block("This is block 3",trashChain.get((trashChain.size())-1).getHash()));
        System.out.println("Mining block 3");
        trashChain.get((trashChain.size())-1).mineBlock(difficulty);


        String blockChainJson=new GsonBuilder().setPrettyPrinting().create().toJson(trashChain);

        System.out.println(blockChainJson);

    }
}
