abstract class AbstractPokemon {
    protected String name;
    protected int level;
    protected int hp;

    public AbstractPokemon(String name, int level, int hp) {
        this.name = name;
        this.level = level;
        this.hp = hp;
    }

    public String getName() { return name; }
    public int getLevel() { return level; }
    public int getHp() { return hp; }
    public void setHp(int hp) { this.hp = Math.max(hp, 0); }

    public abstract void attack(AbstractPokemon enemy);

    // Heal method
    public void heal(int amount) {
        this.hp += amount;
        System.out.println(name + " healed by " + amount + " HP. Current HP: " + hp);
    }

    // Level Up method
    public void levelUp(int levels) {
        this.level += levels;
        System.out.println(name + " leveled up by " + levels + "! Current Level: " + level);
    }
}

