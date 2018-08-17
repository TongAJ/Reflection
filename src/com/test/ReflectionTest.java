package com.test;

import com.bean.Student;
import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

public class ReflectionTest {
    private Class clz;

    //初始化赋值clz
    {
        try {
            clz = Class.forName("com.bean.Student");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        //获取Class的第一种方式
        Student student = new Student();
        Class clz1 = student.getClass();
        System.out.println(clz1.getName());

        //获取Class的第二种方式
        Class clz2 = Student.class;

        System.out.println(clz1 == clz2);

        /*
          常用第三种
         */
        try {
            Class clz3 = Class.forName("com.bean.Student");
            System.out.println(clz3.getName());
            System.out.println(clz3 == clz2);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 测试类的构造器
     *      getConstructors()：获取所有的public构造器
     *      getDeclaredConstructors()：获取所有的构造器
     *      Constructor.newInstance()：使用构造器的构造方法创建一个类的对象
     *      Constructor.setAccessible(true)：如果不是public，可以使用setAccessible(true)来强制公开
     *
     */
    @Test
    public void testConstructor() throws Exception{
        System.out.println("All Public Constructors Belows....");
        Constructor[] constructors = clz.getConstructors();
        for (Constructor constructor : constructors) {
            System.out.println("constructor = " + constructor);
        }

        System.out.println();

        System.out.println("All Constructors Belows...");
        Constructor[] declaredConstructors = clz.getDeclaredConstructors();
        for (Constructor declaredConstructor : declaredConstructors) {
            System.out.println("declaredConstructor = " + declaredConstructor);
        }

        System.out.println();
        System.out.println("Not args Constructor");
        Constructor nullConstructor = clz.getConstructor(null);
        Student student = (Student) nullConstructor.newInstance();
        System.out.println("student = " + student);

        System.out.println();
        System.out.println("Private Constructors be Called && Get Instance");

        Constructor stringConstructor = clz.getDeclaredConstructor(new Class<?>[]{String.class});
        stringConstructor.setAccessible(true);
        Object male = stringConstructor.newInstance("Male");
        System.out.println("male = " + male);
    }


    /**
     * 测试类的属性
     *
     */
    @Test
    public void testFields() throws Exception {
        System.out.println("All Public Fields Belows...");
        Field[] fields = clz.getFields();
        for (Field field : fields) {
            System.out.println("field = " + field);
        }

        System.out.println();
        System.out.println("All Fields Belows...");
        Field[] declaredFields = clz.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            System.out.println("declaredField = " + declaredField);
        }

        System.out.println();
        System.out.println("Get Public Field && Usage");
        Field field = clz.getField("name");
        System.out.println("field = " + field);
        Object o = clz.getConstructor(null).newInstance();
        field.set(o,"AJ");
        Student student = (Student)o;
        System.out.println("valid:name="+student.getName());

        System.out.println();
        System.out.println("Get Private Field && Usage");
        Field fieldPhoneNum = clz.getDeclaredField("phoneNum");
        System.out.println("field = " + field);
        //如果不用
        //java.lang.IllegalAccessException:
        // Class com.test.ReflectionTest can not access a member of
        // class com.bean.Student with modifiers "private"
        fieldPhoneNum.setAccessible(true);

        // 不能赋值错误的属性类型
        //java.lang.IllegalArgumentException:
        // Can not set java.lang.String field com.bean.Student.phoneNum
        // to java.lang.Integer
        fieldPhoneNum.set(o,"phoneNum");
        System.out.println("valid:"+student.toString());
    }

    @Test
    public void testMethod(){

    }
}
