import java.util.*;

class Trainer {
    private String name;
    private List<Pokemon> pokemons;

    public Trainer(String name) {
        this.name = name;
        this.pokemons = new ArrayList<>();
    }

    public void addPokemon(Pokemon p) {
        pokemons.add(p);
    }

    public List<Pokemon> getPokemons() {
    return pokemons;
}

    public Pokemon choosePokemon(Scanner sc) {
        System.out.println("\n" + name + ", choose your Pokémon:");
        for (int i = 0; i < pokemons.size(); i++) {
            Pokemon p = pokemons.get(i);
            System.out.println((i + 1) + ". " + p.getName() + " (Lv." + p.getLevel() + ", HP: " + p.getHp() + ", Type: " + p.getType() + ")");
        }
        int choice;
        do {
            System.out.print("Choose (1-" + pokemons.size() + "): ");
            choice = sc.nextInt();
        } while (choice < 1 || choice > pokemons.size());

        return pokemons.get(choice - 1);
    }

    public Pokemon chooseRandomPokemon() {
    return pokemons.stream().filter(p -> !p.isFainted()).findAny().orElse(null);
}
     public Pokemon chooseRandomPokemonExcluding(Pokemon exclude) {
        List<Pokemon> available = pokemons.stream()
            .filter(p -> !p.isFainted() && p != exclude)
            .toList();
        if (available.isEmpty()) return exclude;
        return available.get(new Random().nextInt(available.size()));
    }
    
    public boolean hasAvailablePokemon() {
        return pokemons.stream().anyMatch(p -> !p.isFainted());
    }

    public void removeFaintedPokemon(Pokemon pokemon) {
    pokemons.remove(pokemon);
    System.out.println(pokemon.getName() + " has been removed from the list of available Pokémon.");
}

    public String getName() { return name; }
}