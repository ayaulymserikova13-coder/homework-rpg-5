package com.narxoz.rpg;

import com.narxoz.rpg.decorator.AttackAction;
import com.narxoz.rpg.decorator.BasicAttack;
import com.narxoz.rpg.decorator.CriticalFocusDecorator;
import com.narxoz.rpg.decorator.FireRuneDecorator;
import com.narxoz.rpg.decorator.PoisonCoatingDecorator;
import com.narxoz.rpg.enemy.BossEnemy;
import com.narxoz.rpg.facade.AdventureResult;
import com.narxoz.rpg.facade.DungeonFacade;
import com.narxoz.rpg.hero.HeroProfile;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Homework 5 Demo: Decorator + Facade ===\n");

        HeroProfile hero=new HeroProfile("Aerin the Ranger", 110);
        BossEnemy boss=new BossEnemy("Shadow Dragon", 125, 14);

        AttackAction basic=new BasicAttack("Strike", 12);
        AttackAction fireStrike=new FireRuneDecorator(basic);
        AttackAction venomStrike=new PoisonCoatingDecorator(basic);
        AttackAction assassinStrike=new CriticalFocusDecorator(
                new PoisonCoatingDecorator(basic)
        );
        AttackAction ultimateStrike=new FireRuneDecorator(
                new PoisonCoatingDecorator(
                        new CriticalFocusDecorator(basic)
                )
        );

        System.out.println("--- Decorator Preview ---");
        printAction("Base action", basic);
        printAction("Fire upgrade", fireStrike);
        printAction("Poison upgrade", venomStrike);
        printAction("Critical + Poison", assassinStrike);
        printAction("Critical + Poison + Fire", ultimateStrike);

        System.out.println("\nDecorator order proof:");
        AttackAction orderOne=new FireRuneDecorator(
                new CriticalFocusDecorator(basic)
        );
        AttackAction orderTwo=new CriticalFocusDecorator(
                new FireRuneDecorator(basic)
        );

        printAction("Fire then Critical", orderOne);
        printAction("Critical then Fire", orderTwo);

        System.out.println("\n--- Facade Preview ---");
        DungeonFacade facade=new DungeonFacade().setRandomSeed(42L);
        AdventureResult result=facade.runAdventure(hero, boss, ultimateStrike);

        System.out.println("Winner: "+result.getWinner());
        System.out.println("Rounds: "+result.getRounds());
        System.out.println("Reward: "+result.getReward());
        System.out.println("\nAdventure log:");
        for (String line:result.getLog()) {
            System.out.println("- "+line);
        }

        System.out.println("\n=== Demo Complete ===");
    }

    private static void printAction(String label, AttackAction action) {
        System.out.println(label+":");
        System.out.println("  Name: "+action.getActionName());
        System.out.println("  Damage: "+action.getDamage());
        System.out.println("  Effects: "+action.getEffectSummary());
        System.out.println();
    }
}
