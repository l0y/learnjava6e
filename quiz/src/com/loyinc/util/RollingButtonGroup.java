package com.loyinc.util;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.*;
import javax.swing.AbstractButton;

/**
 * A variation on radio button groups. This "rolling" group has a maximum
 * number of (typically) checkboxes selected at any given time. If a new
 * selection is added after the maximum is reached, the oldest selection
 * is undone to make room for the new selection.
 *
 * For example, consider five buttons, A - E, in a rolling group with a
 * max selection of two. Initially, no buttons are selected. Select A and
 * it will be added as expected. Next select C, which will also be added
 * as expected. If you go on to select D, though, A will be deselected;
 * leaving C and D as the two selected buttons.
 */
public class RollingButtonGroup implements ItemListener {
  ArrayList<AbstractButton> buttons = new ArrayList<>();
  Queue<AbstractButton> history = new ArrayDeque<>();
  int roll; // maximum number of selected buttons before next selection "rolls"

  /**
   * Create a rolling button group with a default size of 1. This size mimics
   * classic radio button groups.
   */
  public RollingButtonGroup() {
    this(1);
  }

  /**
   * Create a rolling button group with the specified initial size. The initial
   * size must be greater than zero. (No upper limit is enforced as the number
   * of buttons in the group is unknown.)
   *
   * @param rollSize The initial (positive) maximum number of selectable buttons in the group
   */
  public RollingButtonGroup(int rollSize) {
    this.roll = rollSize;
  }

  /**
   * Resets the state of this button group. All buttons are deselected and
   * the history queue is cleared.
   */
  public void reset() {
    history.clear();
    for (AbstractButton b : buttons) {
      b.setSelected(false);
    }
  }

  public int getRollSize() { return roll; }
  public void setRollSize(int rollSize) {
    // Sanity check
    int rollMax = buttons.size();
    if (rollSize > 0 && rollSize <= rollMax) {
      roll = rollSize;
    } else {
      throw new IllegalArgumentException("Roll size ("
          + rollSize + ") must be positive in the range 1 .. "
          + rollMax + " inclusive." );
    }
  }

  /**
   * Adds the specified button to the group. This includes adding
   * the group as an ItemListener on the button.
   *
   * @param b The button to add to this group
   */
  public void add(AbstractButton b) {
    b.addItemListener(this);
    buttons.add(b);
  }

  /**
   * Removes the specified button from this group, including potentially from the
   * current selection history. This group is also removed from the button's ItemListener
   * list.
   *
   * @param b The button to remove from this group
   */
  public void remove(AbstractButton b) {
    b.removeItemListener(this);
    history.remove(b);
    buttons.remove(b);
  }

  @Override
  public void itemStateChanged(ItemEvent e) {
    AbstractButton b = (AbstractButton) e.getSource();
    if (e.getStateChange() == ItemEvent.DESELECTED) {
      // Remove this button from the history
      //System.out.println("Deselected, removing " + b.getText() + " from history");
      history.remove(b);
    } else {
      // One of the buttons was selected, so add it to the history queue
      // but make sure that queue maintains the correct size
      if (history.size() >= roll) {
        // deselect the "oldest" button
        int h1 = history.size();
        AbstractButton old = history.poll();
        int h2 = history.size();
        if (old != null) {
          old.setSelected(false);
        }
        //System.out.println("Rolling " + old.getText() + "; (" + h1 + " -> " + h2 + ")");
      }
      //System.out.println("Adding " + b.getText() + " to history");
      history.add(b);
    }
  }
}
