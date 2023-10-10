package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.springframework.stereotype.Service;

@Service
public class NoteService {
    private final UserMapper userMapper;
    private final NoteMapper noteMapper;

    public NoteService(UserMapper userMapper, NoteMapper noteMapper) {
        this.userMapper = userMapper;
        this.noteMapper = noteMapper;
    }

    public Note[] getUserNoteList(Integer userId){
        return noteMapper.getUserNotes(userId);
    }

    public Note getNote(Integer noteId) {
        return noteMapper.getNote(noteId);
    }

    public void updateNote(Integer noteId, String noteTitle, String noteDesc) {
        noteMapper.updateNote(noteId, noteTitle, noteDesc);
    }

    public void addNote(String noteTitle, String noteDesc, String username) {
        Integer userId = userMapper.getUser(username).getUserId();
        Note note = new Note(0, noteTitle, noteDesc, userId);
        noteMapper.insert(note);
    }

    public void deleteNote(Integer noteId) {
        noteMapper.deleteNote(noteId);
    }
}
