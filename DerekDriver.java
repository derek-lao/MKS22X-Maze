import java.util.*;
import java.io.*;
public class DerekDriver{
  public static void main(String[] args){
    try
    {
      Maze stuff = new Maze("Maze1.txt");
      stuff.solve();
      System.out.println
    }
    catch(FileNotFoundException e)
    {
      System.out.println("Error: File not found!");
      e.printStackTrace();
    }
  }

}
