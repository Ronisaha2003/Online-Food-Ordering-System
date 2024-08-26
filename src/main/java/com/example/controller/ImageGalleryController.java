package com.example.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Date;
import java.util.Optional;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

import com.example.model.ImageGallery;
import com.example.repository.ImageGalleryRepository;
import com.example.service.ImageGalleryService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;



@Controller
public class ImageGalleryController {
	
	
	@Value("${uploadDir}")
	private String uploadFolder;

	@Autowired
	private ImageGalleryService imageGalleryService;
	
	@Autowired
	private ImageGalleryRepository imageGalleryRepository;

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@GetMapping(value = {"/homeimg"})
	public String addProductPage() {
		return "product_page";
	}

	@PostMapping("/image/saveImageDetails")
	public @ResponseBody ResponseEntity<?> createProduct(@RequestParam("name") String name,
			@RequestParam("price") double price, @RequestParam("description") String description, Model model, HttpServletRequest request
			,final @RequestParam("image") MultipartFile file) {
		try {
			//String uploadDirectory = System.getProperty("user.dir") + uploadFolder;
			String uploadDirectory = request.getServletContext().getRealPath(uploadFolder);
			log.info("uploadDirectory:: " + uploadDirectory);
			String fileName = file.getOriginalFilename();
			String filePath = Paths.get(uploadDirectory, fileName).toString();
			log.info("FileName: " + file.getOriginalFilename());
			if (fileName == null || fileName.contains("..")) {
				model.addAttribute("invalid", "Sorry! Filename contains invalid path sequence \" + fileName");
				return new ResponseEntity<>("Sorry! Filename contains invalid path sequence " + fileName, HttpStatus.BAD_REQUEST);
			}
			String[] names = name.split(",");
			String[] descriptions = description.split(",");
			Date createDate = new Date();
			log.info("Name: " + names[0]+" "+filePath);
			log.info("description: " + descriptions[0]);
			log.info("price: " + price);
			try {
				File dir = new File(uploadDirectory);
				if (!dir.exists()) {
					log.info("Folder Created");
					dir.mkdirs();
				}
				// Save the file locally
				BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(filePath)));
				stream.write(file.getBytes());
				stream.close();
			} catch (Exception e) {
				log.info("in catch");
				e.printStackTrace();
			}
			byte[] imageData = file.getBytes();
			ImageGallery imageGallery = new ImageGallery();
			imageGallery.setName(names[0]);
			imageGallery.setImage(imageData);
			imageGallery.setPrice(price);
			imageGallery.setDescription(descriptions[0]);
			imageGallery.setCreateDate(createDate);
			imageGalleryService.saveImage(imageGallery);
			log.info("HttpStatus===" + new ResponseEntity<>(HttpStatus.OK));
			return new ResponseEntity<>("Product Saved With File - " + fileName, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			log.info("Exception: " + e);
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/image/display/{id}")
	@ResponseBody
	void showImage(@PathVariable("id") Long id, HttpServletResponse response, Optional<ImageGallery> imageGallery)
			throws ServletException, IOException {
		log.info("Id :: " + id);
		imageGallery = imageGalleryService.getImageById(id);
		response.setContentType("image/jpeg, image/jpg, image/png, image/gif");
		response.getOutputStream().write(imageGallery.get().getImage());
		response.getOutputStream().close();
	}

	@GetMapping("/image/imageDetails")
	String showProductDetails(@RequestParam("id") Long id, Optional<ImageGallery> imageGallery, Model model) {
		try {
			log.info("Id :: " + id);
			if (id != 0) {
				imageGallery = imageGalleryService.getImageById(id);
			
				log.info("products :: " + imageGallery);
				if (imageGallery.isPresent()) {
					model.addAttribute("id", imageGallery.get().getId());
					model.addAttribute("description", imageGallery.get().getDescription());
					model.addAttribute("name", imageGallery.get().getName());
					model.addAttribute("price", imageGallery.get().getPrice());
					return "imagedetails";
				}
				return "redirect:/homeimg";
			}
		return "redirect:/homeimg";
		} catch (Exception e) {
			e.printStackTrace();
			return "redirect:/homeimg";
		}	
	}

	@GetMapping("/image/show")
	String show(Model map) {
		List<ImageGallery> images = imageGalleryService.getAllActiveImages();
		map.addAttribute("images", images);
		return "images";
	}
		@GetMapping("/deleteImageById/{id}")
	public String deleteImage(@PathVariable (value = "id") long id) {
		
		// call delete employee method 
		this.imageGalleryService.deleteImageById(id);
		return "redirect:/image/show";
	}
		@GetMapping("/test")
		public String test(Model map)
		{
			List<ImageGallery> images = imageGalleryService.getAllActiveImages();
			map.addAttribute("images", images);
			
			return "test";
		}
		
		//testing section
		 @GetMapping("/formPage")
		    public String showFormPage(@RequestParam("id") Long imageGalleryId, Model model) {
		        Optional<ImageGallery> imageGalleryOptional =imageGalleryRepository.findById(imageGalleryId) ;
		        
		        if (imageGalleryOptional.isPresent()) {
		            ImageGallery imageGallery = imageGalleryOptional.get();
		            model.addAttribute("imageGallery", imageGallery);
		            return "test_cart"; // Return the Thymeleaf template name
		        } else {
		            // Handle case where imageGallery with given id is not found
		            return "error"; // Example: Redirect to an error page
		        }
		    }

		    // Process form submission
		    @PostMapping("/processForm")
		    public String processFormSubmission(@RequestParam("name") String name,
		                                        @RequestParam("description") String description,
		                                        @RequestParam("createDate") String createDate,
		                                        @RequestParam("price") double price,
		                                        Model model) {
		        // Process the form data (e.g., save to database, send email, etc.)
		        // For demonstration, we'll just print the data
		        System.out.println("Name: " + name);
		        System.out.println("Description: " + description);
		        System.out.println("Create Date: " + createDate);
		        System.out.println("Price: " + price);

		        // Add success message or any other logic as needed
		        model.addAttribute("message", "Form submitted successfully!");

		        // Redirect to a success page or return to another view
		        return "redirect:/successPage"; // Redirect to a success page
		    }
		}
		
