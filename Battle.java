import java.util.*;

class Battle {
    public static void startPvE(Trainer player, Trainer computer, Scanner sc) {
        System.out.println("\nTrainer Rivasa has challenged you to a battle!");
        System.out.println("Battle Start between " + player.getName() + " and Trainer Rivasa!");

        // Show computer's Pokémon before the battle starts
        showComputerPokemons(computer);

        Map<String, Integer> playerItemUsage = new HashMap<>();
        playerItemUsage.put("Potion", 0);
        playerItemUsage.put("Elixir", 0);

        Map<String, Integer> computerItemUsage = new HashMap<>();
        computerItemUsage.put("Potion", 0);
        computerItemUsage.put("Elixir", 0);

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

            Pokemon[] updated = battleTurnPvE(player, playerPokemon, computer, computerPokemon, sc, playerItemUsage, computerItemUsage);
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

    private static Pokemon[] battleTurnPvE(Trainer player, Pokemon playerPokemon, Trainer computer, Pokemon computerPokemon, Scanner sc, Map<String, Integer> playerItemUsage, Map<String, Integer> computerItemUsage) {
        while (!playerPokemon.isFainted() && !computerPokemon.isFainted()) {
            System.out.println("\nChoose action for " + playerPokemon.getName() + ":");
            System.out.println("1. Regular Attack");
            System.out.println("2. Special Attack");
            System.out.println("3. Guard");
            System.out.println("4. Use Item");
            System.out.println("5. Change Pokémon");

            int playerAction = sc.nextInt();
            sc.nextLine(); 
            System.out.println("Player action chosen: " + playerAction);

            System.out.println("\nTrainer Rivasa is choosing an action for " + computerPokemon.getName() + "...");
            int computerAction = new Random().nextInt(5) + 1;
            System.out.println("Computer action chosen: " + computerAction);

            Pokemon[] updatedPokemons = processActions(playerAction, playerPokemon, computerAction, computerPokemon, player, computer, sc, playerItemUsage, computerItemUsage);
            playerPokemon = updatedPokemons[0];
            computerPokemon = updatedPokemons[1];

            System.out.println("\n[Status] " + playerPokemon.getName() + " HP: " + playerPokemon.getHp() + " | " + computerPokemon.getName() + " HP: " + computerPokemon.getHp());
        }
        return new Pokemon[] {playerPokemon, computerPokemon};
    }

    private static Pokemon[] processActions(int playerAction, Pokemon playerPokemon, int computerAction, Pokemon computerPokemon, Trainer player, Trainer computer, Scanner sc, Map<String, Integer> playerItemUsage, Map<String, Integer> computerItemUsage) {
        boolean playerGuarded = false;
        boolean computerGuarded = false;

        switch (playerAction) {
            case 1:
                System.out.println(playerPokemon.getName() + " uses Regular Attack!");
                break;
            case 2:
                System.out.println(playerPokemon.getName() + " uses Special Attack!");
                break;
            case 3:
                System.out.println(playerPokemon.getName() + " is guarding!");
                playerGuarded = true;
                break;
            case 4:
                System.out.println("Choose an item to use:");
                System.out.println("1. Potion (+30 HP)");
                System.out.println("2. Elixir (+10 Levels)");
                int itemChoice = sc.nextInt();
                sc.nextLine();

                if (itemChoice == 1) {
                    if (playerPokemon.getHp() == playerPokemon.getMaxHp()) {
                        System.out.println(playerPokemon.getName() + "'s HP is already full. No item used.");
                    } else if (playerItemUsage.get("Potion") >= 2) {
                        System.out.println("You have no more Potions left!");
                    } else {
                        playerPokemon.heal(30);
                        playerItemUsage.put("Potion", playerItemUsage.get("Potion") + 1);
                        System.out.println(playerPokemon.getName() + " healed 30 HP! (Used " + playerItemUsage.get("Potion") + "/2 Potions)");
                    }
                } else if (itemChoice == 2) {
                    if (playerItemUsage.get("Elixir") >= 2) {
                        System.out.println("You have no more Elixirs left!");
                    } else {
                        playerPokemon.levelUp(10);
                        playerItemUsage.put("Elixir", playerItemUsage.get("Elixir") + 1);
                        System.out.println(playerPokemon.getName() + " leveled up by 10! (Used " + playerItemUsage.get("Elixir") + "/2 Elixirs)");
                    }
                } else {
                    System.out.println("Invalid item choice.");
                }
                return new Pokemon[] {playerPokemon, computerPokemon};
            case 5:
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

        switch (computerAction) {
            case 1:
                System.out.println(computerPokemon.getName() + " uses Regular Attack!");
                break;
            case 2:
                System.out.println(computerPokemon.getName() + " uses Special Attack!");
                break;
            case 3:
                System.out.println(computerPokemon.getName() + " is guarding!");
                computerGuarded = true;
                break;
            case 4:
                System.out.println(computerPokemon.getName() + " uses an item!");
                if (computerPokemon.getHp() == computerPokemon.getMaxHp()) {
                    System.out.println(computerPokemon.getName() + "'s HP is already full. No item used.");
                } else if (computerItemUsage.get("Potion") < 2) {
                    computerPokemon.heal(30);
                    computerItemUsage.put("Potion", computerItemUsage.get("Potion") + 1);
                    System.out.println(computerPokemon.getName() + " healed 30 HP! (Used " + computerItemUsage.get("Potion") + "/2 Potions)");
                } else {
                    System.out.println("Trainer Rivasa has no more Potions left!");
                }
                return new Pokemon[] {playerPokemon, computerPokemon};
            case 5:
                Pokemon newPokemon = computer.chooseRandomPokemonExcluding(computerPokemon);
                if (newPokemon != computerPokemon) {
                    computerPokemon = newPokemon;
                    System.out.println("Trainer Rivasa sends out " + computerPokemon.getName() + "!");
                } else {
                    System.out.println("Trainer Rivasa tried to switch Pokémon, but no other options available!");
                }
                return new Pokemon[] {playerPokemon, computerPokemon};
            default:
                System.out.println("Trainer Rivasa made an invalid choice.");
                return new Pokemon[] {playerPokemon, computerPokemon};
        }

        if (!playerGuarded) {
            int playerDamage = playerAction == 2 ? 10 : 5;
            double effectiveness = TypeChart.getEffectiveness(playerPokemon.getType(), computerPokemon.getType());
            playerDamage *= effectiveness;
            computerPokemon.setHp(computerPokemon.getHp() - playerDamage);
            System.out.println(playerPokemon.getName() + " dealt " + playerDamage + " damage to " + computerPokemon.getName() + "!");
            printEffectiveness(effectiveness);
        }

        if (!computerGuarded) {
            int computerDamage = computerAction == 2 ? 10 : 5;
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
