import com.dingwd.commons.DAssert;
import com.dingwd.commons.exceptions.DParamException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class DAssertTest {

    @Test
    public void notNilShouldThrowExceptionWhenParamIsNull() {
        Assertions.assertThrows(DParamException.class, () -> DAssert.notNil(null));
    }

    @Test
    public void notNilShouldNotThrowExceptionWhenParamIsNotNull() {
        DAssert.notNil(new Object());
    }

    @Test
    public void isNilShouldThrowExceptionWhenParamIsNotNull() {
        Assertions.assertThrows(DParamException.class, () -> DAssert.isNil(new Object()));
    }

    @Test
    public void isNilShouldNotThrowExceptionWhenParamIsNull() {
        DAssert.isNil(null);
    }

    @Test
    public void haveTextShouldThrowExceptionWhenParamIsNull() {
        Assertions.assertThrows(DParamException.class, () -> DAssert.haveText(null));
    }

    @Test
    public void haveTextShouldThrowExceptionWhenParamIsBlank() {
        Assertions.assertThrows(DParamException.class, () -> DAssert.haveText(""));
    }

    @Test
    public void haveTextShouldNotThrowExceptionWhenParamHasText() {
        DAssert.haveText("text");
    }

    @Test
    public void isEmailShouldThrowExceptionWhenParamIsNull() {
        Assertions.assertThrows(DParamException.class, () -> DAssert.isEmail(null));
    }

    @Test
    public void isEmailShouldThrowExceptionWhenParamIsInvalidEmail() {
        Assertions.assertThrows(DParamException.class, () -> DAssert.isEmail("invalidEmail"));
    }

    @Test
    public void isEmailShouldNotThrowExceptionWhenParamIsValidEmail() {
        DAssert.isEmail("valid@email.com.cn");
    }
}