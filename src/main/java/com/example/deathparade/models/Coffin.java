package com.example.deathparade.models;

/**.
 *
 */

public class Coffin {
  public final String outsideColor;
  public final String outsideMaterial;
  public final String insideMaterial;
  public final String insideColor;
  public CoffinParameters parameters;
  public final int cost;
  /**.
   *
   */

  public Coffin(String outsideColor, String outsideMaterial,
                 String insideMaterial, String insideColor, CoffinParameters parameters, int cost) {
    this.outsideColor = outsideColor;
    this.outsideMaterial = outsideMaterial;
    this.insideMaterial = insideMaterial;
    this.insideColor = insideColor;
    this.parameters = parameters;
    this.cost = cost;
  }

  public String getOutsideColor() {
    return outsideColor;
  }

  public String getOutsideMaterial() {
    return outsideMaterial;
  }

  public String getInsideMaterial() {
    return insideMaterial;
  }

  public String getInsideColor() {
    return insideColor;
  }

  public CoffinParameters getParameters() {
    return parameters;
  }

  public int getCost() {
    return cost;
  }
}
