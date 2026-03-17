package com.narxoz.rpg.facade;

public class RewardService {
    public String determineReward(AdventureResult battleResult) {
        if (battleResult==null) {
            return "No reward";
        }

        String winner=battleResult.getWinner();
        int rounds=battleResult.getRounds();

        if (winner==null || winner.equals("No contest")) {
            return "No reward";
        }

        if (winner.toLowerCase().contains("dragon")
                || winner.toLowerCase().contains("boss")
                || winner.toLowerCase().contains("overlord")) {
            return "Consolation herb bundle";
        }

        if (rounds<=3) {
            return "Legendary chest: 500 gold+boss trophy";
        }
        if (rounds<=6) {
            return "Rare chest: 250 gold+enchanted gem";
        }
        return "Victory pouch: 100 gold+healing potion";
    }
}
