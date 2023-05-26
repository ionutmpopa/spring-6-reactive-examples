package guru.springframework.spring6reactiveexamples.repository;

import guru.springframework.spring6reactiveexamples.domain.Person;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@SpringBootTest
class PersonRepositoryImplTest {

    @Autowired
    PersonRepository personRepository;


    @Test
    void testMonoById_found() {

        Mono<Person> personMono = personRepository.getById(2);
        Assertions.assertThat(personMono.hasElement().block()).isTrue();

    }

    @Test
    void testMonoById_foundStepVerifier() {

        Mono<Person> personMono = personRepository.getById(2);
        StepVerifier.create(personMono).expectNextCount(1).verifyComplete();

    }

    @Test
    void testMonoById_notFound() {

        Mono<Person> personMono = personRepository.getById(9);
        Assertions.assertThat(personMono.hasElement().block()).isFalse();

    }

    @Test
    void testMonoById_notFoundStepVerifier() {

        Mono<Person> personMono = personRepository.getById(9);
        StepVerifier.create(personMono).expectNextCount(0).verifyComplete();

    }

    @Test
    void testMonoByIdBlock() {

        Mono<Person> personMono = personRepository.getById(1);

        Person person = personMono.block();

        assert person != null;
        System.out.println(person);

    }

    @Test
    void testMonoById_subscriber() {

        Mono<Person> personMono = personRepository.getById(2);
        personMono.subscribe(person -> System.out.println(person.toString()));

    }

    @Test
    void testMonoById_map() {

        Mono<Person> personMono = personRepository.getById(3);
        personMono.map(Person::getLastName).subscribe(System.out::println);

    }

    @Test
    void findAll_blocking() {

        Flux<Person> personMono = personRepository.findAll();

        Person person = personMono.blockFirst();

        assert person != null;
        System.out.println(person);

    }

    @Test
    void findAll_subscriber() {

        Flux<Person> personFlux = personRepository.findAll();
        personFlux.subscribe(person -> System.out.println(person.toString()));
    }

    @Test
    void findAll_map() {

        Flux<Person> personFlux = personRepository.findAll();
        personFlux.map(Person::getLastName).subscribe(System.out::println);

    }

    @Test
    void findAll_toList() {

        Flux<Person> personFlux = personRepository.findAll();
        Mono<List<Person>> personList = personFlux.collectList();
        personList.subscribe(System.out::println);

    }

    @Test
    void findAll_toMono() {

        Flux<Person> personFlux = personRepository.findAll();
        Mono<Person> personMono = personFlux.filter(person -> person.getLastName().equals("Doe4")).next();
        personMono.subscribe(System.out::println);
    }

    @Test
    void testFindPersonByIdNotFound() {
        Flux<Person> personFlux = personRepository.findAll();

        final Integer id = 4;

        personFlux.filter(person -> Objects.equals(person.getId(), id)).single()
            .doOnError(throwable -> {
                System.out.println("Error occurred in flux");
                System.out.println(throwable.toString());
            })
            .subscribe(person -> {
            System.out.println(person.toString());
        }, throwable -> {
            System.out.println("Error occurred in the mono");
            System.out.println(throwable.toString());
        });
    }


}
