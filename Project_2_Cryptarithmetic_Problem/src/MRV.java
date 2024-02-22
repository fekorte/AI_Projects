import java.util.ArrayList;
import java.util.List;

public class MRV {
  private MRV(){}
  public static Variable calculateMinimumRemainingValue(CSP csp, Assignment assignment){
    //List to save all variables with the smallest MRV (competing variables)
    List<Variable> minMRVVariables = new ArrayList<>();
    //Current smallest MRV value
    int minMRV = Integer.MAX_VALUE;

    for (Variable variable : assignment.getUnassignedVars()) {
      int legalValueSize = variable.getLegalValueSize();
      if (legalValueSize < minMRV) {
        //Found a new min MRV, update minMRV and clear minMRVVariables list
        minMRV = legalValueSize;
        minMRVVariables.clear();
        minMRVVariables.add(variable);
      } else if (legalValueSize == minMRV) {
        //Variable has the same size as the current minimum, add it to the list
        minMRVVariables.add(variable);
      }
    }
    /*If there is only one variable in the minMRVVariables list, we can return it
    otherwise if we have competing variables, we use the degree heuristic to determine which
    variable to return*/
    return (minMRVVariables.size() == 1) ?
        minMRVVariables.get(0) :
        DegreeHeuristic.calculateDegreeHeuristic(minMRVVariables, csp);
  }
}
