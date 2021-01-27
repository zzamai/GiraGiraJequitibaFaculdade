package player;

import java.util.Comparator;

public class PlayerDesc implements Comparator<Player> {
    @Override
    public int compare(Player o1, Player o2) {
        return o2.getPontuation() - o1.getPontuation();
    }
}
