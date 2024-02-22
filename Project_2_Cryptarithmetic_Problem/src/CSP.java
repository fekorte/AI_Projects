import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*Class which defines the Constraint Satisfaction Problem and consists of
the variables (the class Variable includes the domain for each variable) and the constraints which
are being defined during initialization.*/
public class CSP {
  private final List<Variable> variables;
  private List<Constraint> constraints;
  /*Key: Position, Value: Variable.
  Map to keep track of the variable at a certain position. Used to define the constraints.*/
  private Map<Integer, Variable> positions;

  public CSP(List<Variable> variables){
    this.variables = variables;
    addAuxiliaryVariables();
    createPositionMap();
    defineConstraints();
  }

  public List<Constraint> getConstraints(){ return constraints; }
  public List<Variable> getVariables(){ return variables; }

  private void addAuxiliaryVariables(){
    //The position -1 refers to the auxiliary variable C1, the position -2 refers to C2, etc.
    variables.add(new Variable(List.of(-1), 'a', true));
    variables.add(new Variable(List.of(-2), 'b', true));
    variables.add(new Variable(List.of(-3), 'c', true));
    variables.add(new Variable(List.of(-4), 'd', true));
  }

  private void createPositionMap() {
    positions = new HashMap<>();
    for (Variable variable : variables) {
      for (Integer position : variable.getPositions()) {
        positions.put(position, variable);
      }
    }
  }

  /*Method to create new instances for the following constraints and assign the correct variables
  to them. Constraints:
  1: AllDiff(each letter)
  2: x4 + x8 = 10*C1 + x13
  3: C1 + x3 + x7 = 10*C2 + x12
  4: C2 + x2 + x6 = 10*C3 + x11
  5: C3 + x1 + x5 = 10*C4 + x10
  6: C4 = x9*/
  private void defineConstraints(){
    Constraint firstConstraint = new ConstraintAllDiff(variables.stream()
        .filter(variable -> Character.isUpperCase(variable.getChar())).toList());

    Constraint secondConstraint = new ConstraintEquation(List.of(positions.get(4), positions.get(8),
        positions.get(-1), positions.get(13)));

    Constraint thirdConstraint = new ConstraintEquation(List.of(positions.get(-1), positions.get(3),
        positions.get(7), positions.get(-2), positions.get(12)));

    Constraint fourthConstraint = new ConstraintEquation(List.of(positions.get(-2), positions.get(2),
        positions.get(6), positions.get(-3), positions.get(11)));

    Constraint fifthConstraint = new ConstraintEquation(List.of(positions.get(-3), positions.get(1),
        positions.get(5), positions.get(-4), positions.get(10)));

    Constraint sixthConstraint = new ConstraintEquation(List.of(positions.get(-4), positions.get(9)));

    constraints = new ArrayList<>(List.of(firstConstraint, secondConstraint, thirdConstraint,
        fourthConstraint, fifthConstraint, sixthConstraint));
  }
}