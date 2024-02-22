import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BacktrackingSearch {
  public Map<Character, Integer> backtrackSearch(CSP csp){
    return backtrack(csp, new Assignment(csp));
  }

  private Map<Character, Integer> backtrack(CSP csp, Assignment assignment) {
    if (assignment.isComplete())
      return assignment.getAssignmentMap(); //Return solution

    //Perform MRV and degree heuristic to select a variable
    Variable selectedVar = selectUnassignedVariable(csp, assignment);

    //Iterate through all legal domain values of the selectedVar
    List<Integer> orderedDomainValues = (selectedVar.getLegalValues());
    //Sort in ascending order
    Collections.sort(orderedDomainValues);

    for (Integer value : orderedDomainValues) {
      //Check for consistency, only add value to assignment if assignment is consistent
      if (assignment.isConsistent(selectedVar, value)) {
        assignment.addValueToAssignment(selectedVar, value);
        //Recursive call
        Map<Character, Integer> result = backtrack(csp, assignment);
        //If we enter this part of the code, we must have encountered a solution or a failure
        if (!result.isEmpty()) //Check if we found a solution and return it if yes
          return result;
        //If we haven't found a solution, we remove the selectedVar from the assignment
        assignment.removeValueFromAssignment(selectedVar);
      }
    }
    return new HashMap<>(); //Return failure
  }
  private Variable selectUnassignedVariable(CSP csp, Assignment assignment) {
    return MRV.calculateMinimumRemainingValue(csp, assignment);
  }
}
