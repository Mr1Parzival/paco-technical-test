package technical.test.api.repository;

import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import reactor.core.publisher.Flux;
import technical.test.api.record.FlightRecord;

public class CustomFlightRepositoryImpl implements CustomFlightRepository {
    private final ReactiveMongoTemplate mongoTemplate;
    
    @Autowired
    public CustomFlightRepositoryImpl(ReactiveMongoTemplate mongoTemplate){
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public Flux<FlightRecord> findByFilters(Map<String, String> filters){
        Query query = new Query();
        Criteria critPrice = new Criteria();
        Criteria critLoc = new Criteria();
        Criteria critDate = new Criteria();

        if(filters.get("minPrice") != null || filters.get("maxPrice") != null) {
            critPrice = Criteria.where("price").gt(Double.valueOf(filters.get("minPrice"))).lt(Double.valueOf(filters.get("maxPrice")));
            query.addCriteria(critPrice);
        }
        if(filters.get("originLoc") != null || filters.get("destinationLoc") != null){
            // TODO: revoir les inclusions
            critLoc = Criteria.where("origin").is(filters.get("originLoc")).orOperator(Criteria.where("destination").is(filters.get("destinationLoc")));
            query.addCriteria(critLoc);
        }
        if(filters.get("dateStart") != null || filters.get("dateEnd") != null){
            critDate = Criteria.where("departure").is(LocalDateTime.parse(filters.get("dateStart"))).orOperator(Criteria.where("arrival").is(LocalDateTime.parse(filters.get("dateEnd"))));
            query.addCriteria(critDate);
        }

        return mongoTemplate.find(query, FlightRecord.class);
    }

    @Override
    public Flux<FlightRecord> findByPrice(double minPrice, double maxPrice){
        Query query = new Query(Criteria.where("price").gt(minPrice).lt(maxPrice));   
        return mongoTemplate.find(query, FlightRecord.class);
    }

    @Override
    public Flux<FlightRecord> findByLocalisation(String origin, String destination){
        Query query;
        if(origin != null && destination != null){
            query = new Query(Criteria.where("origin").is(origin).andOperator(Criteria.where("destination").is(destination)));    
        } else if(origin != null) {
            query = new Query(Criteria.where("origin").is(origin));    
        } else if(destination != null){
            query = new Query(Criteria.where("destination").is(destination));    
        } else {
            query = null;
        }
        return mongoTemplate.find(query, FlightRecord.class);
    }
}
