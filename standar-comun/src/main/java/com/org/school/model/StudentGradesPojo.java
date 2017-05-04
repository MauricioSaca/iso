package com.org.school.model;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentGradesPojo implements Serializable{
	

    private static final long serialVersionUID = 2111239763914112123L;

	private Student student;
	private Double nota1;
	private Double nota2;
	private Double nota3;
	private SubjectPerCourse sc;
	
	public StudentGradesPojo() {
		student = new Student();
		nota1=null;
		nota2=null;
		nota3=null;

    }

    public StudentGradesPojo(Student student, Double note1, Double note2, Double note3, SubjectPerCourse sc) {
        this.student = student;
        this.nota1 = note1;
        this.nota2 = note2;
        this.nota3 = note3;
        this.sc = sc;
    }
   

	

}