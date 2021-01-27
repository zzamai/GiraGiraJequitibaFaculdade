package game;

import data.GameDataController;
import player.Player;
import player.PlayerDesc;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class GameWindow extends JFrame implements ActionListener {

    private Game game;
    private int qntPlayer;
    private int qntMatches;
    private boolean isWordsMatchType;

    private JDesktopPane desktop;
    private JMenuBar menuBar;
    private JMenu menuInfo;
    private JMenuItem howToPlay;
    private JMenuItem top5;

    private final String HOW_TO_PLAY_IMAGE_PATH = "./src/images/how_to_play.png";
    private final Icon iconHowToPlay = new ImageIcon(HOW_TO_PLAY_IMAGE_PATH);

    private JPanel panelMainMenu;
    private JPanel panelPlayersSelection;
    private JPanel panelMatchesSelection;
    private JPanel panelPlayersNames;
    private JPanel panelGameViewLeft;
    private JPanel panelGameViewRight;

    private JButton btnBack;

    private JButton btnBegin;
    private JButton btnQuit;

    private JButton btn2Players;
    private JButton btn3Players;
    private JButton btn4Players;

    private JButton btn3Matches;
    private JButton btn5Matches;
    private JButton btn7Matches;

    private List<JLabel> lblPlayersNames;
    private List<JTextField> tfPlayersNames;
    private JButton btnContinue;

    private GameDataController dc;
    private String[] gameWords;
    private JLabel theme;
    private StringBuilder letterToUnderscore;
    private List<String> words;
    private List<JLabel> underWords;
    private String underSentence;
    private String sentence;
    List<List<Integer>> indexes;
    private int[] countPlayer;
    List<Boolean> wrotenWords;

    private final ImageIcon USER_IMG = new ImageIcon("./src/images/img_user.png");
    private JLabel lblUserImg;
    private JLabel lblCurrentUser;
    private JTextField tfUserLetter;
    private JButton btnTryLetter;
    private JLabel lblUserCurrentPoints;

    private JTextArea textAreaAlreadyUsedLetters;
    private List<Character> alreadyUsedLetters;

    private GridBagLayout layout;
    private GridBagConstraints constraints;

    public GameWindow(String titulo) {
        super(titulo);
        criarComponentes();
        ajustarPropriedadesJanela();
    }

    private void criarComponentes() {
        desktop = new JDesktopPane();

        menuBar = new JMenuBar();

        menuInfo = new JMenu("Mais");

        top5 = new JMenuItem("Top 5");
        top5.addActionListener(this);
        howToPlay = new JMenuItem("Como jogar");
        howToPlay.addActionListener(this);

        menuInfo.add(top5);
        menuInfo.addSeparator();
        menuInfo.add(howToPlay);

        menuBar.add(menuInfo);

        setJMenuBar(menuBar);

        add(desktop);

        layout = new GridBagLayout();
        constraints = new GridBagConstraints();

        Border border = BorderFactory.createEtchedBorder();

        panelMainMenu = new JPanel();
        panelMainMenu.setLayout(layout);
        panelMainMenu.setBorder(border);

        panelPlayersSelection = new JPanel();
        panelPlayersSelection.setLayout(layout);
        panelPlayersSelection.setBorder(border);

        panelMatchesSelection = new JPanel();
        panelMatchesSelection.setLayout(layout);
        panelMatchesSelection.setBorder(border);

        panelPlayersNames = new JPanel();
        panelPlayersNames.setLayout(layout);
        panelPlayersNames.setBorder(border);

        panelGameViewLeft = new JPanel();
        panelGameViewLeft.setLayout(layout);
        panelGameViewLeft.setBorder(border);

        panelGameViewRight = new JPanel();
        panelGameViewRight.setLayout(layout);
        panelGameViewRight.setBorder(border);

        btnBegin = new JButton("Iniciar");
        btnQuit = new JButton("Sair");

        btn2Players = new JButton("2 Jogadores");
        btn3Players = new JButton("3 Jogadores");
        btn4Players = new JButton("4 Jogadores");

        btn3Matches = new JButton("3 Partidas");
        btn5Matches = new JButton("5 Partidas");
        btn7Matches = new JButton("7 Partidas");

        lblPlayersNames = new ArrayList<>();
        tfPlayersNames = new ArrayList<>();
        btnContinue = new JButton("Continuar");

        theme = new JLabel("");
        words = new ArrayList<>();
        underWords = new ArrayList<>();

        lblUserImg = new JLabel(USER_IMG);
        lblCurrentUser = new JLabel("");
        tfUserLetter = new JTextField(1);
        btnTryLetter = new JButton("TENTAR");
        lblUserCurrentPoints = new JLabel("Pontos: 0");
        textAreaAlreadyUsedLetters = new JTextArea();
        textAreaAlreadyUsedLetters.setEditable(false);
        alreadyUsedLetters = new ArrayList<>();

        countPlayer = new int[2];

        setLayout(layout);
        setComponentsColorsAndShapes();
        loadGameSetupComponents();
        setButtonsListeners();

        addComponents(panelMainMenu, btnBegin, 0, 1, GridBagConstraints.SOUTH, 1, 1, GridBagConstraints.NONE);
        addComponents(panelMainMenu, btnQuit, 1, 1, GridBagConstraints.NORTH, 1, 1, GridBagConstraints.NONE);
        addComponents(this, panelMainMenu, 0, 0, GridBagConstraints.NORTH, 1, 1, GridBagConstraints.BOTH);

    }

    private void setComponentsColorsAndShapes() {
        btnBegin.setFocusPainted(false);
        btnQuit.setFocusPainted(false);

        btnBegin.setPreferredSize(new Dimension(150, 80));
        btnQuit.setPreferredSize(new Dimension(150, 80));

        btn2Players.setFocusPainted(false);
        btn3Players.setFocusPainted(false);
        btn4Players.setFocusPainted(false);

        btn3Matches.setFocusPainted(false);
        btn5Matches.setFocusPainted(false);
        btn7Matches.setFocusPainted(false);
    }

    private void loadGameSetupComponents() {
        addComponents(panelPlayersSelection, btn2Players, 0, 0, GridBagConstraints.SOUTH, 1, 1, GridBagConstraints.NONE);
        addComponents(panelPlayersSelection, btn3Players, 1, 0, GridBagConstraints.CENTER, 1, 1, GridBagConstraints.NONE);
        addComponents(panelPlayersSelection, btn4Players, 2, 0, GridBagConstraints.NORTH, 1, 1, GridBagConstraints.NONE);

        addComponents(panelMatchesSelection, btn3Matches, 0, 0, GridBagConstraints.SOUTH, 1, 1, GridBagConstraints.NONE);
        addComponents(panelMatchesSelection, btn5Matches, 1, 0, GridBagConstraints.CENTER, 1, 1, GridBagConstraints.NONE);
        addComponents(panelMatchesSelection, btn7Matches, 2, 0, GridBagConstraints.NORTH, 1, 1, GridBagConstraints.NONE);

    }

    private void setButtonsListeners() {
        btnBegin.addActionListener(this);
        btnQuit.addActionListener(this);

        btn2Players.addActionListener(this);
        btn3Players.addActionListener(this);
        btn4Players.addActionListener(this);

        btn3Matches.addActionListener(this);
        btn5Matches.addActionListener(this);
        btn7Matches.addActionListener(this);

        btnContinue.addActionListener(this);
        btnTryLetter.addActionListener(this);
    }

    private void addComponents(Container cont, JComponent comp, int linhay, int colunax, int pos, int lar, int alt, int pre) {
        constraints.gridy = linhay;
        constraints.gridx = colunax;
        constraints.gridwidth = lar;
        constraints.gridheight = alt;
        constraints.anchor = pos;
        constraints.fill = pre;
        constraints.insets = new Insets(10, 10, 10, 10);
        constraints.weightx = 1d;
        constraints.weighty = 1d;
        comp.setFont(new Font("arial",Font.PLAIN , 40));
        layout.setConstraints(comp, constraints);
        cont.add(comp);
    }

    private void ajustarPropriedadesJanela() {
        setVisible(true);
        setSize(1240, 480);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void loadPlayersSelection() {
        remove(panelMainMenu);
        addComponents(this, panelPlayersSelection, 0, 0, GridBagConstraints.NORTH, 1, 1, GridBagConstraints.BOTH);
        revalidate();
    }

    private void loadMatchesSelection() {
        remove(panelPlayersSelection);
        addComponents(this, panelMatchesSelection, 0, 0, GridBagConstraints.NORTH, 1, 1, GridBagConstraints.BOTH);
        revalidate();
    }

    private void loadPlayersNamesScreen(int qntPlayers) {
        remove(panelMatchesSelection);
        for (int i = 0; i < qntPlayers; i++) {
            lblPlayersNames.add(new JLabel("Player "+(i+1)));
            tfPlayersNames.add(new JTextField(10));

            addComponents(panelPlayersNames, lblPlayersNames.get(i), i, 0, GridBagConstraints.EAST, 1, 1, GridBagConstraints.NONE);
            addComponents(panelPlayersNames, tfPlayersNames.get(i), i, 1, GridBagConstraints.WEST, 1, 1, GridBagConstraints.NONE);
        }
        addComponents(panelPlayersNames, btnContinue, qntPlayers, 0, GridBagConstraints.NORTH, 2, 1, GridBagConstraints.NONE);
        addComponents(this, panelPlayersNames, 0, 0, GridBagConstraints.NORTH, 1, 1, GridBagConstraints.BOTH);
        revalidate();
    }

    private void loadGameView() {
        remove(panelPlayersNames);

        addComponents(panelGameViewLeft, theme, 0, 0, GridBagConstraints.SOUTHWEST, 2, 1, GridBagConstraints.BOTH);
        reGame();
        addComponents(this, panelGameViewLeft, 0, 0, GridBagConstraints.NORTH, 1, 1, GridBagConstraints.BOTH);

        if(game.getCurrentGame() == 1) {
            loadGameViewRight();
        }
        revalidate();

    }

    private void loadGameViewRight() {
        lblCurrentUser.setText(game.getPlayers().get(0).getName());
        addComponents(panelGameViewRight, lblUserImg, 0, 0, GridBagConstraints.SOUTH, 3, 2, GridBagConstraints.BOTH);
        addComponents(panelGameViewRight, lblCurrentUser, 2, 1, GridBagConstraints.EAST, 2, 1, GridBagConstraints.BOTH);

        addComponents(panelGameViewRight, tfUserLetter, 3, 0, GridBagConstraints.EAST, 1, 1, GridBagConstraints.BOTH);
        addComponents(panelGameViewRight, btnTryLetter, 3, 1, GridBagConstraints.EAST, 2, 1, GridBagConstraints.BOTH);
        addComponents(panelGameViewRight, lblUserCurrentPoints, 4, 0, GridBagConstraints.EAST, 3, 1, GridBagConstraints.BOTH);
        addComponents(panelGameViewRight, textAreaAlreadyUsedLetters, 5, 0, GridBagConstraints.EAST, 3, 2, GridBagConstraints.BOTH);

        addComponents(this, panelGameViewRight, 0, 2, GridBagConstraints.NORTH, 2, 1, GridBagConstraints.BOTH);
    }
    
    private void garbageGameComponents() {
        dc = null;
        gameWords = null;
        wrotenWords = null;
        indexes = null;
        letterToUnderscore = null;
        alreadyUsedLetters.clear();
        underWords.clear();
    }
    
    private void buildGameComponents() {
        letterToUnderscore = new StringBuilder();

        dc = new GameDataController();
        game.setMap(dc.getMap());
        gameWords = game.shuffleSomeWords();
        theme.setText("O tema é: "+gameWords[0]);
        wrotenWords = new ArrayList<>();
        for (int i = 1; i < gameWords.length; i++) {
            if(gameWords[i] != null) {
                wrotenWords.add(false);
                words.add(gameWords[i]);
                indexes = new ArrayList<>();
                letterToUnderscore.setLength(0);
                    for (int j = 0; j < gameWords[i].length(); j++) {
                        if(gameWords[i].charAt(j) != ' ')
                            letterToUnderscore.append("_");
                        else letterToUnderscore.append(" ");
                    }
            } else break;
            underWords.add(new JLabel(letterToUnderscore.toString()));
            addComponents(panelGameViewLeft, underWords.get(i-1), i, 0, GridBagConstraints.NORTHWEST, 1, 1, GridBagConstraints.BOTH);
        }
    }

    private void reGame() {
        if (game.getCurrentGame() == 1) {
            buildGameComponents();
        } else if(game.getCurrentGame() <= game.getQntMatches()) {
            garbageGameComponents();
            buildGameComponents();
        }else{
            JOptionPane.showMessageDialog(this, "Fim de jogo");
        }
    }

    private void clearGameScreen() {
        panelGameViewLeft.removeAll();
        remove(panelGameViewLeft);
        textAreaAlreadyUsedLetters.setText("");
        loadGameView();
    }

    private void replaceUnderLetters(char c) {
        StringBuilder cleared = new StringBuilder();
        for (int i = 0; i < words.size(); i++) {
            cleared.setLength(0);
            cleared.append(underWords.get(i).getText());
            for (int j = 0; j < indexes.get(i).size(); j++) {
                cleared.setCharAt(indexes.get(i).get(j), c);
            }
            underWords.get(i).setText(cleared.toString());
            int countUnderscore = underWords.get(i).getText().length() - underWords.get(i).getText().replace("_", "").length();

            if(countUnderscore == 1) {
                game.playerGotAWord(countPlayer[0]);
            }
            wrotenWords.set(i, countUnderscore == 0);

            if(!wrotenWords.contains(false)) {
                game.increaseGameCount();
                game.getPlayers().sort(new PlayerDesc());
                JOptionPane.showMessageDialog(this, "Fim de jogo\nO vencedor é: "+ game.getPlayers().get(0).getName());
                dc.writeMatch(game.getPlayers());
                clearGameScreen();
                break;
            }
        }
        revalidate();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource().equals(btnBegin)) {
            loadPlayersSelection();
        } else if(e.getSource().equals(btnQuit)) {
            System.exit(0);
        } else if(e.getSource().equals(btn2Players) || e.getSource().equals(btn3Players) || e.getSource().equals(btn4Players)) {

            if (e.getSource().equals(btn2Players)) this.qntPlayer = 2;
            else if (e.getSource().equals(btn3Players)) this.qntPlayer = 3;
            else this.qntPlayer = 4;

            loadMatchesSelection();
        } else if(e.getSource().equals(btn3Matches) || e.getSource().equals(btn5Matches) || e.getSource().equals(btn7Matches)) {

            if (e.getSource().equals(btn3Matches)) this.qntMatches = 3;
            else if (e.getSource().equals(btn5Matches)) this.qntMatches = 5;
            else this.qntMatches = 7;

            isWordsMatchType = (int) (Math.random() * 2 + 1) == 1;

            loadPlayersNamesScreen(this.qntPlayer);
        } else if (e.getSource().equals(btnContinue)) {
            List<Player> players = new ArrayList<>();

            for (JTextField playerName: tfPlayersNames) {
                players.add(new Player(playerName.getText()));
            }
            countPlayer[0] = 0;
            countPlayer[1] = qntPlayer-1;

            game = new Game(qntPlayer, qntMatches, isWordsMatchType, players);

            loadGameView();
        } else if (e.getSource().equals(btnTryLetter)) {
            String tfLetterUsed = tfUserLetter.getText();

            tfUserLetter.setText("");
            tfUserLetter.requestFocus();

            if (tfLetterUsed.length() == 1) {
                textAreaAlreadyUsedLetters.append(tfLetterUsed);
                char letterUsed = tfLetterUsed.charAt(0);

                if(game.checkIngameLetter(letterUsed) && !alreadyUsedLetters.contains(letterUsed)) {
                    indexes.clear();
                    for (int i = 0; i < words.size(); i++) {
                        System.out.println("palavra "+(i+1));
                        indexes.add(game.getLettersIndexes((i+1), countPlayer[0]));
                        lblUserCurrentPoints.setText("Pontos: "+ game.getPlayers().get(countPlayer[0]).getPontuation());
                    }
                    replaceUnderLetters(letterUsed);
                    alreadyUsedLetters.add(letterUsed);
                } else {
                    if(countPlayer[0] == countPlayer[1]) {
                        countPlayer[0] = 0;
                        lblCurrentUser.setText(game.getPlayers().get(0).getName());
                        lblUserCurrentPoints.setText("Pontos: "+ game.getPlayers().get(countPlayer[0]).getPontuation());
                    }else {
                        lblCurrentUser.setText(game.getPlayers().get(
                                ++countPlayer[0]
                        ).getName());
                        lblUserCurrentPoints.setText("Pontos: "+ game.getPlayers().get(countPlayer[0]).getPontuation());
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Insira apenas/pelo menos uma letra", "Um probleminha", JOptionPane.ERROR_MESSAGE);
            }
        } else if(e.getSource().equals(howToPlay)) {
            JOptionPane.showMessageDialog(null, null, null, JOptionPane.INFORMATION_MESSAGE, iconHowToPlay);
        }
    }
}
