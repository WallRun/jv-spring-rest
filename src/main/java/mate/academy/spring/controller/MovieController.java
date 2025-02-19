package mate.academy.spring.controller;

import java.util.List;
import java.util.stream.Collectors;
import mate.academy.spring.dto.MovieRequestDto;
import mate.academy.spring.dto.MovieResponseDto;
import mate.academy.spring.mapper.MovieMapper;
import mate.academy.spring.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MovieController {
    private final MovieService movieService;
    private final MovieMapper movieMapper;

    @Autowired
    public MovieController(MovieService movieService, MovieMapper movieMapper) {
        this.movieService = movieService;
        this.movieMapper = movieMapper;
    }

    @PostMapping("/movies")
    public MovieResponseDto create(@RequestBody MovieRequestDto movie) {
        return movieMapper.toDto(movieService.add(movieMapper.toModel(movie)));
    }

    @GetMapping("/movies")
    public List<MovieResponseDto> getAll() {
        return movieService.getAll().stream()
            .map(movieMapper::toDto)
            .collect(Collectors.toList());
    }
}
