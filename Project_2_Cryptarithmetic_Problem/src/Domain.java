import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

public class Domain {
  private final List<Integer> positions;
  private List<Integer> domainValues;

  public Domain(List<Integer> positions){
    this.positions = positions;
    initializeDomainValues();
  }

  public List<Integer> getDomainValues(){
    Collections.sort(domainValues);
    return Collections.unmodifiableList(domainValues);
  }

  /*Initializes the domain values of a variable.
  Since a variable can have several positions, we only include those values that are valid for
  both positions. This is being achieved by the build-in retainAll() method.*/
  private void initializeDomainValues() {
    this.domainValues = new ArrayList<>();

    for (Integer pos : positions) {
      List<Integer> validValues = assignDomainValuesForPosition(pos);

      if (domainValues.isEmpty()) {
        domainValues.addAll(validValues);
      } else {
        /*If we have several positions for one character,
        only those values that intersect for both positions are valid*/
        domainValues.retainAll(validValues);
      }
    }
  }


  /*Return the domain values that were pre-defined in the task description depending on the position.
  Pre-defined domain values:
  C1, C2 and C3 {0, 1}
  C4 {1}
  X9 {1}
  X1 and X5 {1, …, 9}
  X2, X3, X3, X6, X7, X8, X10, x11, x12, x13 {0, 1, …, 9}*/
  private List<Integer> assignDomainValuesForPosition(int pos) {
    List<Integer> validValues = new ArrayList<>();

    switch (pos) {
      case -1, -2, -3:
        validValues.addAll(Arrays.asList(0, 1));
        break;
      case 1, 5:
        validValues.addAll(IntStream.rangeClosed(1, 9).boxed().toList());
        break;
      case 9, -4:
        validValues.add(1);
        break;
      case 2, 3, 4, 6, 7, 8, 10, 11, 12, 13:
        validValues.addAll(IntStream.rangeClosed(0, 9).boxed().toList());
        break;
      default:
        System.out.println("Failed to add domain value.");
    }

    return validValues;
  }
}
