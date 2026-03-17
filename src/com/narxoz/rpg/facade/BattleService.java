package com.narxoz.rpg.facade;

import com.narxoz.rpg.decorator.AttackAction;
import com.narxoz.rpg.enemy.BossEnemy;
import com.narxoz.rpg.hero.HeroProfile;

import java.util.Random;

public class BattleService {
    private Random random = new Random(1L);

    public BattleService setRandomSeed(long seed) {
        this.random = new Random(seed);
        return this;
    }

    public AdventureResult battle(HeroProfile hero, BossEnemy boss, AttackAction action) {
        AdventureResult result=new AdventureResult();

        if (hero==null || boss==null || action==null) {
            result.setWinner("No contest");
            result.setRounds(0);
            result.setReward("No reward");
            result.addLine("Battle could not start because required participants were missing.");
            return result;
        }

        final int maxRounds=10;
        int round=0;

        result.addLine("Battle started: " +hero.getName()+" vs "+boss.getName());
        result.addLine("Chosen action: " +action.getActionName());
        result.addLine("Action effects: " +action.getEffectSummary());

        while (hero.isAlive() && boss.isAlive() && round<maxRounds) {
            round++;

            int heroDamage=action.getDamage()+random.nextInt(4);
            boss.takeDamage(heroDamage);
            result.addLine("Round "+round+": "+hero.getName()+" deals "
                    + heroDamage+" damage. " + boss.getName()+" HP = "+boss.getHealth());

            if (!boss.isAlive()) {
                break;
            }

            int bossDamage=boss.getAttackPower()+random.nextInt(3);
            hero.takeDamage(bossDamage);
            result.addLine("Round "+round+": "+boss.getName()+" deals "
                    + bossDamage+" damage. "+hero.getName()+" HP = "+hero.getHealth());
        }

        result.setRounds(round);

        if (hero.isAlive() && !boss.isAlive()) {
            result.setWinner(hero.getName());
            result.addLine("Battle result: hero victory.");
        } else if (!hero.isAlive() && boss.isAlive()) {
            result.setWinner(boss.getName());
            result.addLine("Battle result: boss victory.");
        } else {
            if (hero.getHealth()>=boss.getHealth()) {
                result.setWinner(hero.getName());
                result.addLine("Battle reached round limit. Winner decided by remaining HP: hero victory.");
            } else {
                result.setWinner(boss.getName());
                result.addLine("Battle reached round limit. Winner decided by remaining HP: boss victory.");
            }
        }
        result.setReward("Pending reward calculation");
        return result;
    }
}
