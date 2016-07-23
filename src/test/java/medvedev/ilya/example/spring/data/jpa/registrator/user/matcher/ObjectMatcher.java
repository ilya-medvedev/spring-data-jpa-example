package medvedev.ilya.example.spring.data.jpa.registrator.user.matcher;

import medvedev.ilya.example.spring.data.jpa.registrator.user.model.jpa.User;
import medvedev.ilya.example.spring.data.jpa.registrator.user.model.json.request.RegistrationRequest;
import org.mockito.ArgumentMatcher;
import org.mockito.Mockito;

import java.util.Objects;
import java.util.function.Predicate;

public class ObjectMatcher<T> extends ArgumentMatcher<T> {
    private final Predicate<T> predicate;

    private ObjectMatcher(final Predicate<T> predicate) {
        this.predicate = predicate;
    }

    private static <T> T eq(final Predicate<T> predicate) {
        final ObjectMatcher<T> matcher = new ObjectMatcher<>(predicate);

        return Mockito.argThat(matcher);
    }

    public static RegistrationRequest eq(final RegistrationRequest expected) {
        final Predicate<RegistrationRequest> predicate = actual ->
                Objects.equals(expected.getPassword(), actual.getPassword()) &&
                        Objects.equals(expected.getNick(), actual.getNick());

        return ObjectMatcher.eq(predicate);
    }

    public static User eq(final User expected) {
        final Predicate<User> predicate = actual -> Objects.equals(expected.getId(), actual.getId()) &&
                Objects.equals(expected.getPassword(), actual.getPassword()) &&
                Objects.equals(expected.getNick(), actual.getNick());

        return ObjectMatcher.eq(predicate);
    }

    @Override
    public boolean matches(final Object argument) {
        @SuppressWarnings("unchecked")
        final T actual = (T) argument;

        return predicate.test(actual);
    }
}
