import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Assignment {
  private final List<Constraint> constraints;
  //Key: Character of the variable, value: value that has been assigned
  private final Map<Character, Integer> assignmentMap;
  private final List<Variable> unassignedVars;
  public Assignment(CSP csp){
    this.constraints = csp.getConstraints();
    this.assignmentMap = new HashMap<>();
    this.unassignedVars = new ArrayList<>(csp.getVariables());
  }

  public Map<Character, Integer> getAssignmentMap(){
    return assignmentMap;
  }
  public List<Variable> getUnassignedVars(){ return unassignedVars; }

  public void addValueToAssignment(Variable assignedVariable, Integer value){
    assignedVariable.setValue(value);
    assignmentMap.put(assignedVariable.getChar(), assignedVariable.getValue());
    unassignedVars.remove(assignedVariable);

    /*Update the legal values of the remaining unassignedVars if neither the assignedVariable, nor the
    unassignedVar are an auxiliary variable*/
    for (Variable unassignedVar : unassignedVars) {
      if (!unassignedVar.isAuxiliaryVar() && !assignedVariable.isAuxiliaryVar()){
        unassignedVar.removeLegalValue(assignedVariable.getValue());
      }
    }
  }

  public void removeValueFromAssignment(Variable variableToRemove){
    /*Update the legal values of the remaining unassignedVars to include the value of the
    variableToRemove. The method .addLegalValue() does only add legal values that are
    consistent with the domain values and that are not already included in the
    legal values list of the variable. Update only happens, if neither the assignedVariable,
    nor the unassignedVar are an auxiliary variable.*/
    for(Variable unassignedVar : unassignedVars) {
      if (!unassignedVar.isAuxiliaryVar() && !variableToRemove.isAuxiliaryVar())
        unassignedVar.addLegalValue(variableToRemove.getValue());
    }

    variableToRemove.setValue(null);
    assignmentMap.remove(variableToRemove.getChar());
    if(!unassignedVars.contains(variableToRemove))
      unassignedVars.add(variableToRemove);
  }

  public boolean isConsistent(Variable assignedVariable, Integer value){
    /*To check if the variable with the given value is consistent with the constraints,
    we set the value of the variableToCheck to the specified value, check for consistency and then
    set the value back to null regardless of the result*/
    assignedVariable.setValue(value);
    boolean consistent = true;
    for (Constraint constraint : constraints) {
      if (!constraint.isConsistent(assignedVariable)) {
        consistent = false;
        break;
      }
    }
    assignedVariable.setValue(null);
    return consistent;
  }

  //The assignment is complete if there are no unassigned variables left
  public boolean isComplete(){
    return unassignedVars.isEmpty();
  }
}
