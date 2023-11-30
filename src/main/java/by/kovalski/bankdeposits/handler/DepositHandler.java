package by.kovalski.bankdeposits.handler;

import by.kovalski.bankdeposits.entity.Deposit;
import by.kovalski.bankdeposits.entity.TimeDeposit;
import by.kovalski.bankdeposits.entity.Type;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;

public class DepositHandler extends DefaultHandler {
  private static final String DEPOSIT_ELEMENT = "deposit";
  private static final String TIME_DEPOSIT_ELEMENT = "time_deposit";
  private static final String[] TYPE_ATTRIBUTES = new String[]{"settlement", "savings", "accumulative", "metal"};

  private final EnumSet<DepositXmlTag> withText = EnumSet.range(DepositXmlTag.COUNTRY, DepositXmlTag.TIME);

  private List<Deposit> deposits;
  private Deposit current;
  private DepositXmlTag currentTag;

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
    } else if (DEPOSIT_ELEMENT.equals(qName)) { //if deposit
      current = new Deposit();
      current.setBankName(attributes.getValue(0));
    } else { // if other element
      DepositXmlTag temp = DepositXmlTag.valueOf(qName.toUpperCase());
      if (withText.contains(temp)) {
        currentTag = temp;
      }
    }
  }

  @Override
  public void endElement(String uri, String localName, String qName) throws SAXException {
    if (DEPOSIT_ELEMENT.equals(qName) || TIME_DEPOSIT_ELEMENT.equals(qName))
      deposits.add(current);
  }

  @Override
  public void characters(char[] ch, int start, int length) throws SAXException {
    String data = new String(ch, start, length);
    if (currentTag != null) {
      currentTag.setField(current, data);
      currentTag = null;
    }
  }
}
