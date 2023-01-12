package com.loyinc.util;

import javax.swing.*;
import java.awt.*;

/**
 * A constant-width label for use in layout managers that query
 * their components to determine sizes.
 */
public class CWLabel extends JLabel {
  protected int width;

  public CWLabel(int width) {
    this(width, null, null, SwingConstants.LEADING);
  }

  public CWLabel(int width, String text) {
    this(width, text, null, SwingConstants.LEADING);
  }

  public CWLabel(int width, String text, int horizontalAlignment) {
    this(width, text, null, horizontalAlignment);
  }

  public CWLabel(int width, Icon icon) {
    this(width, null, icon, SwingConstants.CENTER);
  }

  public CWLabel(int width, Icon icon, int horizontalAlignment) {
    this(width, null, icon, horizontalAlignment);
  }

  public CWLabel(int width, String text, Icon icon, int horizontalAlignment) {
    super(text, icon, horizontalAlignment);
    this.width = width;
    Config.applyPrefs(this);
  }

  public Dimension getPreferredSize() {
    return new Dimension(width, this.getHeight());
  }
  public Dimension getMinimumSize() { return getPreferredSize(); }
  public Dimension getMaximumSize() { return getPreferredSize(); }
}
