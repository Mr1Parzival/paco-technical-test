package technical.test.renderer.viewmodels;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class FilterViewModel {
    private int id;
    private Double minPrice;
    private Double maxPrice;
    private String originLoc;
    private String destinationLoc;
    private LocalDateTime dateStart;
    private LocalDateTime dateEnd;

}
