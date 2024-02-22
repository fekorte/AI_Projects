import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Variable {
  private final List<Integer> positions;
  private final char character;
  private Domain domain;
  private List<Integer> legalValues;
  private Integer value;
  private final boolean auxiliaryVar;
  public Variable(List<Integer> positions, char character, boolean auxiliaryVar){
    this.positions = positions;
    this.character = character;
    this.value = null;
    this.auxiliaryVar = auxiliaryVar;
    initializeDomain();
  }

  public void initializeDomain(){
    this.domain = new Domain(positions);
    this.legalValues = new ArrayList<>(domain.getDomainValues());
  }

  public boolean isAuxiliaryVar() {
    return auxiliaryVar;
  }

  public void removeLegalValue(Integer value){
    legalValues.remove(value);
  }

  public void addLegalValue(Integer value){
    if(domain.getDomainValues().contains(value) && !legalValues.contains(value))
      legalValues.add(value);
  }

  public List<Integer> getLegalValues(){
    Collections.sort(legalValues);
    return legalValues;
  }

  public int getLegalValueSize(){ return legalValues.size(); }
  public void setValue(Integer value){ this.value = value; }
  public List<Integer> getPositions(){ return positions; }
  public void addPosition(int pos){
    positions.add(pos);
    initializeDomain();
  }
  public Integer getValue(){ return value; }
  public Character getChar(){ return character; }
}
