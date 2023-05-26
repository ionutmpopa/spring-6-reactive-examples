package guru.springframework.spring6reactiveexamples.repository;

import guru.springframework.spring6reactiveexamples.domain.Person;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class PersonRepositoryImpl implements PersonRepository {

    Person person1 = Person.builder().id(1).firstName("John").lastName("Doe1").build();
    Person person2 = Person.builder().id(2).firstName("John").lastName("Doe2").build();
    Person person3 = Person.builder().id(3).firstName("John").lastName("Doe3").build();
    Person person4 = Person.builder().id(4).firstName("John").lastName("Doe4").build();

    @Override
    public Mono<Person> getById(final Integer id) {
        return findAll().filter(person -> person.getId().equals(id)).next();
    }

    @Override
    public Flux<Person> findAll() {
        return Flux.just(person1, person2, person3, person4);
    }
}
