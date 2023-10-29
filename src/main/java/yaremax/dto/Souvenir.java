package yaremax.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.Objects;

@AllArgsConstructor
@Builder
@Getter
@ToString
public class Souvenir implements IDto{
    private Long id;
    private String name;
    private Long producerId;
    private LocalDate releaseDate;
    private Double price;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Souvenir souvenir = (Souvenir) o;
        return Objects.equals(name, souvenir.name) && Objects.equals(producerId, souvenir.producerId) && Objects.equals(releaseDate, souvenir.releaseDate) && Objects.equals(price, souvenir.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, producerId, releaseDate, price);
    }
}
