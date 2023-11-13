package technical.test.api.endpoints;

import lombok.RequiredArgsConstructor;

import java.util.Map;

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

    @GetMapping(value = "/filterprice", produces = "application/json")
    public Flux<FlightRepresentation> getFlightsByPrice(@RequestParam Map<String, String> filterPrice) {
        return flightFacade.getFlightsByPrice(Double.valueOf(filterPrice.get("minPrice")), Double.valueOf(filterPrice.get("maxPrice")));
    }

    @GetMapping(value = "/filterlocalsiation")
    public Flux<FlightRepresentation> getFlightByLocalisation(@RequestParam Map<String, String> filterLocalisation) {
        return flightFacade.getFlightsByLocalisation(filterLocalisation.get("origin"), filterLocalisation.get("destination"));
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
