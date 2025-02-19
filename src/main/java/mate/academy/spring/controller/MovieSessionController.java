package mate.academy.spring.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import mate.academy.spring.dto.MovieSessionRequestDto;
import mate.academy.spring.dto.MovieSessionResponseDto;
import mate.academy.spring.mapper.MovieSessionMapper;
import mate.academy.spring.model.MovieSession;
import mate.academy.spring.service.MovieSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MovieSessionController {
    private final MovieSessionService movieSessionService;
    private final MovieSessionMapper movieSessionMapper;

    @Autowired
    public MovieSessionController(MovieSessionService movieSessionService,
                                  MovieSessionMapper movieSessionMapper) {
        this.movieSessionService = movieSessionService;
        this.movieSessionMapper = movieSessionMapper;
    }

    @PostMapping("/movie-sessions")
    public MovieSessionResponseDto create(@RequestBody MovieSessionRequestDto movieSession) {
        return movieSessionMapper.toDto(movieSessionService
            .add(movieSessionMapper.toModel(movieSession)));
    }

    @GetMapping("/movie-sessions/available")
    public List<MovieSessionResponseDto> findAllAvailable(@RequestParam Long movieId,
                                                          @RequestParam @DateTimeFormat
                                                              (pattern = "dd.MM.yyyy")
                                                              LocalDate date) {
        return movieSessionService.findAvailableSessions(movieId, date).stream()
            .map(movieSessionMapper::toDto)
            .collect(Collectors.toList());
    }

    @PutMapping("/movie-sessions/{id}")
    public MovieSessionResponseDto update(@PathVariable("id") Long id,
                                          @RequestBody MovieSessionRequestDto movieSessionDto) {
        MovieSession movieSession = movieSessionMapper.toModel(movieSessionDto);
        movieSession.setId(id);
        MovieSession updatedSession = movieSessionService.update(movieSession);
        return movieSessionMapper.toDto(updatedSession);
    }

    @DeleteMapping("/movie-sessions/{id}")
    public void delete(@PathVariable Long id) {
        movieSessionService.delete(id);
    }
}
