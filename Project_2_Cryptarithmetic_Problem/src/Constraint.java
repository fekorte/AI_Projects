import java.util.List;

public abstract class Constraint {
  protected List<Variable> variables;
  protected Constraint(List<Variable> variables){
    this.variables = variables;
  }
  public abstract boolean isConsistent(Variable assignedVariable);

  public boolean hasVariable(Variable variable){
    return variables.contains(variable);
  }

  //Returns all variables with the value null that are included in the constraint
  public List<Variable> getUnassignedVariablesOfConstraint(){
    return variables.stream()
        .filter(variable -> variable.getValue() == null)
        .toList();
  }
}
