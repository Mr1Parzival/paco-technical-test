package technical.test.api.repository;

import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;

import reactor.core.publisher.Flux;
import technical.test.api.record.FlightRecord;

public interface CustomFlightRepository extends ReactiveSortingRepository<FlightRecord, UUID>{
    public Flux<FlightRecord> findByPrice(double minPrice, double maxPrice);
    public Flux<FlightRecord> findByLocalisation(String origin, String Destination);
    public Flux<FlightRecord> findAllByPage(Pageable pageable);
}
