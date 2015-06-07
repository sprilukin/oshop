package oshop.services.filter.converter;

public class IntegerConverter extends BaseConverter<Integer> {

    @Override
    public Integer convert(String value) {
        return Integer.parseInt(value);
    }
}
