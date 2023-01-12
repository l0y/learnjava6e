package com.loyinc.util;

import javax.swing.*;
import java.awt.*;

/**
 * A constant-width label for use in layout managers that query
 * their components to determine sizes.
 */
public class CWButton extends JButton {
  protected int width;

  public CWButton(int width) {
    this(width, null, null);
  }

  public CWButton(int width, String text) {
    this(width, text, null);
  }

  public CWButton(int width, Icon icon) {
    this(width, null, icon);
  }

  public CWButton(int width, String text, Icon icon) {
    super(text, icon);
    this.width = width;
    tailor();
  }

  public CWButton(int width, Action action) {
    super(action);
    this.width = width;
    tailor();
  }

  /**
   * Use this method to apply any app-wide adjustments to instances
   * of this constant-width button, regardless of how it was constructed.
   */
  protected void tailor() {
    Config.applyPrefs(this);
  }

  public Dimension getPreferredSize() {
    return new Dimension(width, this.getHeight());
  }
  public Dimension getMinimumSize() { return getPreferredSize(); }
  public Dimension getMaximumSize() { return getPreferredSize(); }
}
