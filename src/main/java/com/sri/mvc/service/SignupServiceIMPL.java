package com.sri.mvc.service;

import java.util.Objects;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sri.mvc.DAO.SignupDAO;
import com.sri.mvc.DTO.SignupDTO;
import com.sri.mvc.Entity.SignuEntity;
import com.sri.mvc.SignINDTO.SigninDTO;

@Service
public class SignupServiceIMPL implements SignUPServic {
	@Autowired
	private SignupDAO signupDAO;

	public SignupServiceIMPL() {
		System.out.println("created " + this.getClass().getSimpleName());
	}

	public boolean validateandsave(SignupDTO dtoservice) {

		boolean valid = false;
		try {
			System.out.println("invoked validate & save  " + dtoservice);

			if (Objects.nonNull(dtoservice)) {
				System.out.println("its true saving");
				System.out.println("started service with validation");
				String email = dtoservice.getEmail();
				if (email != null && !email.isEmpty() && email.length() >= 5 && email.contains(".com")) {
					System.out.println("email is valid");
					valid = true;
				} else {
					System.out.println("email is invalid");
					if (email == null) {
						System.out.println("email is null");
					}
					if (email != null && email.length() >= 5) {
						System.out.println("email length is less than 5");
					}

					if (email != null && email.contains(".com")) {
						System.out.println("email not valid mail id");
					}
					valid = false;
				}
				String password = dtoservice.getPwd();
				String conformpassword = dtoservice.getConpwd();
				if (valid && password != null && !password.isEmpty() && password.length() >= 8
						&& conformpassword.equals(password)) {
					System.out.println("password is valid");
					valid = true;
				} else {
					System.out.println("password name is invalid");
					if (password == null) {
						System.out.println("password name is null");
					}
					if (password != null && password.length() < 8) {
						System.out.println("password length is less than 8");
					}
					if (password != null && password.equals(conformpassword)) {
						System.out.println("password is not matching");
					}
					valid = false;
				}
				

			}

			if (valid) {
				System.out.println("data is valid can continue");
				SignuEntity signuEntity = new SignuEntity();
				BeanUtils.copyProperties(dtoservice, signuEntity);
				SignuEntity serviceentity = signupDAO.fetch(dtoservice.getEmail());
				if (serviceentity == null) {
					System.out.println("entity is " + signuEntity);
					signupDAO.save(signuEntity);
					;
					System.out.println("data saved");
				} else {
					System.out.println("mail id already exist");
					valid = false;
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	public boolean validateandsignin(SigninDTO signinservice) {
		boolean valid=false;
		try {
			System.out.println("data is valid can continue");
			SignuEntity signuEntity = new SignuEntity();
			String signemail = signinservice.getSigninemail();
			BeanUtils.copyProperties(signinservice,signuEntity);
			SignuEntity serviceentity = signupDAO.fetchbymailandpassword(signinservice.getSigninemail(), signinservice.getSinginpwd());
			if(serviceentity!=null) {
				signupDAO.fetchbymailandpassword(signinservice.getSigninemail(), signinservice.getSinginpwd());
				System.out.println("user available");
				return true;
			}else {
				System.out.println("no user found");
				return false;
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	

}
