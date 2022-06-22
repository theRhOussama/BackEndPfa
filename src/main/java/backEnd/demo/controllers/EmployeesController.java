package backEnd.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import backEnd.demo.entity.EmployeesApp;
import backEnd.demo.entity.UserApp;
import backEnd.demo.service.AccountService;
import backEnd.demo.service.EmailSenderService;
import backEnd.demo.service.EmployeesService;

@RestController

@CrossOrigin(origins = "http://localhost:4200")
public class EmployeesController {


    EmployeesService employeesService;
    AccountService accountService;

	@Autowired
	private EmailSenderService emailSenderService;

    private  String email ;




    public EmployeesController(EmployeesService employeesService, AccountService accountService, EmailSenderService emailSenderService) {
        this.employeesService = employeesService;
        this.accountService = accountService;
        this.emailSenderService = emailSenderService;

    }



    @GetMapping("/getEmployee/{username}")
    public String findEmpByUserName(@PathVariable("username") String username)
    {
            String myemp = employeesService.findEmpByUsernaem(username).getUser().getUsername();
  return myemp;
     }


    @PostMapping("/register/registerEmployee")

    public EmployeesApp  registerEmployee(@RequestBody EmployeesApp employeesApp)
    {


        UserApp user = employeesApp.getUser();
        String username=  user.getUsername().toString();

        employeesService.saveEmployee(employeesApp);
        accountService.addRoleToUser(username,"EMPLOYEE");
        accountService.addRoleToUser(username,"USER");


		try {
		  emailSenderService.sendSimpleEmail(user.getEmail(),"Bienvenue Mr "+user.getUsername()+" dans la premiere platform Marocaine de gestion des contrats","Bienvenue"  );
		}catch(Exception e) {System.out.print(e.getStackTrace());}



        return employeesApp;
    }



}