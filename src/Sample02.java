import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

public class Sample02 {

    private static Random random = new Random();

    /**
     * TODO: 2. Переработать метод generateEmployee(). Метод должен возвращать
     * любого случайного сотрудника разного типа (Worker, Freelancer)
     * @return
     */
    public static Employee generateEmployee(){
        String[] names = new String[] { "Анатолий", "Глеб", "Клим", "Мартин", "Лазарь", "Владлен", "Клим", "Панкратий", "Рубен", "Герман" };
        String[] surnames = new String[] { "Григорьев", "Фокин", "Шестаков", "Хохлов", "Шубин", "Бирюков", "Копылов", "Горбунов", "Лыткин", "Соколов" };
        String[] hobby = new String[]{ "Шахматы", "Программирование","Компьютерные игры", "Легкая атлетика", "Математика"};

        int salary = random.nextInt(900, 1500);
        int salaryIndex = random.nextInt(28, 31);
        int age = random.nextInt(16,70);
        int underworking = random.nextInt(100,2000);
        int a = random.nextInt(2);
        if(a == 1) {
            return new Worker(names[random.nextInt(10)], surnames[random.nextInt(10)], salary * salaryIndex, age);
        }
        else{
            return new Freelancer(names[random.nextInt(10)], surnames[random.nextInt(10)], age, salary * salaryIndex, underworking, hobby[random.nextInt(5)]);
        }
    }

    public static void main(String[] args) {

        //Worker worker = new Worker("Глеб", "Григорьев", 120000);
        //System.out.println(worker);

        Employee[] employees = new Employee[10];
        for (int i = 0; i < employees.length; i++){
            employees[i] = generateEmployee();
        }

        for (Employee employee: employees) {
            System.out.println(employee);
        }

        Arrays.sort(employees, new SalaryComparator());

        System.out.println("\n*** Отсортированный массив сотрудников по размеру зарплаты ***\n");

        for (Employee employee: employees) {
            System.out.println(employee);
        }
        Arrays.sort(employees, new AgeComparator());

        System.out.println("\n*** Отсортированный массив сотрудников по возрасту ***\n");

        for (Employee employee: employees) {
            System.out.println(employee);
        }
        Arrays.sort(employees, new SurNameComparator());

        System.out.println("\n*** Отсортированный массив сотрудников по фамилиям ***\n");

        for (Employee employee: employees) {
            System.out.println(employee);
        }
        Arrays.sort(employees, new EmplComparator());

        System.out.println("\n*** Отсортированный массив сотрудников по виду деятельности ***\n");

        for (Employee employee: employees) {
            System.out.println(employee);
        }


        /*int a = 2;
        boolean f = true;
        String str = "Hello!";
        System.out.println(worker);
        System.out.println(a);
        System.out.println(f);
        System.out.println(str);*/

    }

}

class SalaryComparator implements Comparator<Employee>{

    // 1 0 -1
    @Override
    public int compare(Employee o1, Employee o2) {

        return Double.compare(o1.calculateSalary(), o2.calculateSalary());
    }
}

class SurNameComparator implements Comparator<Employee>{

    @Override
    public int compare(Employee o1, Employee o2) {

        return o1.surName.compareTo(o2.surName);
    }
}

class AgeComparator implements Comparator<Employee>{

    @Override
    public int compare(Employee o1, Employee o2) {
        return o1.age == o2.age ? 0 : (o1.age > o2.age ? 1 : -1);
    }
}

class EmplComparator implements Comparator<Employee>{

    @Override
    public int compare(Employee o1, Employee o2) {
        if(o1 instanceof Worker && o2 instanceof Freelancer){
            return 1;
        }
        else if(o1 instanceof Worker && o2 instanceof Worker || o1 instanceof Freelancer && o2 instanceof Freelancer){
            return 0;
        }
        else{
            return -1;
        }
    }
}

abstract class Employee implements Comparable<Employee>{

    protected String firstName;
    protected String surName;
    protected double salary;
    protected double underworking;
    public int age;
    protected String hobby;

    public Employee(String firstName, String surName, double salary, int age) {
        this.firstName = firstName;
        this.surName = surName;
        this.salary = salary;
        this.age = age;
    }
    public Employee(String firstName, String surName, double salary, int age, String hobby) {
        this.firstName = firstName;
        this.surName = surName;
        this.salary = salary;
        this.age = age;
        this.hobby = hobby;
    }


    public abstract double calculateSalary();

    @Override
    public String toString() {
        return String.format("Сотрудник: %s %s; Ставка: %.2f; Среднемесячная заработная плата: %.2f",
                surName, firstName, salary, calculateSalary());
    }

    @Override
    public int compareTo(Employee o) {
        return Double.compare(calculateSalary(), o.calculateSalary());
    }
}

class Worker extends Employee{

    public Worker(String firstName, String surName, double salary, int age) {
        super(firstName, surName, salary, age);
    }

    @Override
    public double calculateSalary() {
        return salary;
    }

    @Override
    public String toString() {
        return String.format("%s %s; Рабочий; Возраст: %d; Среднемесячная заработная плата: %.2f",
                surName, firstName, age, calculateSalary());
    }
}

/**
 * TODO: 1. Разработать самостоятельно в рамках домашней работы.
 */
class Freelancer extends Employee {
    public Freelancer(String firstName, String surName, int age, double salary, double underworking, String hobby) {
        super(firstName, surName, salary, age, hobby);
        this.underworking = underworking;

    }

    @Override
    public double calculateSalary() {
        if (salary <= 100) {
            return salary + underworking;
        } else {
            return salary;
        }
    }
    @Override
    public String toString() {
        return String.format("%s %s; Фрилансер; Возраст: %d; Среднемесячная заработная плата: %.2f; Хобби: %s",
                surName, firstName, age, calculateSalary(), hobby);
    }
}