package yaremax.dto;

import lombok.Data;

import java.util.Objects;

@Data
public class Producer implements IDto {
    private String name;
    private String country;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Producer producer = (Producer) o;
        return Objects.equals(name, producer.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
