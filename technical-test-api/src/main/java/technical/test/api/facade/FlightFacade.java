package technical.test.api.facade;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import technical.test.api.mapper.AirportMapper;
import technical.test.api.mapper.FlightMapper;
import technical.test.api.record.AirportRecord;
import technical.test.api.record.FlightRecord;
import technical.test.api.representation.FlightRepresentation;
import technical.test.api.services.AirportService;
import technical.test.api.services.FlightService;

@Component
@RequiredArgsConstructor
public class FlightFacade {
    private final FlightService flightService;
    private final AirportService airportService;
    private final FlightMapper flightMapper;
    private final AirportMapper airportMapper;

    public Flux<FlightRepresentation> getAllFlights() {
        return flightService.getAllFlights()
                .flatMap(flightRecord -> airportService.findByIataCode(flightRecord.getOrigin())
                        .zipWith(airportService.findByIataCode(flightRecord.getDestination()))
                        .flatMap(tuple -> {
                            AirportRecord origin = tuple.getT1();
                            AirportRecord destination = tuple.getT2();
                            FlightRepresentation flightRepresentation = this.flightMapper.convert(flightRecord);
                            flightRepresentation.setOrigin(this.airportMapper.convert(origin));
                            flightRepresentation.setDestination(this.airportMapper.convert(destination));
                            return Mono.just(flightRepresentation);
                        }));
    }

    public Flux<FlightRepresentation> getFlightsByPrice(double minPrice, double maxPrice) {
        return flightService.getFlightsByPrice(minPrice, maxPrice).flatMap(flightRecord -> airportService.findByIataCode(flightRecord.getOrigin())
        .zipWith(airportService.findByIataCode(flightRecord.getDestination()))
        .flatMap(tuple -> {
            AirportRecord origin = tuple.getT1();
            AirportRecord destination = tuple.getT2();
            FlightRepresentation flightRepresentation = this.flightMapper.convert(flightRecord);
            flightRepresentation.setOrigin(this.airportMapper.convert(origin));
            flightRepresentation.setDestination(this.airportMapper.convert(destination));
            return Mono.just(flightRepresentation);
        }));
    }

    public Flux<FlightRepresentation> getFlightsByLocalisation(String origin, String destination) {
        return flightService.getFlightsByLocalisation(origin, destination).flatMap(flightRecord -> airportService.findByIataCode(flightRecord.getOrigin())
        .zipWith(airportService.findByIataCode(flightRecord.getDestination()))
        .flatMap(tuple -> {
            AirportRecord origin_airport = tuple.getT1();
            AirportRecord destination_airport = tuple.getT2();
            FlightRepresentation flightRepresentation = this.flightMapper.convert(flightRecord);
            flightRepresentation.setOrigin(this.airportMapper.convert(origin_airport));
            flightRepresentation.setDestination(this.airportMapper.convert(destination_airport));
            return Mono.just(flightRepresentation);
        }));
    }

    public Mono<FlightRepresentation> addFlight(FlightRecord newFlight) {
        return flightService.addFlights(newFlight)
        .flatMap(flightRecord -> airportService.findByIataCode(flightRecord.getOrigin())
            .zipWith(airportService.findByIataCode(flightRecord.getDestination()))
            .flatMap(tuple -> {
                AirportRecord origin = tuple.getT1();
                AirportRecord destination = tuple.getT2();
                FlightRepresentation flightRepresentation = this.flightMapper.convert(flightRecord);
                flightRepresentation.setOrigin(this.airportMapper.convert(origin));
                flightRepresentation.setDestination(this.airportMapper.convert(destination));
                return Mono.just(flightRepresentation);
            }));
    }
}
