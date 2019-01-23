package com.arulsuju.jsonrest.rest;

import com.arulsuju.jsonrest.model.Student;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/rest")
public class DemoRestController {
    public List<Student> theList;

    @PostConstruct
    public void load(){
        theList=new ArrayList<Student>();
        theList.add(new Student(1,"arulsuju","chennai"));
        theList.add(new Student(2,"saritha","kerala"));
    }

    @RequestMapping("/demo")
    public String demoMethod() {
        return "Hello World";
    }

    @RequestMapping("/getstudents")
    public List<Student> getList(){
        return theList;
    }

    @RequestMapping("/getstudent/{studentid}")
    public Student getStudent(@PathVariable int studentid){
      if(studentid>=theList.size()||studentid<0) {
          throw new StudentNotFoundException("Student Not found for the id"+studentid);
      }
        return theList.get(studentid);
    }

    @ExceptionHandler
    public ResponseEntity<StudentNotFoundErrorResponse> handleException(StudentNotFoundException se){
        StudentNotFoundErrorResponse errorResponse=new StudentNotFoundErrorResponse();
        errorResponse.setStatus(HttpStatus.NOT_FOUND.value());
        errorResponse.setMessage(se.getMessage());
        errorResponse.setTimeStamp(System.currentTimeMillis());

        return new ResponseEntity<StudentNotFoundErrorResponse>(errorResponse,HttpStatus.NOT_FOUND);
    }
}
