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
        // TODO: revoir les inclusions
        if(filters.get("minPrice") != "" && filters.get("maxPrice") != "") {
            critPrice = Criteria.where("price").gt(Double.valueOf(filters.get("minPrice"))).lt(Double.valueOf(filters.get("maxPrice")));
            query.addCriteria(critPrice);
        } else if(filters.get("minPrice") != "") {
            critPrice = Criteria.where("price").gt(Double.valueOf(filters.get("minPrice")));
            query.addCriteria(critPrice);
        } else if(filters.get("maxPrice") != "") {
            critPrice = Criteria.where("price").lt(Double.valueOf(filters.get("maxPrice")));
            query.addCriteria(critPrice);
        }

        if(filters.get("originLoc") != "" && filters.get("destinationLoc") != ""){
            critLoc = Criteria.where("origin").is(filters.get("originLoc")).andOperator(Criteria.where("destination").is(filters.get("destinationLoc")));
            query.addCriteria(critLoc);
        } else if(filters.get("originLoc") != ""){
            critLoc = Criteria.where("origin").is(filters.get("originLoc"));
            query.addCriteria(critLoc);
        } else if (filters.get("destinationLoc") != "") {
            critLoc = Criteria.where("destination").is(filters.get("destinationLoc"));
            query.addCriteria(critLoc);
        }

        if(filters.get("dateStart") != "" && filters.get("dateEnd") != ""){
            critDate = Criteria.where("departure").is(LocalDateTime.parse(filters.get("dateStart"))).orOperator(Criteria.where("arrival").is(LocalDateTime.parse(filters.get("dateEnd"))));
            query.addCriteria(critDate);
        } else if(filters.get("dateStart") != ""){
            critDate = Criteria.where("departure").is(LocalDateTime.parse(filters.get("dateStart")));
            query.addCriteria(critDate);
        } else if(filters.get("dateEnd") != "") {
            critDate = Criteria.where("arrival").is(LocalDateTime.parse(filters.get("dateEnd")));
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
