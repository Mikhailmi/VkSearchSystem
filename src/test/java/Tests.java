import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.sql.SQLException;

public class Tests {

    // по очереди все тесты работают

    @Test
    public void test1() throws SQLException {

        // проверка на предмет очищения введенного пользователем запроса от ненужных символов

        String string = "абв, ека/ абв";
        ByteArrayInputStream in = new ByteArrayInputStream(string.getBytes());
        System.setIn(in);
        SearchSystemApp searchSystem = new SearchSystemApp();

        SearchSystemApp.main(new String[]{});

        Assertions.assertEquals("абв ека абв", searchSystem.userRequest());
    }

    @Test
    public void test2() throws SQLException {

        // проверка вывода данных в консоль

        ByteArrayOutputStream output = new ByteArrayOutputStream();

        String string = "а";
        ByteArrayInputStream in = new ByteArrayInputStream(string.getBytes());
        System.setIn(in);
        SearchSystemApp searchSystem = new SearchSystemApp();
        System.setOut(new PrintStream(output));

        String[] strings = {};
        SearchSystemApp.main(strings);
        String str = output.toString();

        StringBuilder stringBuilder = new StringBuilder("");

        for (String s : str.split("\\p{P}?[ \\t\\n\\r]+")) {

            stringBuilder.append(s);
            stringBuilder.append(" ");
        }
        Assertions.assertEquals("американский футбол смотреть онлайн американский футбол смотреть американский футбол абв где ёжз икл американский футбол главный герой", stringBuilder.toString().trim());
    }

    @Test
    public void test3() throws SQLException {

        // вторая проверка вывода данных в консоль

        ByteArrayOutputStream output = new ByteArrayOutputStream();

        String string = "рубашка";
        ByteArrayInputStream in = new ByteArrayInputStream(string.getBytes());
        System.setIn(in);
        SearchSystemApp searchSystem = new SearchSystemApp();
        System.setOut(new PrintStream(output));

        String[] strings = {};
        SearchSystemApp.main(strings);
        String str = output.toString();

        StringBuilder stringBuilder = new StringBuilder("");

        for (String s : str.split("\\p{P}?[ \\t\\n\\r]+")) {

            stringBuilder.append(s);
            stringBuilder.append(" ");
        }

        Assertions.assertEquals("рубашка белая рубашка купить рубашка зеленая рубашка красная " +
                "рубашка серая",stringBuilder.toString().trim());
    }
}
