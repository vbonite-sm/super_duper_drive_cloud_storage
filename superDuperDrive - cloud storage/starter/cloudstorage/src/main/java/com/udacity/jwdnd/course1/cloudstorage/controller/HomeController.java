package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.CredentialModel;
import com.udacity.jwdnd.course1.cloudstorage.model.FileModel;
import com.udacity.jwdnd.course1.cloudstorage.model.NoteModel;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.*;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

@Controller
@RequestMapping("/home")
public class HomeController {
    private final FileService fileService;
    private final UserService userService;
    private final NoteService noteService;
    private final EncryptionService encryptionService;
    private final CredentialService credentialService;


    public HomeController(FileService fileService, UserService userService, NoteService noteService, EncryptionService encryptionService, CredentialService credentialService) {
        this.fileService = fileService;
        this.userService = userService;
        this.noteService = noteService;
        this.encryptionService = encryptionService;
        this.credentialService = credentialService;
    }

    private Integer getUserId(Authentication authentication) {
        String username = authentication.getName();
        User user = userService.getUser(username);
        return user.getUserId();
    }

    @GetMapping
    public String homePage(Authentication authentication, @ModelAttribute("uploadFile") FileModel uploadFile, @ModelAttribute("uploadNote") NoteModel uploadNote, @ModelAttribute("uploadCredential") CredentialModel uploadCredential, Model model) {
        Integer id = getUserId(authentication);
        model.addAttribute("files", this.fileService.getFileList(id));
        model.addAttribute("notes", this.noteService.getUserNoteList(id));
        model.addAttribute("credentials", this.credentialService.getCredentialList(id));
        model.addAttribute("encryptionService", encryptionService);
        return "home";
    }

    @PostMapping
    public String newFileRecord(Authentication authentication, @ModelAttribute("uploadFile") FileModel uploadFile, @ModelAttribute("uploadNote") NoteModel uploadNote, Model model, RedirectAttributes redirectAttributes) throws IOException {
        String uploadError = null;
        boolean isDuplicate = false;

        String username = authentication.getName();
        User user = userService.getUser(username);
        Integer userId = user.getUserId();
        String[] fileList = fileService.getFileList(userId);
        MultipartFile multipartFile = uploadFile.getFile();
        String fileName = multipartFile.getOriginalFilename();


        if(fileName.isEmpty()) {
            uploadError = "No file selected. Please select a file";
        }

        for(String f: fileList) {
            if(f.equals(fileName)) {
                isDuplicate = true;
                break;
            }
        }
        if (isDuplicate) {
            uploadError = "File already exists. Please upload another file";
        }

        if(uploadError!=null) {
            redirectAttributes.addFlashAttribute("error", uploadError);
            return "redirect:/result?error";
        }
        fileService.addFile(multipartFile, username);
        return "redirect:/result?success";
    }

    @GetMapping(value = "/viewFile/{fileName}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public @ResponseBody
    byte[] getFile(@PathVariable String fileName) {
        return fileService.getFile(fileName).getFileData();
    }

    @GetMapping(value = "/deleteFile/{fileName}")
    public String deleteRecord(Authentication authentication, @PathVariable String fileName, @ModelAttribute("uploadFile") FileModel uploadFile, Model model, RedirectAttributes redirectAttributes) {
        Integer userId = getUserId(authentication);

        if(fileName != null) {
            fileService.deleteFile(fileName);
            return "redirect:/result?success";
        } else {
            redirectAttributes.addAttribute("error", "Unable to delete the file.");
            return "redirect:/result?error";
        }
    }
}
