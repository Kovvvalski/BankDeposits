package by.kovalski.bankdeposits.adapter;

import by.kovalski.bankdeposits.entity.Type;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.time.LocalDate;

public class TypeAdapter extends XmlAdapter<String, Type> {
  @Override
  public Type unmarshal(String typeString) throws Exception {
    return Type.valueOf(typeString.toUpperCase());
  }

  @Override
  public String marshal(Type type) throws Exception {
    return type.toString().toLowerCase();
  }
}
