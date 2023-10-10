package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.FileModel;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.NoteModel;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Controller
@RequestMapping("/note")
public class NoteController {
    private NoteService noteService;
    private UserService userService;

    public NoteController(NoteService noteService, UserService userService) {
        this.noteService = noteService;
        this.userService = userService;
    }

    private Integer getUserId(Authentication authentication) {
        String username = authentication.getName();
        User user = userService.getUser(username);
        return user.getUserId();
    }

    @GetMapping
    public String homePage(Authentication authentication, @ModelAttribute("uploadNote") NoteModel uploadNote, Model model) {
        Integer id = getUserId(authentication);
        model.addAttribute("notes", this.noteService.getUserNoteList(id));
        return "home";
    }

    @GetMapping(value = "/editNote/{noteId}")
    public Note getNote(@PathVariable Integer noteId) {
        return noteService.getNote(noteId);
    }

    @PostMapping("addNote")
    public String newNoteRecord(Authentication authentication, @ModelAttribute("uploadNote") NoteModel uploadNote, Model model) throws IOException {
        String username = authentication.getName();
        String noteTitle = uploadNote.getNoteTitle();
        String noteId = uploadNote.getNoteId();
        String noteDesc = uploadNote.getNoteDesc();
        if(noteId.isEmpty()) {
            noteService.addNote(noteTitle, noteDesc, username);
        } else {
            Note editNote = getNote(Integer.parseInt(noteId));
            noteService.updateNote(editNote.getNoteId(), noteTitle, noteDesc);
        }
        Integer userId = getUserId(authentication);
        model.addAttribute("notes", noteService.getUserNoteList(userId));
        return "redirect:/result?success";
    }

    @GetMapping(value = "/deleteNote/{noteId}")
    public String deleteNote(Authentication authentication, @PathVariable Integer noteId, @ModelAttribute("uploadNote") NoteModel uploadNote, Model model) {
        noteService.deleteNote(noteId);
        Integer userId = getUserId(authentication);
        model.addAttribute("notes", noteService.getUserNoteList(userId));
        return "redirect:/result?success";
    }
}
