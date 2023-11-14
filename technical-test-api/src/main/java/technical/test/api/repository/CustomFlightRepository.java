package technical.test.api.repository;

import java.util.Map;

import reactor.core.publisher.Flux;
import technical.test.api.record.FlightRecord;

public interface CustomFlightRepository {
    public Flux<FlightRecord> findByFilters(Map<String, String> filters);
    public Flux<FlightRecord> findByPrice(double minPrice, double maxPrice);
    public Flux<FlightRecord> findByLocalisation(String origin, String Destination);
}
