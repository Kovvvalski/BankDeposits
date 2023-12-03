package by.kovalski.bankdeposits.entity;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement
public class Deposits {

  private List<Deposit> deposits = new ArrayList<>();
  public Deposits(){

  }
  @XmlElementRefs({@XmlElementRef(type = Deposit.class),
  @XmlElementRef(type = TimeDeposit.class)
  })
  public List<Deposit> getDeposits() {
    return deposits;
  }

  public void setDeposits(List<Deposit> deposits) {
    this.deposits = deposits;
  }

  public boolean add(Deposit deposit) {
    return deposits.add(deposit);
  }

}
