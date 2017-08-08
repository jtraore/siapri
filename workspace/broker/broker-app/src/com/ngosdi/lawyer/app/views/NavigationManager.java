package com.ngosdi.lawyer.app.views;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class NavigationManager {

	private static int MAX = 10;

	private final Stack<String> backward = new Stack<>();
	private final Stack<String> forward = new Stack<>();
	private String current;

	public void addPart(final String partId) {
		if (current == null) {
			current = partId;
		} else if (!current.equals(partId)) {
			if (backward.size() == MAX) {
				backward.remove(0);
			}
			backward.push(current);
			current = partId;
			forward.clear();
		}
	}

	public String backward() {
		if (!backward.isEmpty()) {
			forward.push(current);
			current = backward.pop();
			return current;
		}
		return null;
	}

	public String forward() {
		if (!forward.isEmpty()) {
			backward.push(current);
			current = forward.pop();
			return current;
		}
		return null;
	}

	public boolean canGoBackward() {
		return !backward.isEmpty();
	}

	public boolean canGoForward() {
		return !forward.isEmpty();
	}

	public List<String> getBackward() {
		final List<String> reverseList = new ArrayList<>(backward.size());
		for (int i = backward.size() - 1; i >= 0; i--) {
			reverseList.add(backward.get(i));
		}
		return reverseList;
	}

	public List<String> getForward() {
		final List<String> reverseList = new ArrayList<>(forward.size());
		for (int i = forward.size() - 1; i >= 0; i--) {
			reverseList.add(forward.get(i));
		}
		return reverseList;
	}

}
