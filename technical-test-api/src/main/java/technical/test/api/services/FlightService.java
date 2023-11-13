package technical.test.api.services;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import technical.test.api.record.FlightRecord;
import technical.test.api.repository.FlightRepository;

@Service
@RequiredArgsConstructor
public class FlightService {
    private final FlightRepository flightRepository;

    public Flux<FlightRecord> getAllFlights() {
        return flightRepository.findAll();
    }

    public Flux<FlightRecord> getAllFlightsByPage(Pageable pageable) {
        return flightRepository.findAllByPage(pageable);
    }

    public Flux<FlightRecord> getFlightsByPrice(double minPrice, double maxPrice) {
        return flightRepository.findByPrice(minPrice, maxPrice);
    }

    public Flux<FlightRecord> getFlightsByLocalisation(String origin, String destination) {
        return flightRepository.findByLocalisation(origin, destination);
    }

    public Mono<FlightRecord> addFlights(FlightRecord newFlight) {
        return flightRepository.insert(newFlight);
    }
}
