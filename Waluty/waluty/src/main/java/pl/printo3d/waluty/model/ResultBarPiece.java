package pl.printo3d.waluty.model;

public class ResultBarPiece {

  private String barWithProc;
  private String barText;

  public ResultBarPiece(String barWithProc, String barText) {
    this.barWithProc = barWithProc;
    this.barText = barText;
  }
  
  public String getBarWithProc() {
    return barWithProc;
  }
  public void setBarWithProc(String barWithProc) {
    this.barWithProc = barWithProc;
  }
  public String getBarText() {
    return barText;
  }
  public void setBarText(String barText) {
    this.barText = barText;
  }
}
