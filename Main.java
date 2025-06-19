import java.util.*;

public class Main {
    public static void main(String[] args) {
        Trainer player1 = new Trainer("Ash");
        Trainer Computer = new Trainer("Trainer Rivasa");
        
        // Add Pokémon to both trainers
        player1.addPokemon(new Pokemon("Charmander", 5, 100, "Fire"));
        player1.addPokemon(new Pokemon("Squirtle", 10, 115, "Water"));
        player1.addPokemon(new Pokemon("Pikachu", 15, 95, "Electric"));

        Computer.addPokemon(new Pokemon("Bulbasaur", 5, 115, "Grass"));
        Computer.addPokemon(new Pokemon("Pidgey", 5, 110, "Flying"));
        Computer.addPokemon(new Pokemon("Onix", 5, 120, "Rock"));
        
        Scanner sc = new Scanner(System.in);
        Battle.startPvE(player1, Computer, sc);
    }
}