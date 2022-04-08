import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

public class SearchSystemApp {

    private static Scanner sc = new Scanner(System.in);
    private static String userRequest = sc.nextLine();

    private static Statement stmt;
    private static Connection connection;

    // переменная для проверки наличия в базе введенного пользователем запроса
    static boolean requestContainsDataBase = false;

    // переменная для хранения id запроса из базы данных
    static int id = 0;

    // метод для очищения запроса пользователя от ненужных символов
    public static String userRequest() {

        StringBuilder stringBuilder = new StringBuilder("");

        for (String s : userRequest.split("\\p{P}?[ \\t\\n\\r]+")) {

            stringBuilder.append(s);
            stringBuilder.append(" ");
        }

        return String.valueOf(stringBuilder).toLowerCase().trim();
    }


    // launcher
    public static void main(String[] args) throws SQLException {
        search();
    }


    private static void search() {
       try {
           // соединяемся с базой данных
           connect();

           // при первом проходе по базе создаем дополнительную таблицу - указатель (по первому символу)
           // pointerBaseInit();

           // основной поисковый метод
           mySqlSearch();

       } catch (Exception e) {
           e.printStackTrace();
       } finally {
           disconnect();
       }
    }

    // метод, отвечающий за подключение к базе данных
    private static void connect() throws SQLException {
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/requestbase", "root", "Gkxpupva123");
        stmt = connection.createStatement();
    }

    // метод, закрывающий соединение с базой данных
    private static void disconnect() {
        try {
            if (stmt != null) {
                stmt.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    // основной поисковый метод
    private static void mySqlSearch() {


        // блок кода для поиска во вспомогательной таблице pointer порядкового номера base_number,
        // с помощью которого оптимизируется поиск по совпадающим запросам в таблице requests
        int cursor = 0;
        {
            try (ResultSet resultSet = stmt.executeQuery("Select * FROM requestbase.pointer")) {
                while (resultSet.next()) {
                    char a = resultSet.getString("first_char").charAt(0);
                    char b = userRequest().charAt(0);

                    if (a == b) {
                        cursor = resultSet.getInt("base_number") ;
                        break;
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }


        try (ResultSet rs = stmt.executeQuery("SELECT * FROM requestbase.requests")) {
            requestContainsDataBase = false;

            // перемещаем указатель на нужную строку в базе данных, почему-то метод rs.absolute(cursor) выдает ошибку;
            for (int q = 1; q < cursor; q++) {
                rs.next();
            }

            // мапа для записи в из базы нее подходящих запросов "request_name" и их количества "request_amount";
            // инициализирую значение больше 5, так как ключами являются совпадающие запросы из базы,
            // а ранжирование ведется по значениям
            Map<String, Integer > resultMap = new HashMap<>(32);

            // цикл для прохода по базе с запросами
            while (rs.next()) {

                // условие выхода из цикла поиска, если запрос состоит из одного символа
                if (userRequest().length() == 1 && rs.getString("request_name").charAt(0) != userRequest().charAt(0)) {
                    break;
                }

                // проверка наличия запроса, полностью совпадающего с запросом пользователя, в базе
                if (rs.getString("request_name").equals(userRequest())) {
                    requestContainsDataBase = true;
                    id = rs.getInt("id");

                }

                // переменная для хранения количества прошлых обращений к запросу, который достаем из базы
                int hashKey = rs.getInt("request_amount");

                // переменная для хранения запроса, который достаем из базы
                StringBuilder lineBuilder = new StringBuilder("");

                for (String s : rs.getString("request_name").toLowerCase().trim().split("\\p{P}?[ \\t\\n\\r]+")) {

                    lineBuilder.append(s);
                    lineBuilder.append(" ");
                }

                if (userRequest().length() > 1) { // если запрос состоит больше чем из одной буквы или цифры

                    // минимальная длина строки для сравнения
                    int min = (lineBuilder.length() < userRequest().length() ? lineBuilder.length() - 1 : userRequest().length());

                    // счетчик совпавших символов
                    int compareCount = 0;

                    // счетчик опечаток (допускается не больше одной подрят)
                    int typoCount = 0;

                    for (int i = 0; i < min && min < lineBuilder.length() - 1; i++) {

                        // если прослеживается вторая опечатка подрят во фразе, то break
                        if (i > 0 && lineBuilder.charAt(i) != userRequest().charAt(i) && typoCount == 1) {
                            break;
                        }

                        // нахождение первой опечатки во фразе
                        if (lineBuilder.charAt(i) != userRequest().charAt(i)) {
                            typoCount++;
                        }

                        compareCount++;
                    }
                    // если запрос пользователя совпадает с данными, которые есть в базе, то считываем данные из базы
                    if (min == compareCount) {
                        resultMap.put(String.valueOf(lineBuilder).trim(), hashKey);
                    }
                } else {
                    resultMap.put(String.valueOf(lineBuilder).trim(), hashKey);
                }

            }

            // далее осуществляется вывод не более 5 совпадающих запросов

            List<String> resultStr = new ArrayList<>(resultMap.size());

            for (int i = 0; i < 5; i++) {

                try {
                    resultStr.add(String.valueOf(resultMap.entrySet().stream()
                            .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                            .collect(Collectors.toList()).get(i)));

                    for (String s : resultStr.get(i).split("=")) {

                        System.out.println(s);
                        break;
                    }
                } catch (IndexOutOfBoundsException ex) {

                }
            }

            // обновляем счетчик запросов в базе данных (если введенный пользователем запрос повторяется),
            // либо добавляем этот запрос в базу, и обновляем указатель
            updateOrInsert();

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }


    private static void updateOrInsert() throws SQLException {

        /**
         если строка, введенная в консоль, ранее содержалась в базе данных "requestbase.requests",
         то увеличиваем на единицу значение в столбце request_amount,
         иначе добавляем эту строку в базу данных
         */

        if (requestContainsDataBase == true) {
            String sql = "UPDATE requestbase.requests SET request_amount = request_amount + 1 WHERE id = " + id;
            stmt.executeUpdate(sql);
        } else {
            try (PreparedStatement ps = connection.prepareStatement(
                    "insert into requestBase.requests(request_name, request_amount) " +
                            "values (?, ?)")) {

                ps.setString(1, userRequest());
                ps.setInt(2, 1);
                ps.addBatch();
                ps.executeBatch();
            } catch (Exception ex) {
                //   ex.printStackTrace();
            }
            try (PreparedStatement ps = connection.prepareStatement(
                    "insert into requestBase.pointer(first_char, base_number) " +
                            "values (?, ?)")) {

                ps.setString(1, String.valueOf(userRequest().charAt(0)));
                ps.setInt(2, 0);
                ps.addBatch();
                ps.executeBatch();
            } catch (Exception ex) {
                //   ex.printStackTrace();
            }
            int newBaseNumber = 0;

            try (ResultSet rs = stmt.executeQuery("SELECT * FROM requestbase.pointer")) {
                while (rs.next()) {
                    boolean a = rs.getString("first_char").equals(String.valueOf(userRequest().charAt(0)));
                    if (a == true){
                        rs.next();
                        newBaseNumber = rs.getInt("base_number");
                        break;
                    }
                }
            } catch (Exception ex){
                ex.printStackTrace();
            }
            String sql = "UPDATE requestbase.pointer SET base_number = base_number + 1 WHERE base_number >= " + newBaseNumber;
            stmt.executeUpdate(sql);
            sql = "UPDATE requestbase.pointer SET base_number = " + newBaseNumber + " WHERE base_number = 0";
            stmt.executeUpdate(sql);


        }

    }

    /**
     метод создания вспомогательной таблицы с указателями строку в таблице requests,
     которая начинается на тот же символ, что и введенный пользователем запрос
     */

    private static void pointerBaseInit(){

        String word, previousWord = "";
        int listNumber = 0;

        try (ResultSet rsRequest = stmt.executeQuery("SELECT * FROM requestbase.requests")){

            while (rsRequest.next()) {
                listNumber++;

                for (String s : rsRequest.getString("request_name").toLowerCase().trim().split("\\p{P}?[ \\t\\n\\r]+")) {
                    word = s;
                    if (previousWord.equals(word)) {
                        break;
                    }
                    try (PreparedStatement ps = connection.prepareStatement(
                            "insert into requestBase.pointer(first_char, base_number) " +
                                    "values (?, ?)")) {

                        ps.setString(1, String.valueOf(word.charAt(0)));
                        ps.setInt(2, listNumber);
                        ps.addBatch();
                        ps.executeBatch();
                    } catch (Exception ex) {
                      //  ex.printStackTrace();
                    }
                    previousWord = word;
                    break;
                }
            }
        } catch (SQLException e) {
        //    e.printStackTrace();
        }
    }
}