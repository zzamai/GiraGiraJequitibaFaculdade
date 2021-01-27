package data;

import player.Player;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class GameDataController implements IGameDataController {

    private final String WORDS_PATH = "./src/data/txtfiles/words.txt";
    private final String SENTENCES_PATH = "./src/data/txtfiles/sentences";
    private final String MATCHES_PATH = "./src/data/txtfiles/matches.txt";
    private final String PLAYER_MATCHES_PATH = "./src/data/txtfiles/matchesbyplayer/";
    private final String TOP_FIVE_PATH = "./src/data/txtfiles/topfive.txt";

    private InputStream is;
    private InputStreamReader isr;
    private BufferedReader reader;

    private OutputStream matchOs;
    private OutputStreamWriter matchOsw;
    private BufferedWriter matchWriter;

    private OutputStream playerMatchOs;
    private OutputStreamWriter playerMatchOsw;
    private BufferedWriter playerMatchWriter;

    private Map<String, Map<String, List<String>>> map;

    public GameDataController() {
        map = new HashMap<>();
        map.put(Categories.WORDS, new HashMap<>());
        map.put(Categories.SENTENCES, new HashMap<>());

        putWords();
        putSentences();
    }

    public void writeMatch(List<Player> players) {
        StringBuilder match = new StringBuilder();
        List<StringBuilder> playerMatch = new ArrayList<>();

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:MM:ss");

        match.append("Informações da partida(");
        match.append(LocalDateTime.now().format(dtf));
        match.append("): \n");

        for (Player player: players) {
            match.append(player.getName());
            match.append(" fez ");
            match.append(player.getPontuation());
            match.append(" pontos.\n");

            playerMatch.add(new StringBuilder());
        }

        for (int i = 0; i < players.size(); i++) {

            StringBuilder cpm = playerMatch.get(i);
            Player cp = players.get(i);

            cpm.setLength(0);

            cpm.append("Informações da partida(");
            cpm.append(LocalDateTime.now().format(dtf));
            cpm.append("): \n");

            cpm.append(cp.getName());
            cpm.append(" fez ");
            cpm.append(cp.getPontuation());
            cpm.append(" pontos.\n");

            playerMatch.add(cpm);
        }

        match.append("\n\n");

        try {
            matchOs = new FileOutputStream(MATCHES_PATH, true);
            matchOsw = new OutputStreamWriter(matchOs);
            matchWriter = new BufferedWriter(matchOsw);

            for (int i = 0; i < players.size(); i++) {
                Player cp = players.get(i);

                try {
                    playerMatchOs = new FileOutputStream((PLAYER_MATCHES_PATH + cp.getName() + ".txt"), true);
                    playerMatchOsw = new OutputStreamWriter(playerMatchOs);
                    playerMatchWriter = new BufferedWriter(playerMatchOsw);

                    playerMatchWriter.append(playerMatch.get(i).toString());
                    playerMatchWriter.append("\n\n");
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        playerMatchWriter.close();
                        playerMatchOsw.close();
                        playerMatchOs.close();
                    }catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            matchWriter.append(match.toString());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                matchWriter.close();
                matchOsw.close();
                matchOs.close();
            }catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void putWords() {
        try {

            is = new FileInputStream(WORDS_PATH);
            isr = new InputStreamReader(is);
            reader = new BufferedReader(isr);

            String line;
            String[] splitLine;
            reader.readLine();
            while((line = reader.readLine()) != null) {
                splitLine = line.split(";");
                if(!map.get(Categories.WORDS).containsKey(splitLine[0])) {
                    map.get(Categories.WORDS).put(splitLine[0], new ArrayList<>());
                    map.get(Categories.WORDS).get(splitLine[0]).add(splitLine[1]);
                } else {
                    map.get(Categories.WORDS).get(splitLine[0]).add(splitLine[1]);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
                isr.close();
                is.close();
            }catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void putSentences() {
        try {
            is = new FileInputStream(SENTENCES_PATH);
            isr = new InputStreamReader(is);
            reader = new BufferedReader(isr);

            String line;
            String[] splitLine;
            reader.readLine();
            while((line = reader.readLine()) != null) {
                splitLine = line.split(";");
                if(!map.get(Categories.SENTENCES).containsKey(splitLine[0])) {
                    map.get(Categories.SENTENCES).put(splitLine[0], new ArrayList<>());
                    map.get(Categories.SENTENCES).get(splitLine[0]).add(splitLine[1]);
                } else {
                    map.get(Categories.SENTENCES).get(splitLine[0]).add(splitLine[1]);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
                isr.close();
                is.close();
            }catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public Map<String, Map<String, List<String>>> getMap() {
        return map;
    }
}
