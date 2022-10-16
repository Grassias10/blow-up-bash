import java.util.*;
import java.util.ArrayList;
import java.net.Socket;

class Player {
  String playerName = "";
  int lives = 3;
  ArrayList<String> lettersAvail = new ArrayList<String>();
  boolean isDead = false;
  boolean isTurn = true;
  GUI gui = new GUI();
  Client client;
  Socket socket;
  String guess = "";
  Thread thread;
  String data = "";
  ArrayList<String> names = new ArrayList<String>();

  public Player(Socket socket1, String name) {
    playerName = name;
    socket = socket1;
    client = new Client(socket);
    thread = new Thread(client);
    thread.start();
    try {
      Thread.sleep(10);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    client.sendGuess(name);
    // System.out.println("hello!");
    theLoop();
  }

  public void getGuess() {
    // if (gui.isValid()) {
    // guess = gui.sendCInput();
    // System.out.println(guess);
    // gui.updateLives(lives - 1);
    // System.out.println(lives);
  }

  // }

  public void letterAdd() {
    lettersAvail.add("A");
    lettersAvail.add("B");
    lettersAvail.add("C");
    lettersAvail.add("D");
    lettersAvail.add("E");
    lettersAvail.add("F");
    lettersAvail.add("G");
    lettersAvail.add("H");
    lettersAvail.add("I");
    lettersAvail.add("J");
    lettersAvail.add("I");
    lettersAvail.add("K");
    lettersAvail.add("L");
    lettersAvail.add("M");
    lettersAvail.add("N");
    lettersAvail.add("O");
    lettersAvail.add("P");
    lettersAvail.add("Q");
    lettersAvail.add("R");
    lettersAvail.add("S");
    lettersAvail.add("T");
    lettersAvail.add("U");
  }

  public int lifeRemove(boolean timeGone) {
    if (timeGone) {
      lives--;
    }
    return lives;
  }

  public int lifeAdd() {
    if (lettersAvail.size() == 0) {
      lives++;
    }
    return lives;
  }

  public ArrayList<String> updateUsedLetters(String word) {
    for (int j = 0; j < word.length(); j++) {
      String letter = word.substring(j, j + 1);

      for (int i = 0; i < lettersAvail.size(); i++) {
        String a = lettersAvail.get(i);
        if (letter.equals(a)) {
          lettersAvail.remove(i);
        }
      }

      if (lettersAvail.size() == 0) {
        letterAdd();
      }

    }
    /*
     * for (String i : lettersAvail) {
     * System.out.print(i + " ");
     * }
     */
    gui.updateLetters(lettersAvail);
    return lettersAvail;
  }

  public String sendGuess() {
    return guess;
  }
  /*
   * public ArrayList<String> sepLetters(String word) {
   * ArrayList<String> letters = new ArrayList<String>();
   * for (int i = 0; i < word.length(); i++) {
   * letters.add(word);
   * }
   * return letters;
   * }
   */

  public ArrayList<String> sendLetters() {
    return lettersAvail;
  }

  public void sendLives() {
    gui.updateLives(lives);
  }

  public boolean sendTurn() {
    return isTurn;
  }

  public void sendPrompts() {
    gui.setPrompt(data);
  }

  public void sendNames(String[] names) {
    gui.setNames(names);
  }

  public void theLoop() {
    // System.out.print("is dead =" + isDead);

    while (!isDead) {
      guess = gui.sendCInput();

      if (!guess.equalsIgnoreCase("")) {
        if (guess != null) {
          // System.out.println(guess);
        }
        client.sendGuess(guess);
        gui.resetGuesses();
        // System.out.println("Hello!");
        data = client.data;
        String[] dataArr;

        if (data != null) {
          dataArr = data.split(",");
        } else {
          break;
        }

        if (dataArr[0].equals("names")) {
          sendNames(dataArr);
        }

        if (dataArr[0].equals("lives")) {
          sendLives();
        }

        if (dataArr[0].equals("correct")) {

        }

      }
      try {
        Thread.sleep(10);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }

}
