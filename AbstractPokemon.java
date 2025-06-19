abstract class AbstractPokemon {
    protected String name;
    protected int level;
    protected int hp;
    protected int maxHp;

    public AbstractPokemon(String name, int level, int hp) {
        this.name = name;
        this.level = level;
        this.hp = hp;
        this.maxHp = hp;
    }

    public String getName() { return name; }
    public int getLevel() { return level; }
    public int getHp() { return hp; }
    public void setHp(int hp) { this.hp = Math.max(hp, 0); }
    public int getMaxHp() {return maxHp;}
    public abstract void attack(AbstractPokemon enemy);

    // Heal method
    public void heal(int amount) {
        if (hp == maxHp) {
        System.out.println(name + "'s HP is already full. Heal skipped.");
        return;
        }
        int oldHp = hp;
        this.hp = Math.min(hp + amount, maxHp); // Cegah overheal
        System.out.println(name + " healed by " + (hp - oldHp) + " HP. Current HP: " + hp + "/" + maxHp);
    }
    
    // Level Up method
    public void levelUp(int levels) {
        this.level += levels;
        System.out.println(name + " leveled up by " + levels + "! Current Level: " + level);
    }
}

