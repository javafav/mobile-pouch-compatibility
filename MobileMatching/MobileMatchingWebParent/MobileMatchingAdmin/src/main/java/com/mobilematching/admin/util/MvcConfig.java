package com.mobilematching.admin.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

	
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {

			
		exposeDirectory("../mobile-photos", registry);
		exposeDirectory("../category-images", registry);
		exposeDirectory("../brand-logos", registry);
		exposeDirectory("../pouch-photos", registry);
		exposeDirectory("../glass_protector", registry);

		
	}
	
	private void exposeDirectory(String pathPattren, ResourceHandlerRegistry registry) {
		
		Path path = Paths.get(pathPattren);
		String absloutePath = path.toFile().getAbsolutePath();
		
		String logicalPath = pathPattren.replace("..", "") + "/**";
		
		registry.addResourceHandler(logicalPath)
		                      .addResourceLocations("file:/" + absloutePath + "/");


	}
	
	

	public static void cleanDir(String dir) {
		Path dirPath = Paths.get(dir);
		try {
			Files.list(dirPath).forEach(file -> {
				if (Files.isDirectory(file)) {
					try {
						Files.delete(file);
					} catch (IOException e) {
						System.out.print("Could not delete the file" + file);
					}

				}
			});
		} catch (IOException e) {
			System.out.print("Could not list the directory" + dir);

		}
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
//	@Override
//	public void addResourceHandlers(ResourceHandlerRegistry registry) {
//		 String mobileDirName = "mobile-photos";
//		 Path mobilePhotosDirPath = Paths.get(mobileDirName);
//		 
//		 String mobilePhotosPath = mobilePhotosDirPath.toFile().getAbsolutePath();
//		
//		
//		 
//		 registry.addResourceHandler("/" + mobileDirName + "/**")
//		 	.addResourceLocations("file:/" + mobilePhotosPath + "/");
//		 
//		 
//		 
//		
//	
//
//		
//		String brandLogosDirName = "brand-logos";
//		Path brandLogosDir = Paths.get(brandLogosDirName);
//		
//		String brandLogosPath = brandLogosDir.toFile().getAbsolutePath();
//		
//		 registry.addResourceHandler("/" + brandLogosDirName + "/**")
//			.addResourceLocations("file:/" + brandLogosPath + "/");	
//		
//		
//		
//		String pouchImageDirName = "pouch-photos";
//		Path pouchImageDir = Paths.get(pouchImageDirName);
//		
//		String pouchImagePath = pouchImageDir.toFile().getAbsolutePath();
//		
//		 registry.addResourceHandler("/" + pouchImageDirName + "/**")
//			.addResourceLocations("file:/" + pouchImagePath + "/");	
//	}
}