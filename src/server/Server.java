package server;

import common.Exceptions;
import common.GameProtocol;
import common.Duplexer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * A class that provides a multithreaded server to play a matching game
 *
 * @author Josh Greco
 */

public class Server implements Runnable {
    private static String port;
    private Duplexer duplexer;
    private String[] input;
    private boolean loop = true;
    private int clientNum = 1;


    public Server(Duplexer duplexer) throws
            Exceptions, IOException {
        this.duplexer = duplexer;
        System.out.println("Concentration server starting on port " + port);
        System.out.println("Client #" + clientNum + ": Client " + clientNum + " connected: " + ".....");
        board = new ConcentrationBoard(DIM, true);
        String phrase1 = String.format(ConcentrationProtocol.BOARD_DIM_MSG,
                DIM);

        duplexer.send(phrase1);
    }

    /**
     * main game loop. overrides the run function implemented by runnable
     */
    @Override
    public void run(){
        System.out.println("Client #" + clientNum + " started...");
        while (loop) {

            System.out.println("Client #" + clientNum + ":\n" + board.toString());

            String receive = null;
            try {
                receive = duplexer.receive();
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
            input = receive.split(" ");
            switch (input[0]) {
                case (ConcentrationProtocol.REVEAL):
                    ConcentrationCard card1 = null;
                    try {

                        System.out.println();

                        card1 = board.getCard(Integer.parseInt(input[1]),
                                Integer.parseInt(input[2]));
                    } catch (ConcentrationException e) {
                        duplexer.send(ConcentrationProtocol.ERROR +
                                " " + e.getMessage());
                    }

                    System.out.println("received: " + String.format(ConcentrationProtocol.REVEAL_MSG, Integer.parseInt(input[1]), Integer.parseInt(input[2])));

                    String cardMsg = ConcentrationProtocol.CARD + " " +
                            input[1] + " " + input[2] + " " + card1.getLetter();
                    ConcentrationBoard.CardMatch output = null;

                    System.out.println("sending: " + String.format(ConcentrationProtocol.CARD_MSG,
                            Integer.parseInt(input[1]), Integer.parseInt(input[2]), card1.getLetter()));

                    try {
                        output = board.reveal(Integer.parseInt(input[1]),
                                Integer.parseInt(input[2]));
                    } catch (ConcentrationException e) {
                        duplexer.send(ConcentrationProtocol.ERROR + " "
                                + e.getMessage());
                    }
                    duplexer.send(cardMsg);
                    if (output.isReady()) {
                        if (output.isMatch()) {
//                            System.out.println(ConcentrationProtocol.MATCH);
                            System.out.println("sending: " +
                                    String.format(ConcentrationProtocol.MATCH_MSG,
                                            output.getCard1().getRow(), output.getCard1().getCol(),
                                            output.getCard2().getRow(),
                                            output.getCard2().getCol()));

                            duplexer.send(ConcentrationProtocol.MATCH);
                            if (board.gameOver()) {
                                duplexer.send(ConcentrationProtocol.GAME_OVER);
                            }

                        } else {
                            duplexer.send(ConcentrationProtocol.MISMATCH +
                                    " " + output.getCard1().getRow() + " " +
                                    output.getCard1().getCol() + " " +
                                    output.getCard2().getRow() + " " +
                                    output.getCard2().getCol());
                            System.out.println("sending: " +
                                    String.format(ConcentrationProtocol.MISMATCH_MSG,
                                            output.getCard1().getRow(), output.getCard1().getCol(),
                                            output.getCard2().getRow(),
                                            output.getCard2().getCol()));
                        }
                    }
                    break;
                default:
                    duplexer.send(ConcentrationProtocol.ERROR + " " + receive);
                    loop = false;
                    break;
            }
        }
        System.out.println("Client ending...");
    }


    public static void main(String[] args) throws IOException,
            ConcentrationException {
        port = args[0];
        if (args.length != 2) {
            System.out.println("Usage: java ConcentrationServer port DIM");
        } else {
            ServerSocket server = new ServerSocket(Integer.parseInt(args[0]));
            while (true) {
                Socket socket = server.accept();
                Duplexer duplexer = new Duplexer(socket);
                Thread thread = new Thread(new ConcentrationServer(duplexer,
                        Integer.parseInt(args[1])));
                thread.start();
            }
        }
    }
}

    @Override
    public void run() {

    }
}
