package hu.unideb.bootstrap_test;

import hu.unideb.bootstrap_test.model.Person;
import hu.unideb.bootstrap_test.model.PersonRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
class BootstrapTestApplicationTests {

    @Autowired
    PersonRepository personRepository;

    @Test
    void testPersonAdd() {
        Person expected = Person.builder()
                .firstName("Tom")
                .lastName("Smith")
                .email("tom.smith@gmail.com")
                .password("uztQWER1234_")
                .enabled(true)
                .build();

        Person actual = personRepository.save(expected);

        Assertions.assertThat(actual).isNotNull();
        Assertions.assertThat(actual).isEqualTo(expected);
    }

}
