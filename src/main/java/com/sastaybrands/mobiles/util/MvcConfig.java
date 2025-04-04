package com.sastaybrands.mobiles.util;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		 String dirName = "mobile-photos";
		 Path mobilePhotosDir = Paths.get(dirName);
		
		 String mobilePhotosPath = mobilePhotosDir.toFile().getAbsolutePath();
		
		 registry.addResourceHandler("/" + dirName + "/**")
		 	.addResourceLocations("file:/" + mobilePhotosPath + "/");
		
	

		
		String brandLogosDirName = "brand-logos";
		Path brandLogosDir = Paths.get(brandLogosDirName);
		
		String brandLogosPath = brandLogosDir.toFile().getAbsolutePath();
		
		 registry.addResourceHandler("/" + brandLogosDirName + "/**")
			.addResourceLocations("file:/" + brandLogosPath + "/");	
		
		
		
		String pouchImageDirName = "pouch-photos";
		Path pouchImageDir = Paths.get(pouchImageDirName);
		
		String pouchImagePath = pouchImageDir.toFile().getAbsolutePath();
		
		 registry.addResourceHandler("/" + pouchImageDirName + "/**")
			.addResourceLocations("file:/" + pouchImagePath + "/");	
	}
}