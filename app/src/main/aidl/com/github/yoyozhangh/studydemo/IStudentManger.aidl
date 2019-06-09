// IStudentManger.aidl
package com.github.yoyozhangh.studydemo;
import  com.github.yoyozhangh.studydemo.Student;

interface IStudentManger {
    List<Student> getStudentList();
    void addStudent(in Student student);
}
