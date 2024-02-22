import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Main {
  /*Reads the first three lines of the file and create the variables for each character*/
  public static List<Variable> readVariablesFromFile(String filePath){
    List<Variable> variables = new ArrayList<>();
    int position = 1;
    try (BufferedReader reader = new BufferedReader(new FileReader("../" + filePath))){
      for (int i = 0; i < 3; i++){
        String line = reader.readLine();
        if (line != null){
          processLine(line, variables, position);
          position += line.length();
        }
      }
    } catch (IOException e) {
      System.out.println(e.getMessage());
    }
    return variables;
  }

  private static void processLine(String line, List<Variable> variables, int position){
    for (char currentChar : line.toCharArray()){
      //Check if a variable with a similar character has already been read, if yes, just add the position to it
      Variable existingVariable = getVariable(variables, currentChar);
      if (existingVariable != null) {
        existingVariable.addPosition(position);
      } else {
        variables.add(new Variable(new ArrayList<>(List.of(position)), currentChar, false));
      }
      position++;
    }
  }

  private static Variable getVariable(List<Variable> variables, char currentChar) {
    for (Variable variable : variables) {
      if (variable.getChar() == currentChar) {
        return variable;
      }
    }
    return null;
  }

  private static List<Character> readCharsFromFile(String inputFilePath){
    List<Character> characters = new ArrayList<>();
    try (BufferedReader reader = new BufferedReader(new FileReader("../" + inputFilePath))) {
      for (int i = 0; i < 3; i++) {
        String line = reader.readLine();
        if (line != null) {
          for (char currentChar : line.toCharArray()){
            characters.add(currentChar);
          }
        }
      }
    } catch (IOException e) {
      System.out.println(e.getMessage());
    }
    return characters;
  }

  private static void writeSolutionToFile(List<Character> characters, String outputFilePath,  Map<Character, Integer> solution) {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter("../" + outputFilePath))) {
      //Write the solution values by replacing characters in the order of the input file
      int num = 0;
      for (Character character : characters) {
        writer.write(String.valueOf(solution.get(character)));
        num++;
        if ((num % 4 == 0) && (num / 4 <= 2)) {
          writer.newLine();
        }
      }
      writer.flush();
      System.out.println("Solution written to: " + outputFilePath);
    } catch (IOException e) {
      System.out.println("Error writing to the output file: " + e.getMessage());
    }
  }

  public static void main(String[] args) {
    String inputFileName = args[0];
    List<Variable> variables = readVariablesFromFile(inputFileName);

    //Create CSP and start backtracking
    CSP csp = new CSP(variables);
    BacktrackingSearch backtrackingSearch = new BacktrackingSearch();
    Map<Character, Integer> solution = backtrackingSearch.backtrackSearch(csp);

    if(solution.isEmpty()){
      System.out.println("No solution found.");
    } else {
      writeSolutionToFile(readCharsFromFile(inputFileName), "OutputFor" + inputFileName, solution);
    }
  }
}