import java.util.*;

public class DepthFirstSearch {
	public static List<State> solve(State start, State goal) {
		if (goal == null && !(start instanceof DetectableGoal))
			throw new IllegalArgumentException();

		// LIFO collection of nodes that have not been explored.
		Stack<State> frontier = new Stack<>();
		// Collection (with fast search) holding nodes in the current search
		// path or in the frontier.  Each node maps to its parent node.
		Map<State,State> pathAndFrontier = new HashMap<>();
		// If the DFS has backtracked, records the State backtracked from
		// (null otherwise).
		State backtrackFrom = null;
		// Will hold the solution path, if found.
		List<State> solution = null;
		// The total number of nodes explored.
		int nodesExplored = 0;
		// The total number of nodes that have been in the frontier.
		int nodesInFrontier = 0;
		// The total number of nodes created.
		int nodesCreated = 0;

		frontier.add(start);
		pathAndFrontier.put(start, null);
		nodesInFrontier++;
outer:	while (!frontier.isEmpty()) {
			State node = frontier.pop();
			nodesExplored++;
			// Uncomment the following block to force the search to explore
			// all paths, not just all states.
/*			if (backtrackFrom != null) {
				State nodeParent = pathAndFrontier.get(node);
				do {
					backtrackFrom = pathAndFrontier.remove(backtrackFrom);
				} while (backtrackFrom != nodeParent);
			} */
			backtrackFrom = node;
			for (State child : node.getNeighbors()) {
				nodesCreated++;
				if (!pathAndFrontier.containsKey(child)) {
					frontier.add(child);
					pathAndFrontier.put(child, node);
					nodesInFrontier++;
					backtrackFrom = null;
					if (goal != null ? child.equals(goal) : ((DetectableGoal)child).isGoal()) {
						solution = constructSolutionPath(child, pathAndFrontier);
						break outer;
					}
				}
			}
		}
		if (solution != null) {
			for (State state : solution)
				System.out.println(state);
			System.out.println("Solution in " + (solution.size() - 1) + " moves.");
		}
		System.out.println("   Nodes explored: " + nodesExplored);
		System.out.println("Nodes in frontier: " + nodesInFrontier);
		System.out.println("    Nodes created: " + nodesCreated);
		return solution;
	}

	private static List<State> constructSolutionPath(State node, Map<State,State> parentMap) {
		LinkedList<State> path = new LinkedList<>();
		while (node != null) {
			path.addFirst(node);
			node = parentMap.get(node);
		}
		return path;
	}
}
