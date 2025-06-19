class Pokemon extends AbstractPokemon {
    private String type;

    public Pokemon(String name, int level, int hp, String type) {
        super(name, level, hp);
        this.type = type;
    }

    public String getType() { return type; }

    @Override
    public void attack(AbstractPokemon enemy) {
        int damage = calculateDamage(enemy, false);
        printEffectiveness(this.type, ((Pokemon) enemy).getType());
        System.out.println(name + " uses regular attack on " + enemy.getName() + " for " + damage + " damage!");
        enemy.setHp(enemy.getHp() - damage);
    }

    public void attack(AbstractPokemon enemy, int extraDamage) {
        int damage = calculateDamage(enemy, true) + extraDamage;
        printEffectiveness(this.type, ((Pokemon) enemy).getType());
        System.out.println(name + " uses special attack on " + enemy.getName() + " for " + damage + " damage!");
        enemy.setHp(enemy.getHp() - damage);
    }

    public int calculateDamage(AbstractPokemon enemy, boolean isSpecial) {
        int baseDamage = level * (isSpecial ? 6 : 5);
        double multiplier = TypeChart.getEffectiveness(this.type, ((Pokemon) enemy).getType());
        return (int) (baseDamage * multiplier);
    }

    private void printEffectiveness(String attackerType, String defenderType) {
        double multiplier = TypeChart.getEffectiveness(attackerType, defenderType);
        if (multiplier == 2.0) {
            System.out.println("It's super effective!");
        } else if (multiplier == 0.5) {
            System.out.println("It's not very effective...");
        }
    }

    public boolean isFainted() {
        return hp <= 0;
    }
}