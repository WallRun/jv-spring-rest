package mate.academy.spring.controller;

import java.util.List;
import java.util.stream.Collectors;
import mate.academy.spring.dto.CinemaHallRequestDto;
import mate.academy.spring.dto.CinemaHallResponseDto;
import mate.academy.spring.mapper.CinemaHallMapper;
import mate.academy.spring.service.CinemaHallService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CinemaHallController {
    private final CinemaHallService cinemaHallService;
    private final CinemaHallMapper cinemaHallMapper;

    @Autowired
    public CinemaHallController(CinemaHallService cinemaHallService,
                                CinemaHallMapper cinemaHallMapper) {
        this.cinemaHallService = cinemaHallService;
        this.cinemaHallMapper = cinemaHallMapper;
    }

    @PostMapping("/cinema-halls")
    public CinemaHallResponseDto create(@RequestBody CinemaHallRequestDto cinemaHall) {
        return cinemaHallMapper.toDto(cinemaHallService.add(cinemaHallMapper.toModel(cinemaHall)));
    }

    @GetMapping("/cinema-halls")
    public List<CinemaHallResponseDto> getAll() {
        return cinemaHallService.getAll().stream()
            .map(cinemaHallMapper::toDto)
            .collect(Collectors.toList());
    }
}
