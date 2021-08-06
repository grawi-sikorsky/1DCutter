package pl.printo3d.waluty.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class TransactionComponent {
  private TransRepo transRepo;

  @Autowired
  public TransactionComponent(TransRepo transRepo, UserRepo tUserRepo) 
  {
    this.transRepo = transRepo;
  }

  @EventListener(ApplicationReadyEvent.class)
  public void runrunrun()
  {
    TransEntity transik = new TransEntity("var1", "var2","var3","var4");
    transRepo.save(transik);
  }  
}
