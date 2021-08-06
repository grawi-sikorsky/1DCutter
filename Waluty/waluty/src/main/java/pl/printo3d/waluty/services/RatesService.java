package pl.printo3d.waluty.services;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import pl.printo3d.waluty.model.CurNamesModel;
import pl.printo3d.waluty.model.RatesModel;

@Service
public class RatesService {

  public RatesModel rates = new RatesModel();         // Rate Model class
  CurNamesModel names = new CurNamesModel();  // Currencies names Model class
  LinkedHashMap<String,String> baseCurrencyValues = new LinkedHashMap<>();  // Converted prices based on picked currency (only USD - fri api..)

  public RatesService()
  {
    try
    {
      getRatesFromAPI();
    }
    catch(Exception e)
    {
      System.out.println("Nie udalo sie odczytac danych z API !!!!!!!!!!!!!!!!!!");
    }
  }
  
  public void getRatesFromAPI()
  {
    //StringBuilder builder = new StringBuilder();
    //builder.append("https://openexchangerates.org/api/latest.json?app_id=8011116e5aaa43698b0eb4dc6cb18ba8");
    //builder.append("http://printo3d.pl/latest.json");
    
    RestTemplate restTemplate = new RestTemplate();
    rates = restTemplate.getForObject("https://printo3d.pl/latest.json", RatesModel.class);
    names = restTemplate.getForObject("https://printo3d.pl/currencies.json", CurNamesModel.class);
    
    //return rates;
  }

  // zwraca exchange rate dla wybranego kodu waluty
  public String getExRateFromName(String searchKey)
  {
    return rates.getRates().get(searchKey);
  }

  // zwraca pare: waluta + exchange rate dla podanego kodu
  public LinkedHashMap<String,String> findCurrency(String search)
  {
    LinkedHashMap <String,String> o = new LinkedHashMap<String,String>();
  
    o.put(search.toUpperCase(), rates.getRates().get(search.toUpperCase()));
    return o;
  }

  public String findExchangeRateForCode(String searchCode)
  {
    return rates.getRates().get(searchCode);
  }

  // zwraca cala mape z danymi pobranycmi z JSONa czyli kursy wymiany z bazowej USD
  public String getWaluty()
  {
    return rates.getRates().entrySet().stream().collect(Collectors.toList()).toString();
  }

  // zwraca same nazwy kodowe walut bez rate ?? [w sumie do wywalenia, mozna chyba zwracac glowna mape i wyswietlac tylko klucz zamiast klucza z wartoscia] - TODO: sprawdzic!
  public String getCurrNames()
  {
    //names.getCode().entrySet().forEach(e-> System.out.println(e.getKey() + " " + e.getValue()));
    return names.getCode().entrySet().stream().collect(Collectors.toList()).toString();
  }

  // Zwraca bazowa walute ktorej dotycza wszystkie kursy (z darowego jsona tylko USD..)
  public String getBaseCurrency()
  {
    return rates.getBase();
  }

  // Zwraca mape przeliczonych kursow z domyslnego formatu: [1 'USD' = X 'YYY'] na format: [1 'YYY' = X 'USD'] (1 USD - 0.000043546 BTC  ->  1 BTC = 32000 USD )
  public LinkedHashMap<String,String> getWalutyBasedOnBaseCurrency()
  {
    Set<HashMap.Entry<String, String>> entries = rates.getRates().entrySet();
    BigDecimal jeden = new BigDecimal(1);

    for (HashMap.Entry<String, String> mapEntry : entries) 
    {
      BigDecimal temp = new BigDecimal(mapEntry.getValue());
      
      temp = jeden.divide(temp, 8, RoundingMode.HALF_EVEN);

      baseCurrencyValues.put(mapEntry.getKey(), temp.toPlainString());
    }

    //baseCurrencyValues.entrySet().forEach(e-> System.out.println(e.getKey() + " " + e.getValue()));
  
    return baseCurrencyValues;
  }

  public String getExchangedRate(String searchCode)
  {
    return baseCurrencyValues.get(searchCode); // aby to nie wywalilo bledu powyzsza funkcja musi byc raz odplaona.
  }

  // Zwraca przeliczona wymiane po podaniu ilosci, base, i target:
  public String CalculateExchange(String amountToCalculate, String baseCurrency, String pickedCurrency)
  {
    // potrzebujemy:
    /*
        wynik (picked currency) = amount * exRate (picked currency)
    */
    String baseRate = rates.getRates().get(baseCurrency.toUpperCase());
    String exRate = rates.getRates().get(pickedCurrency.toUpperCase());

    BigDecimal wynik = new BigDecimal(amountToCalculate);
    BigDecimal pickedCur = new BigDecimal(exRate);

    wynik = wynik.multiply(pickedCur);

    return wynik.toPlainString();
  }
}