package com.loyinc.util;

public interface StateProvider {
  public int getState();
  public String getStateText();
  public String getStateIconName(boolean expanded);
}
