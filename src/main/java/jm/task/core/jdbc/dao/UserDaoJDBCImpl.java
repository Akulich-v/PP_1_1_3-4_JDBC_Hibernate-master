package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.util.Util;
import jm.task.core.jdbc.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class UserDaoJDBCImpl implements UserDao {
    private Util util = new Util();
    public UserDaoJDBCImpl() {
    }

    @Override
    public void createUsersTable() {
        //Создание таблицы
        Connection connection = util.getConnection();
        String sql = "create table if not exists mybdtwst (id int auto_increment primary key, name varchar (50), lastName varchar (50),age tinyint)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)){

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void dropUsersTable() {
        //Удаление таблицы
        Connection connection = util.getConnection();
        String sql = "drop table if exists mybdtwst";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void saveUser(String name, String lastName, byte age) {
        //Добавление Юзера в таблицу
        Connection connection = util.getConnection();
        String sql = "insert into mybdtwst (name, lastName, age) values (?,?,?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setString(1,name);
            preparedStatement.setString(2,lastName);
            preparedStatement.setByte(3,age);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void removeUserById(long id) {
        //Удаление Юзера из таблицы по id
        Connection connection = util.getConnection();
        String sql = "DELETE FROM mybdtwst where id=?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setLong(1,id);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public List<User> getAllUsers() {
        //Получение всех Юзеров из таблицы
        Connection connection = util.getConnection();
        List<User> usersList = new ArrayList<>();
        String sql = "Select id, name, lastName, age FROM mybdtwst";

        //try (Statement statement = connection.createStatement()){
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)){

            ResultSet resultSet = preparedStatement.executeQuery(sql);

            while (resultSet.next()){
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge(resultSet.getByte("age"));

                usersList.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return usersList;
    }
    @Override
    public void cleanUsersTable() {
        //Очистка содержания таблицы
        Connection connection = util.getConnection();
        String sql = "Delete from mybdtwst";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)){

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
