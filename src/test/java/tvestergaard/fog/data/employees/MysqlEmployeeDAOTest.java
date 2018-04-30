package tvestergaard.fog.data.employees;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import tvestergaard.fog.data.TestDataSource;

import java.sql.Connection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;
import static tvestergaard.fog.Helpers.randomBoolean;
import static tvestergaard.fog.Helpers.randomString;
import static tvestergaard.fog.data.cladding.CladdingColumn.ID;
import static tvestergaard.fog.data.constraints.Constraint.*;
import static tvestergaard.fog.data.employees.Role.*;

public class MysqlEmployeeDAOTest
{

    private static final MysqlDataSource  source = TestDataSource.getSource();
    private static final MysqlEmployeeDAO dao    = new MysqlEmployeeDAO(source);

    private Employee employee1;
    private Employee employee2;
    private Employee employee3;
    private Employee employee4;
    private Employee employee5;

    private <T> Set<T> set(T... values)
    {
        Set<T> set = new HashSet<>();
        for (T value : values)
            set.add(value);

        return set;
    }

    @Before
    public void createData() throws Exception
    {
        employee1 = dao.create(EmployeeBlueprint.from("name1", "username1", "password1", set(HEAD_OF_CENTER), true));
        employee2 = dao.create(EmployeeBlueprint.from("name2", "username2", "password2", set(SALESMAN), false));
        employee3 = dao.create(EmployeeBlueprint.from("name3", "username3", "password3", set(HEAD_OF_MATERIALS), true));
        employee4 = dao.create(EmployeeBlueprint.from("name4", "username4", "password4", set(), false));
        employee5 = dao.create(EmployeeBlueprint.from("name5", "username5", "password5", set(HEAD_OF_CENTER, HEAD_OF_MATERIALS), true));
    }

    @After
    public void after() throws Exception
    {
        Connection connection = TestDataSource.getSource().getConnection();
        connection.createStatement().executeUpdate("DELETE FROM roles");
        connection.createStatement().executeUpdate("DELETE FROM employees");
    }

    @Test
    public void get() throws Exception
    {
        List<Employee> employees = dao.get();

        assertEquals(5, employees.size());
        assertEquals(employee1, employees.get(0));
        assertEquals(employee2, employees.get(1));
        assertEquals(employee3, employees.get(2));
        assertEquals(employee4, employees.get(3));
        assertEquals(employee5, employees.get(4));
    }

    @Test
    public void getWhereEquals() throws Exception
    {
        List<Employee> employees = dao.get(where(eq(EmployeeColumn.ID, employee1.getId())));

        assertEquals(1, employees.size());
        assertEquals(employee1, employees.get(0));
    }

    @Test
    public void getWhereLike() throws Exception
    {
        List<Employee> employees = dao.get(where(like(EmployeeColumn.NAME, "name%")));

        assertEquals(5, employees.size());
        assertEquals(employee1, employees.get(0));
        assertEquals(employee2, employees.get(1));
        assertEquals(employee3, employees.get(2));
        assertEquals(employee4, employees.get(3));
        assertEquals(employee5, employees.get(4));
    }

    @Test
    public void getOrderBy() throws Exception
    {
        List<Employee> employees = dao.get(order(EmployeeColumn.NAME, desc()));

        assertEquals(5, employees.size());
        assertEquals(employee5, employees.get(0));
        assertEquals(employee4, employees.get(1));
        assertEquals(employee3, employees.get(2));
        assertEquals(employee2, employees.get(3));
        assertEquals(employee1, employees.get(4));
    }

    @Test
    public void getLimit() throws Exception
    {
        List<Employee> employees = dao.get(limit(2));

        assertEquals(2, employees.size());
        assertEquals(employee1, employees.get(0));
        assertEquals(employee2, employees.get(1));
    }

    @Test
    public void getOffset() throws Exception
    {
        List<Employee> employees = dao.get(limit(2), offset(1));

        assertEquals(employee2, employees.get(0));
        assertEquals(employee3, employees.get(1));
    }

    @Test
    public void first() throws Exception
    {
        assertEquals(employee3, dao.first(where(eq(ID, employee3.getId()))));
        assertNull(dao.first(where(eq(ID, -1))));
    }

    @Test
    public void create() throws Exception
    {
        String    name     = randomString();
        String    username = randomString();
        String    password = randomString();
        boolean   active   = randomBoolean();
        Set<Role> roles    = new HashSet<>();
        roles.add(HEAD_OF_CENTER);
        roles.add(HEAD_OF_MATERIALS);

        Employee actual = dao.create(EmployeeBlueprint.from(name, username, password, roles, active));
        assertEquals(name, actual.getName());
        assertEquals(username, actual.getUsername());
        assertEquals(password, actual.getPassword());
        assertEquals(roles, actual.getRoles());
        assertEquals(active, actual.isActive());
    }

    @Test
    public void update() throws Exception
    {
        employee1.setName(randomString());
        employee1.setUsername(randomString());
        employee1.setPassword(randomString());
        employee1.setActive(false);

        assertTrue(dao.update(employee1));

        List<Employee> actual = dao.get(where(eq(ID, employee1.getId())));
        assertEquals(employee1, actual.get(0));
    }
}