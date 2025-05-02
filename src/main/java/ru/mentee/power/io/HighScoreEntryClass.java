package ru.mentee.power.io;


import java.util.Objects;

class HighScoreEntryClass {

  private final String playerName;
  private final int score;


  public HighScoreEntryClass(String playerName, int score) {
    this.playerName = playerName;
    this.score = score;
  }

  public String getPlayerName() {
    return playerName;
  }

  public int getScore() {
    return score;
  }

  @Override
  public String toString() {
    return "HighScoreEntryClass{" + "playerName='" + playerName + '\'' + ", score=" + score + '}';
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    HighScoreEntryClass that = (HighScoreEntryClass) obj;
    return score == that.score && Objects.equals(playerName, that.playerName);
  }

  @Override
  public int hashCode() {
    return Objects.hash(playerName, score);
  }

}

