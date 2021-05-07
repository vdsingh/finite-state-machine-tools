import java.util.*;

class DFA extends FiniteStateMachine{
  
  public DFA(Node initialState, Set<Node> finalStates) {
    super(initialState, finalStates);
  }

  public DFA(String initialState, String[] states, String[] finalStates, String[][] transitions) {
    super(initialState, states, finalStates, transitions);
  }
  
}