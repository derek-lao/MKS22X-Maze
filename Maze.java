import java.util.*;
import java.io.*;
public class Maze{

    private char[][] maze;
    private boolean animate;//false by default

    /*Constructor loads a maze text file, and sets animate to false by default.

      1. The file contains a rectangular ascii maze, made with the following 4 characters:
      '#' - Walls - locations that cannot be moved onto
      ' ' - Empty Space - locations that can be moved onto
      'E' - the location of the goal (exactly 1 per file)
      'S' - the location of the start(exactly 1 per file)

      2. The maze has a border of '#' around the edges. So you don't have to check for out of bounds!

      3. When the file is not found OR the file is invalid (not exactly 1 E and 1 S) then:
         throw a FileNotFoundException or IllegalStateException
    */
    public Maze(String filename) throws FileNotFoundException{
      //COMPLETE CONSTRUCTOR
      animate = false;
      File text = new File(filename);
      Scanner scanner = new Scanner(text);
      String overall = "";
      int row = 0;
      int col = 0;
      int increment = 0;
      while(scanner.hasNextLine())
      {
        String line = scanner.nextLine();
        if(line.charAt(0) == '#' && line.charAt(line.length()-1) == '#')
        {
          row ++;
          col = line.length();
        }
      }
      scanner = new Scanner(text);
      maze = new char[row][col];
      while(scanner.hasNextLine())
      {
        String line = scanner.nextLine();
        if(line.charAt(0) == '#' && line.charAt(line.length()-1) == '#')
        {
          for(int i = 0; i < line.length(); i ++)
          {
            maze[increment][i] = line.charAt(i);
          }
          increment ++;
          System.out.println(line);
        }
      }
    }

    // private int solveCount = 0;
    private int[] rowIncrements = {0,-1,0,1};
    private int[] colIncrements = {1,0,-1,0};

    private void wait(int millis){
         try {
             Thread.sleep(millis);
         }
         catch (InterruptedException e) {
         }
     }

    public void setAnimate(boolean b){
        animate = b;
    }

    public void clearTerminal(){
        //erase terminal, go to top left of screen.
        System.out.println("\033[2J\033[1;1H");
    }





   /*Return the string that represents the maze.
     It should look like the text file with some characters replaced.
    */
    public String toString(){
      String answer = "";
      for(int r = 0; r < maze.length; r ++)
      {
        for(int c = 0; c < maze[0].length; c ++)
        {
          answer += maze[r][c];
        }
        answer += "\n";
      }
      return answer;
    }


    /*Wrapper Solve Function returns the helper function
      Note the helper function has the same name, but different parameters.
      Since the constructor exits when the file is not found or is missing an E or S, we can assume it exists.
    */
    public int solve(){
      //find the location of the S.
      int srow = -1;
      int scol = -1;
      for(int r = 0; r < maze.length; r ++)
      {
        for(int c = 0; c < maze[0].length; c ++)
        {
          if(maze[r][c] == 'S')
          {
            srow = r;
            scol = c;
            r = maze.length;
            c = maze[0].length;
          }
        }
      }
      //erase the S
      if(srow != -1 && scol != -1)
      {
        maze[srow][scol] = ' ';
        //and start solving at the location of the s.
        return solve(srow,scol);
      }
      System.out.println("No S was found");
       return -1; //so it compiles
    }


    /*
      Recursive Solve function:

      A solved maze has a path marked with '@' from S to E.

      Returns the number of @ symbols from S to E when the maze is solved,
      Returns -1 when the maze has no solution.

      Postcondition:
        The S is replaced with '@' but the 'E' is not.
        All visited spots that were not part of the solution are changed to '.'
        All visited spots that are part of the solution are changed to '@'
    */
    private int solve(int row, int col){ //you can add more parameters since this is private
      //automatic animation! You are welcome.
      if(animate){
          clearTerminal();
          System.out.println(this);
          wait(20);
      }

      //COMPLETE SOLVE
      if(row != -1 && col != -1)
      {
        int count = 0;
        if(maze[row][col] == 'E')
        {
          return count;
        }
        else if(maze[row][col] == ' ')
        {
          maze[row][col] = '@';
          count ++;
          // loop, should never exit if it finds a path.
          for(int a = 0; a < rowIncrements.length; a ++)
          {
            for(int b = 0; b < colIncrements.length; b ++)
            {
              solve(row + a,col + b);
            }
          }
          // if exited the loop, begin backtracking.
          maze[row][col] = '.';
          count --;
        }
        else
        {
          System.out.println("this means that the thing you encountered was super duper wack");
          return -1;
        }
      }
      return -1; // so it compliles
    }

    // private boolean solveHelper(int count, int row, int col){
    //   // bug might arise from this first boolean!!!
    //   if(maze[row][col] == 'E')
    //   {
    //     return true;
    //   }
    //   else if(maze[row][col] == '@' || maze[row][col] == '.' || maze[row][col] == '#')
    //   {
    //     return false;
    //   }
    //   else if(maze[row][col] == 'S' || maze[row][col] == ' ')
    //   {
    //     maze[row][col] = '@';
    //
    //     // loop, should never exit if it finds a path.
    //     for(int a = 0; a < rowIncrements.length; a ++)
    //     {
    //       for(int b = 0; b < colIncrements.length; b ++)
    //       {
    //         if(solveHelper(count + 1,row + a,col + b))
    //         {
    //           return true;
    //         }
    //       }
    //     }
    //     // if exited the loop, begin backtracking.
    //     maze[row][col] = '.';
    //     return false;
    //   }
    //   else
    //   {
    //     System.out.println("this means that the thing you encountered was super duper wack");
    //     return false;
    //   }
    // }

}
