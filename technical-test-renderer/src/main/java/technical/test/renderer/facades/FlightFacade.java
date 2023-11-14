package technical.test.renderer.facades;

import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import technical.test.renderer.services.FlightService;
import technical.test.renderer.viewmodels.FilterViewModel;
import technical.test.renderer.viewmodels.FlightViewModel;

@Component
public class FlightFacade {

    private final FlightService flightService;

    public FlightFacade(FlightService flightService) {
        this.flightService = flightService;
    }

    public Flux<FlightViewModel> getFlights() {
        return this.flightService.getFlights();
    }

    public Flux<FlightViewModel> getFlightsByFilters(FilterViewModel filters) {
        return this.flightService.getFlightsByFilters(filters);
    }
}
