package com.example.aircomms.approach.Examples;

import java.util.ArrayList;

public class DialogueItem {
    String dialogueSpeaker, dialogueContent;
    ArrayList<DialogueHighlight> dialogueHighlights;

    public DialogueItem(String dialogueSpeaker, String dialogueContent, ArrayList<DialogueHighlight> dialogueHighlights) {
        this.dialogueSpeaker = dialogueSpeaker;
        this.dialogueContent = dialogueContent;
        this.dialogueHighlights = dialogueHighlights;
    }

    public DialogueItem(String dialogueSpeaker, String dialogueContent) {
        this.dialogueSpeaker = dialogueSpeaker;
        this.dialogueContent = dialogueContent;
    }

    public DialogueItem(ArrayList<DialogueHighlight> dialogueHighlights) {
        this.dialogueHighlights = dialogueHighlights;
    }

    public DialogueItem(String dialogueContent) {
        this.dialogueContent = dialogueContent;
    }

    public String getDialogueSpeaker() {
        return dialogueSpeaker;
    }

    public String getDialogueContent() {
        return dialogueContent;
    }

    public ArrayList<DialogueHighlight> getDialogueHighlights() {
        return dialogueHighlights;
    }
}
