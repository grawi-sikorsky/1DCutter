package pl.printo3d.waluty.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResultModel {

  private Double resultWaste;
  private Double resultUsed;
  private Double resultWasteProcent;

  private List<String> result = new ArrayList<String>();
  private List<ResultBarPiece> resultBar = new ArrayList<ResultBarPiece>();

  public ResultModel()
  {

  }
  
  public List<String> getResults(List<StockPiece> workPieces)
  {
    // chwilowo...
    result.clear();

    for (var work : workPieces) 
    {
      StringBuilder temp = new StringBuilder();
      temp.append("Dlugość 1000: |  ");

      for(int i=0; i<work.cuts.size(); ++i)
      {
        temp.append(work.cuts.get(i).toString() + "  |  ");
      }

      temp.append(" - odpad: " + work.freeSpace());
      
      result.add(temp.toString());
    }

    return result;
  }
// TODO: uciac wyswietlanie powtarzajacych sie barow
  public List<List<ResultBarPiece>> getResultsBars(List<StockPiece> workPieces)
  {
    List<List<ResultBarPiece>> resultBars = new ArrayList<List<ResultBarPiece>>();
    //Map<String,String> resultBar = new HashMap<String,String>();
    resultBars.clear();

    for (StockPiece wp : workPieces)
    {
      for(int i=0; i < wp.cuts.size(); ++i)
      {
        resultBar.add(new ResultBarPiece((String.valueOf(  (wp.cuts.get(i) / wp.getStockLenght()) * 100)), String.valueOf(wp.cuts.get(i))));
      }
      resultBars.add(new ArrayList<ResultBarPiece>(resultBar));
      resultBar.clear();
    }

    return resultBars;
  }

  public Double calculateWaste(List<StockPiece> workPieces)
  {
    for (var workpc : workPieces) 
    {
      resultUsed += workpc.getStockLenght();
      resultWaste += workpc.freeSpace();
    }
    resultWasteProcent = (resultWaste / resultUsed) * 100.0;

    return resultWasteProcent;
  }

  

  public Double getResultWaste() {
    return resultWaste;
  }

  public void setResultWaste(Double resultWaste) {
    this.resultWaste = resultWaste;
  }

  public Double getResultUsed() {
    return resultUsed;
  }

  public void setResultUsed(Double resultUsed) {
    this.resultUsed = resultUsed;
  }

  public Double getResultWasteProcent() {
    return resultWasteProcent;
  }

  public void setResultWasteProcent(Double resultWasteProcent) {
    this.resultWasteProcent = resultWasteProcent;
  }
}
