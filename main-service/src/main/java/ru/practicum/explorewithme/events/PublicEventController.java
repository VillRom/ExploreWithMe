package ru.practicum.explorewithme.events;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithme.events.dto.EventFullDto;
import ru.practicum.explorewithme.events.dto.EventShortDto;
import ru.practicum.explorewithme.statsclient.StatsClient;
import ru.practicum.explorewithme.statsclient.endpoint.EndpointHit;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;

@RestController
@Slf4j
@RequestMapping("/events")
public class PublicEventController {

    private final EventService eventService;

    private final StatsClient statsClient;

    @Autowired
    public PublicEventController(EventService eventService, StatsClient statsClient) {
        this.eventService = eventService;
        this.statsClient = statsClient;
    }

    @GetMapping()
    public List<EventShortDto> getEventsWithFilter(@RequestParam(required = false) String text,
                                                   @RequestParam(required = false) Set<Long> categories,
                                                   @RequestParam(required = false) Boolean paid,
                                                   @RequestParam(required = false) @DateTimeFormat(pattern =
                                                           "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeStart,
                                                   @RequestParam(required = false) @DateTimeFormat(pattern =
                                                           "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeEnd,
                                                   @RequestParam(defaultValue = "false") Boolean onlyAvailable,
                                                   @RequestParam(required = false) String sort,
                                                   @RequestParam(defaultValue = "0") int from,
                                                   @RequestParam(defaultValue = "1000") int size,
                                                   HttpServletRequest request) {
        log.info("Get events with filter");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        EndpointHit hit = new EndpointHit();
        hit.setApp("main-service");
        hit.setIp(request.getLocalAddr());
        hit.setUri(request.getRequestURI());
        hit.setTimestamp(LocalDateTime.now().format(formatter));
        statsClient.postEndpointHit(hit);
        return eventService.getPublishedEventsWithFilter(text, categories, paid, rangeStart, rangeEnd, onlyAvailable,
                sort, from, size, request);
    }

    @GetMapping("/{id}")
    public EventFullDto getFullInfoAboutEventBuId(@PathVariable Long id, HttpServletRequest request) {
        log.info("Get full info about event Id {}", id);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        EndpointHit hit = new EndpointHit();
        hit.setApp("main-service");
        hit.setIp(request.getLocalAddr());
        hit.setUri(request.getRequestURI());
        hit.setTimestamp(LocalDateTime.now().format(formatter));
        statsClient.postEndpointHit(hit);
        return eventService.getPublishedEventById(id, request);
    }
}
