package yaremax.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.Objects;

@AllArgsConstructor
@Getter
@ToString
public class Souvenir implements IDto{
    private String name;
    private String producerName;
    private LocalDate releaseDate;
    private Double price;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Souvenir souvenir = (Souvenir) o;
        return Objects.equals(name, souvenir.name) && Objects.equals(producerName, souvenir.producerName) && Objects.equals(releaseDate, souvenir.releaseDate) && Objects.equals(price, souvenir.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, producerName, releaseDate, price);
    }
}
