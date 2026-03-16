package com.narxoz.rpg.decorator;

public class CriticalFocusDecorator extends ActionDecorator {
    public CriticalFocusDecorator(AttackAction wrappedAction) {
        super(wrappedAction);
    }

    @Override
    public String getActionName() {
        return super.getActionName()+" + Critical Focus";
    }

    @Override
    public int getDamage() {
        int base=super.getDamage();
        return base+Math.max(3, base/2);
    }

    @Override
    public String getEffectSummary() {
        return super.getEffectSummary()+" | Focuses for a critical strike (+50% damage)";
    }
}
