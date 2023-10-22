package yaremax.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Souvenir implements IDto{
    private final Long id;
    private String name;
    private String producerName;
    private LocalDate releaseDate;
    private Double price;
}
