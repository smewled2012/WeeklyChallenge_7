package com.example.demo.controller;


/* The programs registers departments first and then employees from the form
* we can see the list of all employees with their details
* we can also see the list of the departments
* it saves it in MySQL database
* for each employee there is an option to update, see Detail or Delete an Employee
* */
import com.example.demo.model.Department;
import com.example.demo.model.Employee;
import com.example.demo.repository.DepartmentRepository;
import com.example.demo.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {

    @Autowired
    DepartmentRepository departmentRepository;

    @Autowired
    EmployeeRepository employeeRepository;

    // create a home page with the links
    @RequestMapping("/")
    public String homePage(){

        return "homepage";
    }
    // create department
    @RequestMapping("/adddepartment")
    public String addDepartment(Model model){
        model.addAttribute("department",new Department());
        return "departmentform";
    }

    // save the department to the database
    @PostMapping("/savedepartment")
    public String saveDepartment(@ModelAttribute("department") Department department, BindingResult result){

        if(result.hasErrors()){
            return "departmentform";
        }
        departmentRepository.save(department);
        return "homepage";
    }


    // create an Employee
    @RequestMapping("/addemployee")
    public String addEmployee(Model model){
        model.addAttribute("employee",new Employee());
        // we add all the departments available
        model.addAttribute("departments",departmentRepository.findAll());
        return "employeeform";
    }

    // save the employee to the database
    @RequestMapping("saveemployee")
    public String saveEmployee(@ModelAttribute("employee") Employee employee, BindingResult result){
        if(result.hasErrors()){
            return "employeeform";
        }
        employeeRepository.save(employee);

        return "homepage";
    }

    // display all the departments
    @RequestMapping("/departmentlist")
    public String showDepartment(Model model){
        model.addAttribute("departments", departmentRepository.findAll());

        return "departmentlist";

    }

    //display the list of employees
    @RequestMapping("/employeelist")
    public String showEmployee(Model model){

        model.addAttribute("employees", employeeRepository.findAll());
        model.addAttribute("departments", departmentRepository.findAll());

        return "employeelist";
    }

    // update an employee
    @RequestMapping("/update/{id}")
    public String updateEmployee(@PathVariable("id") long id, Model model){
        model.addAttribute("departments",departmentRepository.findAll());
        model.addAttribute("employee", employeeRepository.findById(id));
        return "employeeform";
    }

    // to see the detail of an employee
    @RequestMapping("/detail/{id}")
    public String showEmployee(@PathVariable("id") long id, Model model){
        model.addAttribute("employee", employeeRepository.findById(id).get());
        return "show";
    }

    // delete a specific employee
    @RequestMapping("/delete/{id}")
    public String deleteEmployee(@PathVariable("id") long id){
        employeeRepository.deleteById(id);
        return "homepage";
    }


}
