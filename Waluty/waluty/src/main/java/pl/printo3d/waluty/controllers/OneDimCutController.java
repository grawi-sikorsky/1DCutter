package pl.printo3d.waluty.controllers;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import pl.printo3d.waluty.model.CutListModel;
import pl.printo3d.waluty.model.StockListModel;
import pl.printo3d.waluty.services.OneDCutterService;

@Controller
public class OneDimCutController {

  private OneDCutterService oneDCutterService = new OneDCutterService();

  @RequestMapping(value="/1dcut", method={RequestMethod.GET})
  public String OneDimCutControllerShow(Model mdl)
  {
    //mdl.addAttribute("in_pclength", oneDCutterService.pcsLength);
    //mdl.addAttribute("in_pcscount", oneDCutterService.pcs);
    mdl.addAttribute("stocklist", oneDCutterService.stockList);
    mdl.addAttribute("cuttlistmap", oneDCutterService.cutList);
    return "1dcut";
  }

  @RequestMapping(value="/1dcut", method={RequestMethod.POST})
  public String ReqStockRun(
    @RequestParam(value = "in_stockcount", required = false)   List<String> in_stockCount,
    @RequestParam(value = "in_stocklength", required = false)  List<String> in_stockLength,
    @RequestParam(value = "in_pcscount", required = false)  List<String> in_pcscount,
    @RequestParam(value = "in_pclength", required = false)  List<String> in_pclength, Model mdl  )
  {
    System.out.println(in_pcscount);
    System.out.println(in_pclength);
    System.out.println(in_stockCount);
    System.out.println(in_stockLength);
    System.out.println(oneDCutterService.calculateWaste());

    oneDCutterService.cutList.clear();
    for (int i=0; i < in_pclength.size(); ++i)
    {
      oneDCutterService.cutList.add(new CutListModel(in_pclength.get(i), in_pcscount.get(i)));
    }
    mdl.addAttribute("cuttlistmap", oneDCutterService.cutList);

    oneDCutterService.stockList.clear();
    for (int i=0; i < in_stockLength.size(); ++i)
    {
      oneDCutterService.stockList.add(new StockListModel(in_stockLength.get(i), in_stockCount.get(i)));
    }
    mdl.addAttribute("stocklist", oneDCutterService.stockList);


    oneDCutterService.firstFit();

    mdl.addAttribute("wyniki", oneDCutterService.getResults());

    // liczymy
    mdl.addAttribute("stockLen", oneDCutterService.stockList.get(0).getStockLenght());
    mdl.addAttribute("stockPcs", oneDCutterService.stockList.get(0).getStockPcs());
    mdl.addAttribute("cuts", oneDCutterService.getCuts());
    mdl.addAttribute("stockneed", oneDCutterService.calculateNeededStock(oneDCutterService.partsList, oneDCutterService.stockLen));
    mdl.addAttribute("wasteproc", oneDCutterService.calculateWaste());
    mdl.addAttribute("usedproc", 100 - oneDCutterService.calculateWaste());
    mdl.addAttribute("resultbars", oneDCutterService.getResultBars());

    return "1dcut";
  }

}


/*
public static void binPacking(int[] a, int size, int n)
{
  int binCount = 0;
  int[] binValues = new int[n];
  for (int i = 0; i < binValues.length; i++)
    binValues[i] = size;

  for (int i = 0; i < n; i++)
    for (int j = 0; j < binValues.length; j++)
    {
      if (binValues[j] - a[i] >= 0)
      {
        binValues[j] -= a[i];
        break;
      }
    }

  for (int i = 0; i < binValues.length; i++)
    if (binValues[i] != size)
      binCount++;

  System.out
      .println("Number of bins required using first fit decreasing algorithm is:"
          + binCount);
}

static int[] sort(int[] sequence)
{
  // Bubble Sort descending order
  for (int i = 0; i < sequence.length; i++)
    for (int j = 0; j < sequence.length - 1; j++)
      if (sequence[j] < sequence[j + 1])
      {
        sequence[j] = sequence[j] + sequence[j + 1];
        sequence[j + 1] = sequence[j] - sequence[j + 1];
        sequence[j] = sequence[j] - sequence[j + 1];
      }
  return sequence;
}
*/