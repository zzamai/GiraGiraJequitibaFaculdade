package player;

public class Player implements Comparable<Player>{

    private String name;
    private int pontuation;

    public Player(String name) {
        this.name = name;
        pontuation = 0;
    }

    public void increasePontuation(int pontuation) {
        this.pontuation += pontuation;
    }

    public String getName() {
        return name;
    }

    public int getPontuation() {
        return pontuation;
    }

    @Override
    public int compareTo(Player o) {
        return pontuation - o.getPontuation();
    }
}
