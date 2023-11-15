package technical.test.renderer.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import reactor.core.publisher.Mono;
import technical.test.renderer.facades.FlightFacade;
import technical.test.renderer.viewmodels.FilterViewModel;
import technical.test.renderer.viewmodels.FlightViewModel;

@Controller
@RequestMapping
@RequiredArgsConstructor
@Slf4j
public class TechnicalController {

    @Autowired
    private FlightFacade flightFacade;

    @GetMapping
    public Mono<String> getMarketPlaceReturnCouponPage(@ModelAttribute FilterViewModel filters, final Model model) {
        log.info("/basepath");
        if(model.getAttribute("filters") == null) {
            model.addAttribute("filters", new FilterViewModel());
        } else {
            model.addAttribute("filters", filters);
        }
        model.addAttribute("flights", this.flightFacade.getFlights(filters));
        return Mono.just("pages/index");
    }

    // @GetMapping(value= "/filter")
    // public Mono<String> getMarketPlaceByFilters(@ModelAttribute FilterViewModel filters, final Model model) {
    //     log.info("/filterpath");
    //     model.addAttribute("filters", filters);
    //     model.addAttribute("flights", this.flightFacade.getFlightsByFilters(filters));
    //     return Mono.just("redirect:/");
    // }
    
    @GetMapping(value = "/admin")
    public Mono<String> getAdminPage(Model model) {
        log.info("/admin");
        model.addAttribute("newFlight", new FlightViewModel());
        return Mono.just("pages/admin");
    }

    @PostMapping(value = "/admin")
    public Mono<String> postFlight(@ModelAttribute FlightViewModel newFlight, Model model){
        log.info("/addflightpath");
        Mono<FlightViewModel> flight = flightFacade.createFlight(newFlight);
        model.addAttribute("flights", this.flightFacade.getFlights(new FilterViewModel()));
        return Mono.just("redirect:/");
    }

    
}
