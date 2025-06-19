import java.util.HashMap;
import java.util.Map;

public class TypeChart {
    private static final Map<String, Map<String, Double>> chart = new HashMap<>();

    static {
        // Fire
        addEffectiveness("Fire", "Grass", 2.0);
        addEffectiveness("Fire", "Water", 0.5);
        addEffectiveness("Fire", "Rock", 0.5);

        // Water
        addEffectiveness("Water", "Fire", 2.0);
        addEffectiveness("Water", "Grass", 0.5);
        addEffectiveness("Water", "Rock", 2.0);
        addEffectiveness("Water", "Electric", 0.5);

        // Grass
        addEffectiveness("Grass", "Water", 2.0);
        addEffectiveness("Grass", "Fire", 0.5);
        addEffectiveness("Grass", "Flying", 0.5);
        addEffectiveness("Grass", "Electric", 2.0);

        // Flying
        addEffectiveness("Flying", "Grass", 2.0);
        addEffectiveness("Flying", "Electric", 0.5);
        addEffectiveness("Flying", "Rock", 0.5);
        
        // Electric
        addEffectiveness("Electric", "Water", 2.0);
        addEffectiveness("Electric", "Grass", 0.5);
        addEffectiveness("Electric", "Flying", 2.0);
        
        // Rock
        addEffectiveness("Rock", "Flying", 2.0);
        addEffectiveness("Rock", "Fire", 2.0);
        addEffectiveness("Rock", "Water", 0.5);
        // Default = 1.0
    }

    private static void addEffectiveness(String attacker, String defender, double multiplier) {
        chart.computeIfAbsent(attacker, k -> new HashMap<>()).put(defender, multiplier);
    }

    public static double getEffectiveness(String attacker, String defender) {
        return chart.getOrDefault(attacker, new HashMap<>()).getOrDefault(defender, 1.0);
    }
}
