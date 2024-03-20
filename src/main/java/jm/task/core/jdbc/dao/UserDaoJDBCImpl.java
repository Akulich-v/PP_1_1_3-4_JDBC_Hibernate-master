package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.util.Util;
import jm.task.core.jdbc.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class UserDaoJDBCImpl implements UserDao {
    Util util = new Util();

    Connection connection = util.getConnection();


    public UserDaoJDBCImpl() {

    }


    public void createUsersTable() {
        //Создание таблицы
        PreparedStatement preparedStatement = null;
        String sql = "create table if not exists mybdtwst (id int auto_increment primary key, name varchar (50), lastName varchar (50),age tinyint)";

        try {
            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.executeUpdate();
            System.out.println("Создание таблицы выполнено");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } /*finally {
            if(preparedStatement != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }*/


    }

    public void dropUsersTable() {
        //Удаление таблицы
        PreparedStatement preparedStatement = null;
        String sql = "drop table if exists mybdtwst";

        try {
            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.executeUpdate();
            System.out.println("Удаление таблицы выполнено");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } /*finally {
            if(preparedStatement != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }*/
    }

    public void saveUser(String name, String lastName, byte age) {
        //Добавление Юзера в таблицу
        PreparedStatement preparedStatement = null;
        String sql = "insert into mybdtwst (name, lastName, age) values (?,?,?)";

        try {
            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1,name);
            preparedStatement.setString(2,lastName);
            preparedStatement.setByte(3,age);

            preparedStatement.executeUpdate();
            System.out.println("Пользователь добавлен");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } /*finally {
            if(preparedStatement != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }*/
    }

    public void removeUserById(long id) {
        //Удаление Юзера из таблицы по id
        PreparedStatement preparedStatement = null;
        String sql = "DELETE FROM mybdtwst where id=?";

        try {
            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setLong(1,id);

            preparedStatement.executeUpdate();
            System.out.println("Пользователь удалён");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } /*finally {
            if(preparedStatement != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }*/

    }

    public List<User> getAllUsers() {
        //Получение всех Юзеров из таблицы
        List<User> usersList = new ArrayList<>();
        Statement statement = null;
        String sql = "Select id, name, lastName, age FROM mybdtwst";

        try {
            statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()){
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge(resultSet.getByte("age"));

                usersList.add(user);
                System.out.println(usersList.toString());
            }
            System.out.println("Выведены все юзеры");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } /*finally {
            if(statement != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }*/
        return usersList;
    }


    public void cleanUsersTable() {
        //Очистка содержания таблицы
        PreparedStatement preparedStatement = null;
        String sql = "Delete from mybdtwst";

        try {
            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.executeUpdate();
            System.out.println("Данные таблицы удалены");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } /*finally {
            if(preparedStatement != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }*/
    }
}
