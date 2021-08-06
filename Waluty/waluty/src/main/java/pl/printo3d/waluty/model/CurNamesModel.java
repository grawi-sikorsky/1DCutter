package pl.printo3d.waluty.model;

import java.util.LinkedHashMap;

public class CurNamesModel {

  // ni cholery nie wiem jak przygotowac model pod jsona typu: (zeby nie klepac 170 stringow setterow i getterow...) HashMap nie dziala bo oczekuje kolejnego zagnieżdżenia.. jak w RatesModel
  /*
  {
    "AED": "United Arab Emirates Dirham",
    "AFN": "Afghan Afghani",
    "ALL": "Albanian Lek",
    "AMD": "Armenian Dram",
    "ANG": "Netherlands Antillean Guilder",
    "AOA": "Angolan Kwanza",
    "ARS": "Argentine Peso",
    "AUD": "Australian Dollar",
    "AWG": "Aruban Florin",
    "AZN": "Azerbaijani Manat",
    [...]
  }
*/
  LinkedHashMap<String,String> code;
 // List<String> name= new ArrayList<>();

  public LinkedHashMap<String, String> getCode() {
    return code;
  }

  public void setCode(LinkedHashMap<String, String> code) {
    this.code = code;
  }

}
