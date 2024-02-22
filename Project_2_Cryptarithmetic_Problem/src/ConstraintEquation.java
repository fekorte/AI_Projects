import java.util.List;
import java.util.Objects;

public class ConstraintEquation extends Constraint {

  /*Each instance of this class represents one of the following constraints which I have defined:
  x4 + x8 = 10*C1 + x13
  C1 + x3 + x7 = 10*C2 + x12
  C2 + x2 + x6 = 10*C3 + x11
  C3 + x1 + x5 = 10*C4 + x10
  C4 = x9
  The instantiation can be found in the CSP class in the defineConstraints() method.*/

  public ConstraintEquation(List<Variable> variables){ super(variables); }

  @Override
  public boolean isConsistent(Variable variable){
    /*We do not have to check further if the equation of the constraint is equal
     if variables inside the constraint have not been assigned yet.
     Thus, we return true if all variables are not assigned yet.
    If all variables are assigned, we return the result of isEqual().*/
    return !allVariablesAssigned() || (isEqual());
  }

  //Return true if all variables of the list satisfy the condition .getValue != null
  private boolean allVariablesAssigned() {
    return variables.stream().allMatch(v -> v.getValue() != null);
  }

  private boolean isEqual(){
    //Depending on the length of variables that the constraint contains, the equation differs.
    if(variables.size() == 2){
      //C4 = x9 => Stands for an equation with only two variables
      return Objects.equals(variables.get(0).getValue(), variables.get(1).getValue());
    }

    if(variables.size() == 4){
      //x4 + x8 = 10*C1 + x13 => Equation with four variables
      return variables.get(0).getValue() + variables.get(1).getValue()
          == 10 * variables.get(2).getValue() + variables.get(3).getValue();
    }

    if(variables.size() == 5){
      //E.g. C1 + x3 + x7 = 10*C2 + x12 => Equation with five variables
      return variables.get(0).getValue() + variables.get(1).getValue() + variables.get(2).getValue()
          == 10 * variables.get(3).getValue() + variables.get(4).getValue();
    }
    return false;
  }
}
