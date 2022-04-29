import java.util.*;
public class LogicGame {

    private static final String ENT_NAME = "Enter your name:\n";
    private static final List<Integer> TOP_ROW = Arrays.asList(1,2,3);
    private static final List<Integer> MIDDLE_ROW = Arrays.asList(4,5,6);
    private static final List<Integer> LOW_ROW = Arrays.asList(7,8,9);
    private static final List<Integer> LEFT_COLUMN = Arrays.asList (1,4,7);
    private static final List<Integer> MIDDLE_COLUMN = Arrays.asList(2,5,8);
    private static final List<Integer> RIGHT_COLUMN = Arrays.asList (3,6,9);
    private static final List<Integer> CROSS_LEFT_RIGHT = Arrays.asList (1,5,9);
    private static final List<Integer> CROSS_RIGHT_LEFT = Arrays.asList (3,5,7);

    static int numberOfGame;
    static ArrayList<Integer> firstPlayerPos = new ArrayList<>();
    static ArrayList<Integer> secondPlayerPosition = new ArrayList<>();
    static String nameFirstPlayer;
    static String nameSecondPlayer;
    static int countWinsFirstPlayer = 0;
    static int countWinsSecondPlayer = 0;
    static char [][] gameBoard = {{' ', '|', ' ', '|', ' '},
            {'-', '+', '-', '+', '-'},
            {' ', '|', ' ', '|', ' '},
            {'-', '+', '-', '+', '-'},
            {' ', '|', ' ', '|', ' '}};
    static Scanner scan = new Scanner(System.in);

    public static void allGame(){
        enterNameFirstGamer();
        enterNameSecondGamer();
        enterNumberOfGame();
        gameProcess();
    }

    private static void enterNameFirstGamer() {

        System.out.println(ENT_NAME);
        nameFirstPlayer = scan.nextLine();
        if (!nameFirstPlayer.isEmpty()){
            nameFirstPlayer = Validate.extracted(nameFirstPlayer);
        }
        while (nameFirstPlayer.isEmpty()){
            System.out.println(ENT_NAME);
            nameFirstPlayer = scan.nextLine();
        }
        Validate.extracted(nameFirstPlayer);
    }

    private static void enterNameSecondGamer() {
        System.out.println("Enter name second player. If you want play" +
                "with computer enter - CPU: \n");
        nameSecondPlayer = scan.nextLine();
        if (!nameSecondPlayer.isEmpty()) {
            nameSecondPlayer = Validate.extracted(nameSecondPlayer);
        }
        while (nameSecondPlayer.isEmpty()) {
            System.out.println(ENT_NAME);
            nameSecondPlayer = scan.nextLine();
            Validate.extracted(nameSecondPlayer);
        }
    }

    private static void enterNumberOfGame() {
        System.out.println("Enter number of games for end game");
        numberOfGame = Validate.validateNumberInt(scan);

        while (numberOfGame % 2 == 0){
            System.out.println("To avoid a draw, enter an odd number");
            numberOfGame = Validate.validateNumberInt(scan);
        }
    }

    private static void gameProcess() {
        int fastWin = (numberOfGame + 1)/2;
        while (countWinsSecondPlayer + countWinsFirstPlayer < numberOfGame) {
            System.out.println(nameFirstPlayer + ": " + countWinsFirstPlayer);
            System.out.println(nameSecondPlayer + ": " + countWinsSecondPlayer);
            if(countWinsFirstPlayer == fastWin || countWinsSecondPlayer == fastWin){
                break;
            }
            playerPlay(nameFirstPlayer);
            String result = checkWinner();
            if (result.length() > 0) {
                System.out.println(result);
                continue;
            }
            printGameBoard(gameBoard);
            System.out.println("Opponent's turn " + nameSecondPlayer);
            if (nameSecondPlayer.equalsIgnoreCase("CPU")) {
                cpuPlay(nameSecondPlayer);
            } else {
                playerPlay(nameSecondPlayer);
            }
            System.out.println(checkWinner());
        }
    }

    private static void playerPlay(String competitor) {
        printGameBoard(gameBoard);
        System.out.println("Enter your placement (1-9) " + competitor);
        int playerPosition = Validate.validateNumberInt(scan) ;
        while (0 > playerPosition || playerPosition > 9){
            System.out.println("Enter NUMBER (1-9): ");
            playerPosition = Validate.validateNumberInt(scan);
        }
        while (firstPlayerPos.contains(playerPosition) || secondPlayerPosition.contains(playerPosition)) {
            System.out.println("Position taken! Enter a correct");
            playerPosition = Validate.validateNumberInt(scan);
        }
        placeXorO(gameBoard, playerPosition, competitor);
    }

    private static void cpuPlay(String competitor){
        Random rand = new Random();
        int cpuPosition = rand.nextInt(9) + 1;
        while (firstPlayerPos.contains(cpuPosition) || secondPlayerPosition.contains(cpuPosition)){
            System.out.println("Position taken! Enter a correct");
            cpuPosition = rand.nextInt(9) + 1;
        }
        placeXorO(gameBoard, cpuPosition, competitor);
    }

    private static void placeXorO(char[][] gameBoard, int position, String user) {
        char symbol;
        if(user.equals(nameFirstPlayer)){
            symbol = 'X';
            firstPlayerPos.add(position);
        }else{
            symbol = '0';
            secondPlayerPosition.add(position);
        }
        switch (position) {
            case 1 -> gameBoard[0][0] = symbol;
            case 2 -> gameBoard[0][2] = symbol;
            case 3 -> gameBoard[0][4] = symbol;
            case 4 -> gameBoard[2][0] = symbol;
            case 5 -> gameBoard[2][2] = symbol;
            case 6 -> gameBoard[2][4] = symbol;
            case 7 -> gameBoard[4][0] = symbol;
            case 8 -> gameBoard[4][2] = symbol;
            case 9 -> gameBoard[4][4] = symbol;
            default -> throw new IllegalStateException("Unexpected value: " + position);
        }
    }

    private static void printGameBoard(char[][] gameBoard) {
        for(char[] row : gameBoard){
            for(char c : row){
                System.out.print(c);
            }
            System.out.println();
        }
    }

    private static String checkWinner(){

        Map<String, List<Integer>> victoryConditions = new HashMap<>();

        victoryConditions.put("TopRow", TOP_ROW);
        victoryConditions.put("MiddleRow", MIDDLE_ROW);
        victoryConditions.put("LowRow", LOW_ROW);
        victoryConditions.put("LeftCol", LEFT_COLUMN);
        victoryConditions.put("MiddleCol", MIDDLE_COLUMN);
        victoryConditions.put("RightCol", RIGHT_COLUMN);
        victoryConditions.put("CrossLeftToRight", CROSS_LEFT_RIGHT);
        victoryConditions.put("CrossRightToLeft", CROSS_RIGHT_LEFT);

        for (List<Integer> winning : victoryConditions.values()) {
            if (firstPlayerPos.containsAll(winning)) {
                printGameBoard(gameBoard);
                cleanBoard();
                countWinsFirstPlayer++;
                System.out.println(nameFirstPlayer + " win. Number of wins: " + countWinsFirstPlayer);
                return " ";
            } else if(secondPlayerPosition.containsAll(winning)){
                printGameBoard(gameBoard);
                cleanBoard();
                countWinsSecondPlayer++;
                System.out.println(nameSecondPlayer + " win. Number of wins: " + countWinsSecondPlayer);
                return " ";
            } else if (firstPlayerPos.size() + secondPlayerPosition.size() == 9){
                cleanBoard();
                return "CAT!";
            }
        }
        return "";
    }

    private static void cleanBoard(){
        char clean = ' ';
        gameBoard[0][0] = clean;
        gameBoard[0][2] = clean;
        gameBoard[0][4] = clean;
        gameBoard[2][0] = clean;
        gameBoard[2][2] = clean;
        gameBoard[2][4] = clean;
        gameBoard[4][0] = clean;
        gameBoard[4][2] = clean;
        gameBoard[4][4] = clean;
        firstPlayerPos.clear();
        secondPlayerPosition.clear();
    }
}
