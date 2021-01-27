package game;

import data.Categories;
import data.utils.CharUtils;
import player.Player;

import java.util.*;

public class Game {

    private int qntPlayers;
    private int qntMatches;
    private boolean isWordsMatchType;

    private List<Player> players;

    private int numberOfWords;
    private String[] words;
    private String currentCategory;
    private Map<String, Map<String, List<String>>> map;

    private int currentGame;

    private char currentLetter;

    public Game(int qntPlayers, int qntMatches, boolean isWordsMatchType, List<Player> players) {
        this.qntPlayers = qntPlayers;
        this.qntMatches = qntMatches;
        this.isWordsMatchType = isWordsMatchType;
        this.players = players;

        currentGame = 1;

        numberOfWords = (int) (Math.random() * 3) + 1;

        System.out.println("The game got started with:\n "+ qntPlayers +" players\n "+ qntMatches +" matches\n" +
                "and it is "+isWordsMatchType+" for Words Match");
    }

    public void setMap(Map<String, Map<String, List<String>>> map) {
        this.map = map;
    }

    private void setCategory() {

        int randomCategory = (int) (Math.random() * (map.get(Categories.WORDS).size()));
        Iterator it = map.get(Categories.WORDS).keySet().iterator();
        for (int i = 0; i < randomCategory; i++) it.next();

        currentCategory = (String) it.next();
    }

    public String[] shuffleSomeWords() {
        setCategory();
        words = new String[4];
        words[0] = currentCategory;
        for (int i = 1; i <= numberOfWords; i++) {
            words[i] = map.get(Categories.WORDS).get(currentCategory).get(
                    (int) (Math.random() * 10)
            );
        }
        return words;
    }

    public boolean checkIngameLetter(char letter) {
        letter = CharUtils.decapitalizeChar(letter);

        String word;
        for (int i = 1; i < (numberOfWords+1); i++) {
            word = words[i];
            for (int j = 0; j < word.length(); j++) {
                if(word.charAt(j) == letter) {
                    currentLetter = letter;
                    return true;
                }
            }
        }

        return false;
    }

    public List<Integer> getLettersIndexes(int index, int player) {

        List<Integer> indexes = new ArrayList<>();
        String word = words[index];

        for (int j = 0; j < word.length(); j++) {
            if(word.charAt(j) == currentLetter) {
                indexes.add(j);
                players.get(player).increasePontuation(10);
                System.out.println(j + " = " + word.charAt(j));
            }
        }

        return indexes;
    }

    public void increaseGameCount() {
        currentGame++;
    }

    public int getCurrentGame() {
        return currentGame;
    }

    public void playerGotAWord(int player) {
        players.get(player).increasePontuation(100);
    }

    public List<Player> getPlayers() {
        return players;
    }

    public int getQntMatches() {
        return qntMatches;
    }
}
