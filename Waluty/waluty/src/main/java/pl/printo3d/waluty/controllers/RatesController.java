package pl.printo3d.waluty.controllers;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import pl.printo3d.waluty.repository.TransEntity;
import pl.printo3d.waluty.repository.TransRepo;
import pl.printo3d.waluty.repository.UserService;
import pl.printo3d.waluty.services.RatesService;



@Controller
public class RatesController {

  @Autowired
  private TransRepo transRepo;

  @Autowired
  private UserService uService;

  @Autowired
  RatesService ratesService;
  String targetCode="";
  String baseCode="";
  String amount="";
  
  // HOME
  @RequestMapping(value="/", method={RequestMethod.GET})
  public String showRates(Model mdl)
  {
    targetCode = "PLN";
    baseCode = "USD";
    amount = "1";

    mdl.addAttribute("waluty", ratesService.rates.getRates());
    mdl.addAttribute("basecurrency", ratesService.getBaseCurrency());
    mdl.addAttribute("currencies", ratesService.getWalutyBasedOnBaseCurrency() );
    mdl.addAttribute("target", ratesService.findExchangeRateForCode("PLN"));
    mdl.addAttribute("exchanged", ratesService.getExchangedRate("PLN"));
    mdl.addAttribute("amountToCalc", "1");
    mdl.addAttribute("amountCalculated", ratesService.CalculateExchange("1", "USD", "PLN"));
    mdl.addAttribute("pickedCurrency", "PLN");
        
    
    //String username = uService.getUsername();
    //mdl.addAttribute("uname", username.toUpperCase());

    System.out.println("showRates()[home] GET: "+amount+" "+baseCode+" "+targetCode);
    return "home";
  }

  @RequestMapping(value="/", method = {RequestMethod.POST}, params = "q")
  public String searchForCurrency(@RequestParam(value = "q", required = false) String szukaj, Model mdl)
  {
    targetCode = szukaj;

    mdl.addAttribute("waluty", ratesService.findCurrency(szukaj));
    mdl.addAttribute("basecurrency", ratesService.getBaseCurrency());
    mdl.addAttribute("currencies", ratesService.getWalutyBasedOnBaseCurrency() );

    //mdl.addAttribute("wynik", ratesService.wynik(swynik));
    System.out.println("searchForCurrency() POST: "+szukaj);
    
    //System.out.println(ratesService.findCurrency("BTC"));
    return "home";
  }

  // NIEUZYWANE JUZ
  @RequestMapping(value = "/", method = {RequestMethod.POST}, params = "pick")
  public String pick(@RequestParam(value="pick", required = true) String pick, Model mdl)
  {
    jakiswynik(amount,baseCode,pick,mdl);

    System.out.println("pick() POST: "+pick);

    return "home";
  }

  @RequestMapping(value="/", method = {RequestMethod.GET}, params = {"amountToCalc","base","tar"})
  public String jakiswynik( @RequestParam(value = "amountToCalc", required = false) String amountToCalc,
                            @RequestParam(value = "base", required = true) String base,
                            @RequestParam(value = "tar", required = true) String tar,
                            Model mdl)
  {
    targetCode = tar;
    baseCode = base;
    amount = amountToCalc;

    mdl.addAttribute("waluty", ratesService.rates.getRates());
    mdl.addAttribute("basecurrency", ratesService.getBaseCurrency());
    mdl.addAttribute("currencies", ratesService.getWalutyBasedOnBaseCurrency() );
    mdl.addAttribute("target", ratesService.findExchangeRateForCode(tar));
    mdl.addAttribute("exchanged", ratesService.getExchangedRate(tar));

    mdl.addAttribute("amountToCalc", amountToCalc);
    mdl.addAttribute("amountCalculated", ratesService.CalculateExchange(amountToCalc, "USD", tar.toUpperCase()));
    mdl.addAttribute("pickedCurrency", tar.toUpperCase());

    System.out.println("jakiswynik() GET: "+amountToCalc+" "+base+" "+tar);
    
    //System.out.println(ratesService.findCurrency("BTC"));
    return "home";
  }

  @RequestMapping(value="/", method=RequestMethod.POST, params = "kuppan")
  public String kupPan(@RequestParam String kuppan) 
  {

    LocalDateTime loc = LocalDateTime.now();

    TransEntity transakszyn = new TransEntity("USD", "BTC", "10", loc.toString() );
    transRepo.save(transakszyn);

    System.out.println(kuppan);
    return "home";
  }
 

}
