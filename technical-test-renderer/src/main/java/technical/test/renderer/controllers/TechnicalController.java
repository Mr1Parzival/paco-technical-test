package technical.test.renderer.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import reactor.core.publisher.Mono;
import technical.test.renderer.facades.FlightFacade;
import technical.test.renderer.viewmodels.FilterViewModel;

@Controller
@RequestMapping
@RequiredArgsConstructor
@Slf4j
public class TechnicalController {

    @Autowired
    private FlightFacade flightFacade;

    @GetMapping
    public Mono<String> getMarketPlaceReturnCouponPage(final Model model) {
        log.info("/basepath");
        model.addAttribute("filters", new FilterViewModel());
        model.addAttribute("flights", this.flightFacade.getFlights());
        return Mono.just("pages/index");
    }

    @GetMapping(value= "/filter")
    public Mono<String> getMarketPlaceByFilters(@ModelAttribute FilterViewModel filters, final Model model) {
        log.info("/filterpath");
        model.addAttribute("flights", this.flightFacade.getFlightsByFilters(filters));
        return Mono.just("redirect:/");
    }
}
