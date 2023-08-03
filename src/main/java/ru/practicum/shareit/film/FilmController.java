package ru.practicum.shareit.film;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.practicum.shareit.exception.ValidationException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
@Slf4j
public class FilmController {
    private int lastId = 0;
    final static int MAX_LENGTH_DESCRIPTION = 200;
    final static LocalDate MIN_DATE_RELEASE = LocalDate.of(1895, 12, 28);
    private List<Film> films = new ArrayList<>();

    @PostMapping("/films")
    public ResponseEntity<String> postFilm(@RequestBody Film film) {
        try {
            validate(film);
            Film newFilm = film.toBuilder().id(getLastId()).build();
            films.add(newFilm);
            log.info("Добавлена запись о фильме: " + film.toString());
            return new ResponseEntity<String>(film.toString(), HttpStatus.OK);
        } catch (ValidationException e) {
            log.error("Возникла ошибка при добавлении фильма. " + e.toString());
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/films")
    public ResponseEntity<List<Film>> getFilms() {
        return new ResponseEntity<List<Film>>(films, HttpStatus.OK);
    }

    @PutMapping("/films/")
    public ResponseEntity<String> putFilm(@RequestBody Film film) {
        try {
            validate(film);
            for (Film oldFilm : films) {
                if (oldFilm.getId() == film.getId()) {
                    films.add(films.indexOf(oldFilm), film);
                    log.info("Обновлена запись о фильме. Было: " + oldFilm.toString() + ". Стало: " + film.toString());
                    return new ResponseEntity<String>(film.toString(), HttpStatus.OK);
                }
            }
            Film newFilm = film.toBuilder().id(getLastId()).build();
            films.add(newFilm);
            log.info("Добавлена новая запись о фильме: " + newFilm.toString());
            return new ResponseEntity<String>(newFilm.toString(), HttpStatus.OK);
        } catch (ValidationException e) {
            log.error("Возникла ошибка при добавлении фильма. " + e.toString());
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    private void validate(Film film) throws ValidationException {
        if (film.getName().isBlank()
                || film.getDescription().length() > MAX_LENGTH_DESCRIPTION
                || film.getReleaseDate().isBefore(MIN_DATE_RELEASE)
                || film.getDuration().isNegative()) {
            throw new ValidationException("Некорректно заполнена информация о фильме");
        }
    }

    public int getLastId() {
        return ++lastId;
    }
}
