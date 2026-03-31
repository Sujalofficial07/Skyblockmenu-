// Path: /skyblock-mobs/src/main/java/net/sujal/mobs/combat/DamageCalculator.java
package net.sujal.mobs.combat;

import java.util.Random;

/**
 * Handles the raw math for Hypixel Skyblock style combat.
 */
public class DamageCalculator {

    private static final Random RANDOM = new Random();

    /**
     * Calculates the final outgoing damage.
     * Formula: (5 + WeaponDamage) * (1 + Strength/100) * (CritMultiplier)
     */
    public static double calculateOutgoingDamage(double baseWeaponDamage, double strength, double critChance, double critDamage) {
        double initialDamage = 5.0 + baseWeaponDamage;
        double strengthMultiplier = 1.0 + (strength / 100.0);
        
        double totalDamage = initialDamage * strengthMultiplier;

        // Crit Calculation
        // Crit chance is out of 100. If random number (0-100) is less than crit chance, it's a critical hit!
        if (RANDOM.nextDouble() * 100.0 <= critChance) {
            double critMultiplier = 1.0 + (critDamage / 100.0);
            totalDamage *= critMultiplier;
            // TODO: In the future, you can return a wrapper object to spawn a "CRIT!" hologram here.
        }

        return totalDamage;
    }

    /**
     * Calculates the damage a player actually takes after Defense reduction.
     * Formula: IncomingDamage * (1 - (Defense / (Defense + 100)))
     */
    public static double calculateDamageTaken(double incomingDamage, double defense) {
        if (defense <= 0) return incomingDamage;

        double damageReductionPercentage = defense / (defense + 100.0);
        return incomingDamage * (1.0 - damageReductionPercentage);
    }
}
