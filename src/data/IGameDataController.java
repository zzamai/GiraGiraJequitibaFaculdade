package data;

import player.Player;

import java.util.List;

public interface IGameDataController {

    void writeMatch(List<Player> players);
    void putWords();
    void putSentences();

}
