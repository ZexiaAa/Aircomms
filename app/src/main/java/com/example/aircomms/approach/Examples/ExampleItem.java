package com.example.aircomms.approach.Examples;

import java.util.ArrayList;

public class ExampleItem {
    String exampleTitle;
    ArrayList<DialogueItem> exampleDialogue;
    ArrayList<NoteItem> exampleNotes;
    int audioFileID;

    public ExampleItem(String exampleTitle, ArrayList<DialogueItem> exampleDialogue, ArrayList<NoteItem> exampleNotes, int audioFileID) {
        this.exampleTitle = exampleTitle;
        this.exampleDialogue = exampleDialogue;
        this.exampleNotes = exampleNotes;
        this.audioFileID = audioFileID;
    }

    public ExampleItem(String exampleTitle, ArrayList<DialogueItem> exampleDialogue, ArrayList<NoteItem> exampleNotes) {
        this.exampleTitle = exampleTitle;
        this.exampleDialogue = exampleDialogue;
        this.exampleNotes = exampleNotes;
        this.audioFileID = 0;
    }

    public ExampleItem(String exampleTitle, ArrayList<DialogueItem> exampleDialogue, int audioFileID) {
        this.exampleTitle = exampleTitle;
        this.exampleDialogue = exampleDialogue;
        this.audioFileID = audioFileID;
    }

    public ExampleItem(String exampleTitle, ArrayList<DialogueItem> exampleDialogue) {
        this.exampleTitle = exampleTitle;
        this.exampleDialogue = exampleDialogue;
        this.audioFileID = 0;
    }

    public String getExampleTitle() {
        return exampleTitle;
    }

    public ArrayList<DialogueItem> getExampleDialogue() {
        return exampleDialogue;
    }

    public ArrayList<NoteItem> getExampleNotes() {
        return exampleNotes;
    }

    public int getAudioFileID() {
        return audioFileID;
    }
}
