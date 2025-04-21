public class Pokemon {
    private int id;
    private String name;
    private int hp;
    private int attack;
    private int defense;
    private int spAttack;
    private int spDefense;
    private int speed;
    private int baseStats;
    private double grassWeakness;

    // The Constructor for the Pokémon Method itself - Dara
    public Pokemon(int id, String name, int hp, int attack, int defense,
                   int spAttack, int spDefense, int speed, int baseStats,
                   double grassWeakness) {
        this.id = id;
        this.name = name;
        this.hp = hp;
        this.attack = attack;
        this.defense = defense;
        this.spAttack = spAttack;
        this.spDefense = spDefense;
        this.speed = speed;
        this.baseStats = baseStats;
        this.grassWeakness = grassWeakness;
    }

    // The Getters - Dara
    public int getId() { return id; }
    public String getName() { return name; }
    public int getHp() { return hp; }
    public int getAttack() { return attack; }
    public int getDefense() { return defense; }
    public int getSpAttack() { return spAttack; }
    public int getSpDefense() { return spDefense; }
    public int getSpeed() { return speed; }
    public int getBaseStats() { return baseStats; }
    public double getGrassWeakness() { return grassWeakness; }

    // I created an Override toString for easy debugging for us - Dara
    @Override
    public String toString() {
        return name + " (ID: " + id + ", Attack: " + attack + ", Base Stats: " + baseStats + ")";
    }
}
