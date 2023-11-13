package technical.test.api.repository;

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
