package com.narxoz.rpg.facade;

import com.narxoz.rpg.decorator.AttackAction;
import com.narxoz.rpg.enemy.BossEnemy;
import com.narxoz.rpg.hero.HeroProfile;

public class DungeonFacade {
    private final PreparationService preparationService = new PreparationService();
    private final BattleService battleService = new BattleService();
    private final RewardService rewardService = new RewardService();

    public DungeonFacade setRandomSeed(long seed) {
        battleService.setRandomSeed(seed);
        return this;
    }

    public AdventureResult runAdventure(HeroProfile hero, BossEnemy boss, AttackAction action) {
        String preparationSummary=preparationService.prepare(hero, boss, action);

        AdventureResult result=new AdventureResult();
        result.addLine(preparationSummary);

        if (preparationSummary.startsWith("Preparation failed")) {
            result.setWinner("No contest");
            result.setRounds(0);
            result.setReward("No reward");
            return result;
        }

        AdventureResult battleResult=battleService.battle(hero, boss, action);

        for (String line : battleResult.getLog()) {
            result.addLine(line);
        }

        result.setWinner(battleResult.getWinner());
        result.setRounds(battleResult.getRounds());
        result.setReward(rewardService.determineReward(battleResult));

        return result;
    }
}
