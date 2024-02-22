import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class Main {

  public static void main(String[] args) {
    String inputFile = args[0];
    String outputFile = "OutputFor" + inputFile;

    int[][][] initialState = readStateFromFile(inputFile, 1, 11);
    int[][][] goalState = readStateFromFile(inputFile, 13, 23);

    AStarSearch searchAlgorithm = new AStarSearch();
    List<PuzzleState> solutionPath = searchAlgorithm.performAStarSearch(initialState, goalState);

    if(solutionPath.isEmpty()){
      System.out.println("No solution found for input file.");
    } else {
      System.out.println("Solution found for input file.");
      writeOutputToFile(initialState, goalState, outputFile, solutionPath,
          searchAlgorithm.getTotalNodesGenerated());
    }

  }

  public static int[][][] readStateFromFile(String inputFilePath, int startLine, int endLine) {
    try (BufferedReader reader = new BufferedReader(new FileReader("../" + inputFilePath))) {
      //Create the state space which is of dimensions 3 * 3 * 3
      int[][][] state = new int[3][3][3];
      int lineNum = 1; //Start at line 1

      //Skip lines before startLine
      while (lineNum < startLine) {
        reader.readLine();
        lineNum++;
      }

      //Read state from file
      for (int k = 0; k < 3 && lineNum <= endLine; k++) {
        for (int i = 0; i < 3; i++, lineNum++) {
          String[] line = reader.readLine().split(" ");
          for (int j = 0; j < 3; j++) {
            state[k][i][j] = Integer.parseInt(line[j]);
          }
        }
        if (lineNum <= endLine) {
          reader.readLine(); //skip the blank line in between the layers
          lineNum++;
        }
      }

      return state;
    } catch (IOException e) {
      System.out.println(e.getMessage());
    }
    return new int[0][0][0];
  }

  public static void writeOutputToFile(int[][][] initialState, int[][][] goalState,
      String outputFile, List<PuzzleState> solutionPath, int totalNodesGenerated) {

    try (BufferedWriter writer = new BufferedWriter(new FileWriter("../" + outputFile))) {
      StringBuilder output = new StringBuilder();

      //Add lines regarding initial state
      appendStateLines(output, initialState);
      //Add lines regarding the goal state
      appendStateLines(output, goalState);
      //Add a blank line
      output.append("\n");

      //Add depth level of the shallowest goal node
      int shallowestNodeDepth = solutionPath.get(solutionPath.size() - 1).getDepth();
      output.append(shallowestNodeDepth).append("\n");

      //Add total number of nodes generated
      output.append(totalNodesGenerated).append("\n");

      // Add solution
      for (PuzzleState state : solutionPath) {
        if(state.getAction() != null) {
          Action action = state.getAction();
          output.append(action).append(" ");
        }
      }
      output.append("\n");

      //Add f(n) values of the nodes along the solution path, from root node to goal node
      for (PuzzleState state : solutionPath) {
        int fValue = state.getFValue();
        output.append(fValue).append(" ");
      }
      output.append("\n");

      //Write the output to the file
      writer.write(output.toString());

    } catch (IOException e) {
      System.out.println(e.getMessage());
    }
  }

  public static void appendStateLines(StringBuilder output, int[][][] state) {
    for (int[][] layer : state) {
      for (int[] row : layer) {
        for (int val : row) {
          output.append(val).append(" ");
        }
        output.append("\n");
      }
      output.append("\n");
    }
  }
}