package by.kovalski.bankdeposits.adapter;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.time.LocalDate;

public class LocalDateAdapter extends XmlAdapter<String, LocalDate> {
  @Override
  public LocalDate unmarshal(String dateString) throws Exception {
    return LocalDate.parse(dateString);
  }

  @Override
  public String marshal(LocalDate localDate) throws Exception {
    return localDate.toString();
  }
}