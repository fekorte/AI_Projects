import java.util.List;
import java.util.Objects;

public class ConstraintAllDiff extends Constraint {

  /*Defines the constraint
  AllDiff(Variables), where "variables" refers to all variables except the auxiliary variables.*/

  public ConstraintAllDiff(List<Variable> variables){ super(variables); }

  @Override
  public boolean isConsistent(Variable assignedVariable){
    /*If the assignedVariable is an auxiliary var, we do not have to check if the constraint is
    consistent and we can return true*/
    if(assignedVariable.isAuxiliaryVar())
      return true;

    return isAllDiff(assignedVariable);
  }

  /*Check for all already assigned variables (.getValue() != null),
   if the newly assigned variable does assign a value that has already been assigned to a different
   char and variable.*/
  private boolean isAllDiff(Variable assignedVariable) {
    return variables.stream()
        .noneMatch(variable ->
            variable.getValue() != null &&
                Objects.equals(variable.getValue(), assignedVariable.getValue()) &&
                !Objects.equals(variable.getChar(), assignedVariable.getChar()));
  }
}
