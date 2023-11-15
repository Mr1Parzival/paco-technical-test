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

    public boolean isNotNull() {
        if(this.minPrice != null || this.maxPrice != null || this.originLoc != null || this.destinationLoc != null || this.dateStart != null || this.dateEnd != null) {
            return true;
        }
        return false;
    }
}