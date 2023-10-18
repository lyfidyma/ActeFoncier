package com.cab.sn.controller;

import java.io.IOException;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cab.sn.metier.StorageService;
import com.cab.sn.storage.StorageFileNotFoundException;

@Controller
public class FileUploadController {
	
	
	private final StorageService storageService;
	
	@Autowired
	public FileUploadController(StorageService storageService) {
		this.storageService = storageService;
	}
	
	/*
	 * @GetMapping("/upload") public String listUploadedFiles(Model model) throws
	 * IOException {
	 * 
	 * model.addAttribute("files", storageService.loadAll().map( path ->
	 * MvcUriComponentsBuilder.fromMethodName(FileUploadController.class,
	 * "serveFile", path.getFileName().toString()).build().toUri().toString())
	 * .collect(Collectors.toList()));
	 * 
	 * return "formulaire"; }
	 */

	@GetMapping("/files/{filename:.+}")
	@ResponseBody
	public ResponseEntity<Resource> serveFile(@PathVariable String filename) {

		Resource file = storageService.loadAsResource(filename);
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
				"attachment; filename=\"" + file.getFilename() + "\"").body(file);
	}

	@PostMapping("/upload")
	public String handleFileUpload(@RequestParam("file") MultipartFile file,@RequestParam("file1") MultipartFile file1,
			RedirectAttributes redirectAttributes) {
		if(file!=null) {
		storageService.init();
		storageService.store(file);
		}
		if(file1!=null) {
			storageService.init();
			storageService.store(file1);
		}
		redirectAttributes.addFlashAttribute("message",
				"Fichier chargé " + file.getOriginalFilename() + "!");

		return "formulaire";
	}

	@ExceptionHandler(StorageFileNotFoundException.class)
	public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
		return ResponseEntity.notFound().build();
	}


}
