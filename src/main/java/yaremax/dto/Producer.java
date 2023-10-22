package yaremax.dto;

import lombok.Data;

@Data
public class Producer implements IDto {
    private final Long id;
    private String name;
    private String country;
}
