package com.mobilematching.site;

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
		exposeDirectory("../glass_protector", registry);
		exposeDirectory("../brand-logos", registry);
		exposeDirectory("../pouch-photos", registry);

		
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

}	