import java.util.*;

class Battle {
    public static void startPvE(Trainer player, Trainer computer, Scanner sc) {
        System.out.println("\nTrainer Rivasa has challenged you to a battle!");
        System.out.println("Battle Start between " + player.getName() + " and Trainer Rivasa!");

        // Show computer's Pokémon before the battle starts
        showComputerPokemons(computer);
        
        Pokemon computerPokemon = computer.chooseRandomPokemon();
        
        while (player.hasAvailablePokemon() && computer.hasAvailablePokemon()) {
            Pokemon playerPokemon = player.choosePokemon(sc);
            while (playerPokemon.isFainted()) {
                System.out.println(playerPokemon.getName() + " has fainted. Choose another Pokémon.");
                playerPokemon = player.choosePokemon(sc);
            }
            while (computerPokemon == null || computerPokemon.isFainted()) {
                computerPokemon = computer.chooseRandomPokemon();
            }
    
            System.out.println("\n-- " + playerPokemon.getName() + " VS " + computerPokemon.getName() + " --");

            Pokemon[] updated = battleTurnPvE(player, playerPokemon, computer, computerPokemon, sc);
            playerPokemon = updated[0];
            computerPokemon = updated[1];
        }

        if (player.hasAvailablePokemon()) {
            System.out.println("\n" + player.getName() + " wins the battle!");
        } else {
            System.out.println("\nTrainer Rivasa wins the battle!");
        }
    }

    private static void showComputerPokemons(Trainer computer) {
        System.out.println("\nTrainer Rivasa has the following Pokémon:");
        for (Pokemon p : computer.getPokemons()) {
            System.out.println("- " + p.getName() + " (Level: " + p.getLevel() + ", HP: " + p.getHp() + ", Type: " + p.getType() + ")");
        }
    }

    private static Pokemon[] battleTurnPvE(Trainer player, Pokemon playerPokemon, Trainer computer, Pokemon computerPokemon, Scanner sc) {
        while (!playerPokemon.isFainted() && !computerPokemon.isFainted()) {
            // Player chooses action
            System.out.println("\nChoose action for " + playerPokemon.getName() + ":");
            System.out.println("1. Regular Attack");
            System.out.println("2. Special Attack");
            System.out.println("3. Guard");
            System.out.println("4. Use Item");
            System.out.println("5. Change Pokémon");

            int playerAction = sc.nextInt();
            sc.nextLine(); 
            System.out.println("Player action chosen: " + playerAction);

            // Computer chooses action
            System.out.println("\nTrainer Rivasa is choosing an action for " + computerPokemon.getName() + "...");
            int computerAction = new Random().nextInt(5) + 1; // Randomly choose an action (1-5)
            System.out.println("Computer action chosen: " + computerAction);

            // Process actions
            Pokemon[] updatedPokemons = processActions(playerAction, playerPokemon, computerAction, computerPokemon, player, computer, sc);
            playerPokemon = updatedPokemons[0];
            computerPokemon = updatedPokemons[1];


            // Display status after both actions
            System.out.println("\n[Status] " + playerPokemon.getName() + " HP: " + playerPokemon.getHp() + " | " + computerPokemon.getName() + " HP: " + computerPokemon.getHp());
        }
        return new Pokemon[] {playerPokemon, computerPokemon};
    }

    private static Pokemon[] processActions(int playerAction, Pokemon playerPokemon, int computerAction, Pokemon computerPokemon, Trainer player, Trainer computer, Scanner sc) {
        boolean playerGuarded = false;
        boolean computerGuarded = false;

        // Process player's action
        switch (playerAction) {
            case 1: // Regular Attack
                System.out.println(playerPokemon.getName() + " uses Regular Attack!");
                break;

            case 2: // Special Attack
                System.out.println(playerPokemon.getName() + " uses Special Attack!");
                break;

            case 3: // Guard
                System.out.println(playerPokemon.getName() + " is guarding!");
                playerGuarded = true;
                break;

            case 4: // Use Item
                System.out.println("Choose an item to use:");
                System.out.println("1. Potion (+30 HP)");
                System.out.println("2. Elixir (+10 Levels)");
                int itemChoice = sc.nextInt();
                sc.nextLine(); // Consume newline
                if (itemChoice == 1) {
                    playerPokemon.heal(30);
                    System.out.println(playerPokemon.getName() + " healed 30 HP!");
                } else if (itemChoice == 2) {
                    playerPokemon.levelUp(10);
                    System.out.println(playerPokemon.getName() + " leveled up by 10!");
                }
                 return new Pokemon[] {playerPokemon, computerPokemon};// Skip computer's action

            case 5: // Change Pokémon
                playerPokemon = player.choosePokemon(sc);
                while (playerPokemon.isFainted()) {
                    System.out.println(playerPokemon.getName() + " has fainted. Choose another Pokémon.");
                    playerPokemon = player.choosePokemon(sc);
                }
                System.out.println(playerPokemon.getName() + " is now in battle!");
               return new Pokemon[] {playerPokemon, computerPokemon};
            default:
                System.out.println("Invalid action.");
                return new Pokemon[] {playerPokemon, computerPokemon};
        }

        // Process computer's action
        switch (computerAction) {
            case 1: // Regular Attack
                System.out.println(computerPokemon.getName() + " uses Regular Attack!");
                break;

            case 2: // Special Attack
                System.out.println(computerPokemon.getName() + " uses Special Attack!");
                break;

            case 3: // Guard
                System.out.println(computerPokemon.getName() + " is guarding!");
                computerGuarded = true;
                break;

            case 4: // Use Item
                System.out.println(computerPokemon.getName() + " uses an item!");
                computerPokemon.heal(30);
                System.out.println(computerPokemon.getName() + " healed 30 HP!");
                return new Pokemon[] {playerPokemon, computerPokemon}; // Skip player's action
            case 5: // Change Pokémon
                 Pokemon newPokemon = computer.chooseRandomPokemonExcluding(computerPokemon);
                if (newPokemon != computerPokemon) {
                computerPokemon = newPokemon;
                System.out.println("Trainer Rivasa sends out " + computerPokemon.getName() + "!");
                } else {
                System.out.println("Trainer Rivasa tried to switch Pokémon, but no other options available!");
                }
                return new Pokemon[] {playerPokemon, computerPokemon}; // Skip player's action
            default:
                System.out.println("Trainer Rivasa made an invalid choice.");
                return new Pokemon[] {playerPokemon, computerPokemon};
        }

        // Calculate damage and effectiveness
        if (!playerGuarded) {
            int playerDamage = playerAction == 2 ? 10 : 5; // Example damage calculation
            double effectiveness = TypeChart.getEffectiveness(playerPokemon.getType(), computerPokemon.getType());
            playerDamage *= effectiveness;
            computerPokemon.setHp(computerPokemon.getHp() - playerDamage);
            System.out.println(playerPokemon.getName() + " dealt " + playerDamage + " damage to " + computerPokemon.getName() + "!");
            printEffectiveness(effectiveness);
        }

        if (!computerGuarded) {
            int computerDamage = computerAction == 2 ? 10 : 5; // Example damage calculation
            double effectiveness = TypeChart.getEffectiveness(computerPokemon.getType(), playerPokemon.getType());
            computerDamage *= effectiveness;
            playerPokemon.setHp(playerPokemon.getHp() - computerDamage);
            System.out.println(computerPokemon.getName() + " dealt " + computerDamage + " damage to " + playerPokemon.getName() + "!");
            printEffectiveness(effectiveness);
        }
         return new Pokemon[] {playerPokemon, computerPokemon};
    }

    private static void printEffectiveness(double effectiveness) {
        if (effectiveness == 2.0) {
            System.out.println("It's super effective!");
        } else if (effectiveness == 0.5) {
            System.out.println("It's not very effective...");
        } else {
            System.out.println("It's a normal attack.");
        }
    }
}