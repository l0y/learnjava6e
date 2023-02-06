package com.loyinc.util;

import com.loyinc.quiz.Review;

import javax.swing.*;
import java.awt.*;
import java.util.prefs.Preferences;

public class Config {
  // Internal Preference node and key names
  static final String PREFS_NODE = "/com/loyinc/util/Review";
  static final String FONT_SIZE_ADJ_KEY = "font.size.adjustment";
  
  static final String osName = System.getProperty("os.name");

  // Public defaults for some common preferences
  public static final float DEFAUlT_ADJUSTMENT = 2.0f;

  public static void applyPrefs(JComponent uiComp) {
    Preferences prefs = Preferences.userRoot().node(PREFS_NODE);
    float adjustment = prefs.getFloat(FONT_SIZE_ADJ_KEY, DEFAUlT_ADJUSTMENT);
    Font f = uiComp.getFont();
    uiComp.setFont(f.deriveFont(f.getSize() + adjustment));
  }

  /**
   * Helper to get the font size adjustment value for use in manually
   * configuring a component.
   */
  public float getFontSizeAdjustment() {
    Preferences prefs = Preferences.userRoot().node(PREFS_NODE);
    return prefs.getFloat(FONT_SIZE_ADJ_KEY, DEFAUlT_ADJUSTMENT);
  }

  public static boolean isMacOS() {
    return osName.startsWith("Mac");
  }

  public static boolean isLinux() {
    return osName.equalsIgnoreCase("linux");
  }
}
