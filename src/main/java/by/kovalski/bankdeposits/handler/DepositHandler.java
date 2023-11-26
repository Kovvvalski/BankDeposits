package by.kovalski.bankdeposits.handler;

import by.kovalski.bankdeposits.entity.DemandDeposit;
import by.kovalski.bankdeposits.entity.Deposit;
import by.kovalski.bankdeposits.entity.TimeDeposit;
import by.kovalski.bankdeposits.entity.Type;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;

public class DepositHandler extends DefaultHandler {
  private List<Deposit> deposits;
  private Deposit current;
  private DepositXmlTag currentTag;
  private final EnumSet<DepositXmlTag> withText = EnumSet.range(DepositXmlTag.COUNTRY, DepositXmlTag.TIME);
  private static final String DEMAND_DEPOSIT_ELEMENT = "demand_deposit";
  private static final String TIME_DEPOSIT_ELEMENT = "time_deposit";
  private static final String[] TYPE_ATTRIBUTES = new String[]{"settlement", "savings", "accumulative", "metal"};

  public DepositHandler() {
    deposits = new ArrayList<>();
  }

  public List<Deposit> getDeposits() {
    return deposits;
  }

  @Override
  public void startElement(String uri, String localName, String qName, Attributes attributes) {
    if (TIME_DEPOSIT_ELEMENT.equals(qName)) { // if time deposit
      current = new TimeDeposit();
      TimeDeposit temp = (TimeDeposit) current;
      if (Arrays.stream(TYPE_ATTRIBUTES).filter(o -> o.equals(attributes.getValue(0))).toArray().length == 0) {
        temp.setBankName(attributes.getValue(0));
        temp.setType(Type.valueOf(attributes.getValue(1).toUpperCase()));
      } else {
        temp.setBankName(attributes.getValue(1));
        temp.setType(Type.valueOf(attributes.getValue(0).toUpperCase()));
      }
    } else if (DEMAND_DEPOSIT_ELEMENT.equals(qName)) { //if demand deposit
      current = new DemandDeposit();
      DemandDeposit temp = (DemandDeposit) current;
      temp.setBankName(attributes.getValue(0));
    } else { // if other element
      DepositXmlTag temp = DepositXmlTag.valueOf(qName.toUpperCase());
      if (withText.contains(temp)) {
        currentTag = temp;
      }
    }
  }

  @Override
  public void endElement(String uri, String localName, String qName) throws SAXException {
    if (DEMAND_DEPOSIT_ELEMENT.equals(qName) || TIME_DEPOSIT_ELEMENT.equals(qName))
      deposits.add(current);
  }

  @Override
  public void characters(char[] ch, int start, int length) throws SAXException {
    String data = new String(ch, start, length);
    if (currentTag != null) {
      switch (currentTag) {
        case ID -> current.getDepositor().setAccountId(data);
        case COUNTRY -> current.getDepositor().setRegistrationCountry(data);
        case DEPOSITOR -> current.getDepositor().setName(data);
        case AMOUNT -> current.setAmount(Integer.parseInt(data));
        case PROFITABILITY -> current.setProfitability(Integer.parseInt(data));
        case TIME -> {
          TimeDeposit temp = (TimeDeposit) current;
          String[] parsed = data.split("-");
          temp.setTime(LocalDate.of(Integer.parseInt(parsed[0]), Integer.parseInt(parsed[1]), Integer.parseInt(parsed[2])));
        }
      }
      currentTag = null;
    }
  }
}