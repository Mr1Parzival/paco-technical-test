package technical.test.renderer.clients;

import lombok.extern.slf4j.Slf4j;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import technical.test.renderer.properties.TechnicalApiProperties;
import technical.test.renderer.viewmodels.FilterViewModel;
import technical.test.renderer.viewmodels.FlightViewModel;

@Component
@Slf4j
public class TechnicalApiClient {

    private final TechnicalApiProperties technicalApiProperties;
    private final WebClient webClient;

    public TechnicalApiClient(TechnicalApiProperties technicalApiProperties, final WebClient.Builder webClientBuilder) {
        this.technicalApiProperties = technicalApiProperties;
        // this.webClient = webClientBuilder.build();
        this.webClient = WebClient.builder().baseUrl(technicalApiProperties.getUrl()).build();
        log.info("uri base : " + technicalApiProperties.getUrl());
    }

    public Flux<FlightViewModel> getFlights(FilterViewModel filters) {
        if(filters.isNotNull()) {
            log.info("uri : " + technicalApiProperties.getFlightByFiltersPath());
            return webClient
            .get()
            .uri(uriBuilder -> uriBuilder.path(technicalApiProperties.getFlightByFiltersPath())
            .queryParam("minPrice", filters.getMinPrice())
            .queryParam("maxPrice", filters.getMaxPrice())
            .queryParam("originLoc", filters.getOriginLoc())
            .queryParam("destinationLoc", filters.getDestinationLoc())
            .queryParam("dateStart", filters.getDateStart())
            .queryParam("dateEnd", filters.getDateEnd())
            .build())
            .retrieve()
            .bodyToFlux(FlightViewModel.class);
        }
        log.info("uri : " + technicalApiProperties.getFlightPath());
        return webClient
                .get()
                .uri(technicalApiProperties.getUrl() + technicalApiProperties.getFlightPath())
                .retrieve()
                .bodyToFlux(FlightViewModel.class);
    }



    // public Flux<FlightViewModel> getFlightsByFilters(FilterViewModel filters) {
    //     log.info("uri : " + technicalApiProperties.getFlightByFiltersPath());
    //     return webClient
    //             .get()
    //             .uri(uriBuilder -> uriBuilder.path(technicalApiProperties.getFlightByFiltersPath())
    //                 .queryParam("minPrice", filters.getMinPrice())
    //                 .queryParam("maxPrice", filters.getMaxPrice())
    //                 .queryParam("originLoc", filters.getOriginLoc())
    //                 .queryParam("destinationLoc", filters.getDestinationLoc())
    //                 .queryParam("dateStart", filters.getDateStart())
    //                 .queryParam("dateEnd", filters.getDateEnd())
    //                 .build())
    //             .retrieve()
    //             .bodyToFlux(FlightViewModel.class);
    // }

    public Mono<FlightViewModel> saveFlight(FlightViewModel newFlight) {
        log.info("uri : " + technicalApiProperties.getAdminPath());
        return webClient
                .post()
                .uri(technicalApiProperties.getUrl() + technicalApiProperties.getAdminPath())
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(newFlight), FlightViewModel.class)
                .retrieve()
                .bodyToMono(FlightViewModel.class);
    }
}
