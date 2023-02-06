package com.loyinc.util;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeCellRenderer;
import java.awt.*;
import java.net.URL;
import java.util.HashMap;

public class StateProviderCellRenderer extends JLabel implements TreeCellRenderer {

  DefaultTreeCellRenderer defaultRenderer;
  HashMap<String, Icon> icons;

  public StateProviderCellRenderer() {
    this.setBackground(Color.ORANGE);
    this.setBorder(BorderFactory.createEmptyBorder(0,2,0,8));
    icons = new HashMap<>();
    defaultRenderer = new DefaultTreeCellRenderer();
  }

  public void addIcon(String iconName, Icon icon) {
    if (iconName == null || icon == null) {
      throw new IllegalArgumentException("Name and icon must be non-null");
    }
    icons.put(iconName, icon);
  }

  public void addIcon(String iconPath) {
    if (iconPath == null) {
      throw new IllegalArgumentException("Icon path must be non-null");
    }
    try {
      //InputStream is = StateProviderCellRenderer.class.getResourceAsStream(iconPath);
      //ImageIcon icon = new ImageIcon(ImageIO.read(is));
      URL iconUrl = ClassLoader.getSystemClassLoader().getResource(iconPath);
      //System.out.println("Attempting to load icon from " + iconUrl);
      String iconName = iconPath.substring(iconPath.lastIndexOf('/') + 1, iconPath.lastIndexOf('.'));
      addIcon(iconName, new ImageIcon(iconUrl));
    } catch (Exception e) {
      System.err.println("Failed to load icon for " + iconPath);
      e.printStackTrace(System.err);
    }
  }

  @Override
  public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {
    StateProvider sp = null;
    if (value instanceof StateProvider) {
      sp = (StateProvider) value;
    } else if (value instanceof DefaultMutableTreeNode node) {
      Object uo = node.getUserObject();
      if (uo instanceof StateProvider) {
        sp = (StateProvider) uo;
      }
    }
    if (sp == null) {
      // Shoot. We're not the right type of renderer; give up
      return defaultRenderer.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);
    }

    // Hooray! We can render this node
    this.setOpaque(selected);
    this.setText(sp.getStateText());
    this.setIcon(icons.get(sp.getStateIconName(expanded)));
    return this;
  }
}
