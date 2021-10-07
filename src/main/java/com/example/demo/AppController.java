package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@Controller
public class AppController {
	@Autowired
    private UserRepository repo;
	@Autowired
	private ProductRepository repos;
	@GetMapping("")
   public String viewHomePage() {
       return "index";
   }
   
   @GetMapping("/register")
   public String showSignUpForm(Model model) {
	   model.addAttribute("user", new User());
	   return "signup_form";
   }
   
   @PostMapping("/process_register")
   public String processRegister(User user) {
	    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	    String encodedPassword = passwordEncoder.encode(user.getPassword());
	    user.setPassword(encodedPassword);
	     
	    repo.save(user);
	     
	    return "register_success";
	}

   
   @GetMapping("/list_users") 
   public String viewUsersLists(Model model) {
	   List<User> listUsers = repo.findAll();
	   model.addAttribute("listUsers", listUsers);
	   return "users";
   }
   @GetMapping("/list_products") 
   public String viewProductsLists(Model model) {
	   List<Product> listProducts = repos.findAll();
	   model.addAttribute("listProducts", listProducts);
	   return "user_product";
   }
   
   @GetMapping("/home_users")
   public String showProductForm(Model model) {
	   model.addAttribute("product", new Product());
	   return "product_form";
   }
   
   @PostMapping("/product_register/save")
   public String saveProduct(@ModelAttribute(name="product") Product product,
		   RedirectAttributes ra,
           @RequestParam("fileImage") MultipartFile multipartFile) throws IOException {
        
       String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
       product.setProductImage(fileName);
        
       Product savedProduct = repos.save(product);

       String uploadDir = "product-photos/" + savedProduct.getId();
       Path uploadPath =Paths.get(uploadDir);
       if (!Files.exists(uploadPath)) {
    	   Files.createDirectories(uploadPath);
       }
       try (InputStream inputStream = multipartFile.getInputStream()) {
           Path filePath = uploadPath.resolve(fileName);
           System.out.println(filePath.toFile().getAbsolutePath());
           Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
       } catch (IOException ioe) {        
           throw new IOException("Could not save image file: " + fileName, ioe);
       }  
       
       ra.addFlashAttribute("message","The product has been saved successfully.");
       return "redirect:/list_products";
       
   }  
}