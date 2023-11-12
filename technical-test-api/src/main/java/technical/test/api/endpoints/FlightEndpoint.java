package technical.test.api.endpoints;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import technical.test.api.facade.FlightFacade;
import technical.test.api.record.FlightRecord;
import technical.test.api.representation.FlightRepresentation;

@RestController
@RequestMapping("/flight")
@RequiredArgsConstructor
public class FlightEndpoint {
    private final FlightFacade flightFacade;

    @GetMapping
    public Flux<FlightRepresentation> getAllFlights() {
        return flightFacade.getAllFlights();
    }

    @GetMapping
    public Flux<FlightRepresentation> getFlightsByPrice(@RequestParam double minPrice, double maxPrice) {
        return flightFacade.getFlightsByPrice(minPrice, maxPrice);
    }

    @GetMapping
    public Flux<FlightRepresentation> getFlightByLocalisation(@RequestParam String origin, String destination) {
        return flightFacade.getFlightsByLocalisation(origin, destination);
    }

    @PostMapping(value = "/add", produces = "application/json")
    public Mono<FlightRepresentation> addFlight(@RequestBody FlightRecord newFlight) {
        Mono<FlightRepresentation> flight = flightFacade.addFlight(newFlight);
        if(flight == null) {
            return null;
        } else {
            return flight;
        }
    }
}
