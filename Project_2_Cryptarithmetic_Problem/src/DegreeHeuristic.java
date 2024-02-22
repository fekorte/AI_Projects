import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class DegreeHeuristic {
  private DegreeHeuristic(){}

  public static Variable calculateDegreeHeuristic(List<Variable> minMRVVariables, CSP csp){
    //List to save all variables with the max degree heuristic value
    List<Variable> maxDegreeVariables = new ArrayList<>();
    //Current max degree heuristic value
    int maxDegreeValue = Integer.MIN_VALUE;
    for(Variable minMRVVariable : minMRVVariables){
      /*Map to save all unassigned variables that share a constraint with the current minMRVVariable
      Map was chosen in order to prevent duplicate values*/
      Map<Character, Variable> sharedConstraintVariables = new HashMap<>();

      //Iterate through all constraints and check if the variable is inside the constraint
      for(Constraint constraint : csp.getConstraints()){
        if(constraint.hasVariable(minMRVVariable)){
          /*If the constraint includes the variable, we add all unassigned variables of the constraint
          to the map*/
          for(Variable constraintVariable : constraint.getUnassignedVariablesOfConstraint())
            sharedConstraintVariables.putIfAbsent(constraintVariable.getChar(), constraintVariable);
        }
      }
      /*Remove the variable itself from the map,
      since we don't count it to measure the variables' degree heuristic value*/
      sharedConstraintVariables.remove(minMRVVariable.getChar());

      //Check if we found a variable with a new max degree heuristic value
      if (sharedConstraintVariables.size() > maxDegreeValue) {
        //If yes, we clear the maxDegreeVariables list and add the newly found variable to it
        maxDegreeValue = sharedConstraintVariables.size();
        maxDegreeVariables.clear();
        maxDegreeVariables.add(minMRVVariable);
      } else if (sharedConstraintVariables.size() == maxDegreeValue) {
        //Variable has the same degree heuristic value as the current maximum, add it to the list
        maxDegreeVariables.add(minMRVVariable);
      }
    }

    /*Return result of the degree heuristic:
    If we have only one variable in the maxDegreeVariables we return it,
    if we have several competing variables with the same degree heuristic value, we pick a random
    variable.*/
    return maxDegreeVariables.size() == 1 ?
        maxDegreeVariables.get(0) :
        maxDegreeVariables.get(new Random().nextInt(maxDegreeVariables.size()));
  }
}
