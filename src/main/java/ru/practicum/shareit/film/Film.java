package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;
import lombok.Value;

import java.time.Duration;
import java.time.LocalDate;

@Builder(toBuilder = true)
@Value
public class Film {
    int id;
    String name;
    String description;
    LocalDate releaseDate;
    Duration duration;
}
